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
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.TaskModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskController implements Initializable {
    public TextField txtStatus;
    public TextField txtDeadline;
    public Label lblTaskId;
    public TextField txtEmployeeId;
    public TextField txtDescription;

    public TableView<TaskTM> tblTask;
    public TableColumn<TaskTM, String> colTaskId;
    public TableColumn<TaskTM, String> colEmployeeId;
    public TableColumn<TaskTM, String> colDescription;
    public TableColumn<TaskTM, String>colDeadline;
    public TableColumn<TaskTM, String>colStatus;
    public ComboBox cmbEID;
    public DatePicker dateDeadline;
    public ComboBox cmbStatus;


    TaskModel taskModel = new TaskModel();
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colTaskId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<TaskDTO> taskDTOArrayList = taskModel.getAllTasks();
        ObservableList<TaskTM> taskTMS = FXCollections.observableArrayList();

        for (TaskDTO dto : taskDTOArrayList) {
            TaskTM tm = new TaskTM(
                    dto.getTaskId(),
                    dto.getEmployeeId(),
                    dto.getDescription(),
                    dto.getDeadline(),
                    dto.getStatus()
            );
            taskTMS.add(tm);
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

            txtEmployeeId.setText("");
            txtDescription.setText("");
            txtDeadline.setText("");
            txtStatus.setText("");

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
                new Alert(Alert.AlertType.ERROR, "Failed to delete task.").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
         String taskId = lblTaskId.getText();
         String employeeId = txtEmployeeId.getText();
         String description = txtDescription.getText();
         String deadline = txtDeadline.getText();
         String status = txtStatus.getText();

         TaskDTO taskDTO = new TaskDTO(
                 taskId,employeeId,description,deadline,status
         );

        try {
            boolean isUpdated = taskModel.updateTask(taskDTO);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Task updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update task.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update task.").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        TaskDTO taskDTO = new TaskDTO(
                lblTaskId.getText(),
                txtEmployeeId.getText(),
                txtDescription.getText(),
                txtDeadline.getText(),
                txtStatus.getText()
        );

        try {
            boolean isSaved = taskModel.saveTask(taskDTO);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Task saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Task could not be saved!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Task could not be saved!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        TaskTM selectedItem = tblTask.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblTaskId.setText(selectedItem.getTaskId());
            txtEmployeeId.setText(selectedItem.getEmployeeId());
            txtDescription.setText(selectedItem.getDescription());
            txtDeadline.setText(selectedItem.getDeadline());
            txtStatus.setText(selectedItem.getStatus());

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