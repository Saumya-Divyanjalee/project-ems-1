package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DepartmentDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.DepartmentTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.DepartmentModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable {

    public Label lblDepartmentId;
    public TextField txtDepartmentName;
    public ComboBox<String> cmbEID;

    public TableView<DepartmentTM> tblDepartment;
    public TableColumn<DepartmentTM, String> colDepartmentId;
    public TableColumn<DepartmentTM, String> colDepartmentName;
    public TableColumn<DepartmentTM, String> colEmployeeId;

    private final DepartmentModel departmentModel = new DepartmentModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDepartmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        colDepartmentName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        try {
            cmbEID.setItems(EmployeeModel.getAllEmployeeid());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading employee IDs!").show();
        }

        resetPage();
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DepartmentDTO> departmentDTOArrayList = departmentModel.getAllDepartment();
        ObservableList<DepartmentTM> departmentTMS = FXCollections.observableArrayList();

        for (DepartmentDTO departmentDTO : departmentDTOArrayList) {
            DepartmentTM departmentTM = new DepartmentTM(
                    departmentDTO.getDepartmentId(),
                    departmentDTO.getDepartmentName(),
                    departmentDTO.getEmployeeId()
            );
            departmentTMS.add(departmentTM);
        }
        tblDepartment.setItems(departmentTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtDepartmentName.clear();
            cmbEID.getSelectionModel().clearSelection();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong during reset!").show();
        }
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        String departmentId = lblDepartmentId.getText();
        String departmentName = txtDepartmentName.getText();
        String employeeId = cmbEID.getValue();

        DepartmentDTO departmentDTO = new DepartmentDTO(departmentId, departmentName, employeeId);

        try {
            boolean isSaved = departmentModel.saveDepartment(departmentDTO);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Department saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Department could not be saved!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Department could not be saved!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        String departmentId = lblDepartmentId.getText();
        String departmentName = txtDepartmentName.getText();
        String employeeId = cmbEID.getValue();

        DepartmentDTO departmentDTO = new DepartmentDTO(departmentId, departmentName, employeeId);

        try {
            boolean isUpdated = departmentModel.updatedepartment(departmentDTO);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Department updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Department could not be updated!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Department could not be updated!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this department?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String departmentId = lblDepartmentId.getText();
            try {
                boolean isDeleted = departmentModel.deletedepartment(departmentId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Department deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Department could not be deleted!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Oops!...Department could not be deleted!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = departmentModel.getNextDepartmentId();
        lblDepartmentId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        DepartmentTM selectedItem = tblDepartment.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblDepartmentId.setText(selectedItem.getDepartmentId());
            txtDepartmentName.setText(selectedItem.getDepartmentName());
            cmbEID.setValue(selectedItem.getEmployeeId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private boolean validateInput() {
        if (txtDepartmentName.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter department name.").show();
            return false;
        }

        if (cmbEID.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an Employee ID.").show();
            return false;
        }

        return true;
    }
}
