package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TaskDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.TaskTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.TaskModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    public Label lblTaskId;
    public TextField txtDescription;
    public ComboBox<String> cmbEID;
    public DatePicker dateDeadline;
    public ComboBox<String> cmbStatus;

    public TableView<TaskTM> tblTask;
    public TableColumn<TaskTM, String> colTaskId;
    public TableColumn<TaskTM, String> colEmployeeId;
    public TableColumn<TaskTM, String> colDescription;
    public TableColumn<TaskTM, String> colDeadline;
    public TableColumn<TaskTM, String> colStatus;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    TaskModel taskModel = new TaskModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colTaskId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbStatus.setItems(FXCollections.observableArrayList("Assigned", "In Progress", "Pending"));

        try {
            cmbEID.setItems(EmployeeModel.getAllEmployeeid());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Employee ID load කිරීමේදී Error එකක්!").show();
        }

        resetPage(); // මුලදී load වෙනවා
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<TaskDTO> taskDTOArrayList = taskModel.getAllTasks();
        ObservableList<TaskTM> taskTMS = FXCollections.observableArrayList();

        for (TaskDTO dto : taskDTOArrayList) {
            taskTMS.add(new TaskTM(
                    dto.getTaskId(),
                    dto.getEmployeeId(),
                    dto.getDescription(),
                    dto.getDeadline(),
                    dto.getStatus()
            ));
        }
        tblTask.setItems(taskTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            cmbStatus.getSelectionModel().clearSelection();
            cmbEID.getSelectionModel().clearSelection();
            txtDescription.clear();
            dateDeadline.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this task?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = taskModel.deleteTask(lblTaskId.getText());
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Task deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete task.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting task.").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        try {
            String taskId = lblTaskId.getText();
            String employeeId = cmbEID.getValue();
            String description = txtDescription.getText();
            String deadline = dateDeadline.getValue().toString();
            String status = cmbStatus.getValue();

            TaskDTO taskDTO = new TaskDTO(taskId, employeeId, description, deadline, status);

            boolean isUpdated = taskModel.updateTask(taskDTO);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Task updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update task.").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update error! Check input fields.").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            String taskId = lblTaskId.getText();
            String employeeId = cmbEID.getValue();
            String description = txtDescription.getText();
            LocalDate deadlineDate = dateDeadline.getValue();
            String status = cmbStatus.getValue();

            if (employeeId == null || description.isEmpty() || deadlineDate == null || status == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
                return;
            }

            TaskDTO taskDTO = new TaskDTO(taskId, employeeId, description, deadlineDate.toString(), status);

            boolean isSaved = taskModel.saveTask(taskDTO);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Task saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Task could not be saved!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Task save error!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        TaskTM selectedItem = tblTask.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblTaskId.setText(selectedItem.getTaskId());
            cmbEID.setValue(selectedItem.getEmployeeId());
            txtDescription.setText(selectedItem.getDescription());

            try {
                LocalDate deadline = LocalDate.parse(selectedItem.getDeadline());
                dateDeadline.setValue(deadline);
            } catch (Exception e) {
                dateDeadline.setValue(null);
            }

            cmbStatus.setValue(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = taskModel.getNextTaskId();
        lblTaskId.setText(nextId);
    }
}
