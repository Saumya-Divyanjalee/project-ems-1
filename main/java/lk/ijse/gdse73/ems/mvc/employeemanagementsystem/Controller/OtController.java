package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.OtTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.OtModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OtController implements Initializable {

    public Label lblOtId;
    public ComboBox<String> cmbEID;
    public TextField txtOtHours;
    public TextField txtRatePerHour;
    public TextField txtOtDate;

    public TableView<OtTM> tblOtRecords;
    public TableColumn<OtTM, String> colOtId;
    public TableColumn<OtTM, String> colEmployeeId;
    public TableColumn<OtTM, String> colOtHours;
    public TableColumn<OtTM, String> colRatePerHour;
    public TableColumn<OtTM, String> colOtDate;
    public TableColumn<OtTM, String> colPayment;



    private final OtModel otModel = new OtModel();
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error during initialization!").show();
            try {
                cmbEID.setItems(EmployeeModel.getAllEmployeeid());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private void setCellValueFactory() {
        colOtId.setCellValueFactory(new PropertyValueFactory<>("otId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
        colRatePerHour.setCellValueFactory(new PropertyValueFactory<>("ratePerHours"));
        colOtDate.setCellValueFactory(new PropertyValueFactory<>("otDate"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
    }



    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<OtDTO> otList = otModel.getAllOt();
        ObservableList<OtTM> obList = FXCollections.observableArrayList();

        for (OtDTO dto : otList) {
            double payment = Double.parseDouble(dto.getOtHours()) * Double.parseDouble(dto.getRatePerHours());
            OtTM tm = new OtTM(
                    dto.getOtId(),
                    dto.getEmployeeId(),
                    dto.getOtHours(),
                    dto.getRatePerHours(),
                    dto.getOtDate(),
                    payment
            );
            obList.add(tm);
        }
        tblOtRecords.setItems(obList);
    }

    private void loadNextId() throws SQLException {
        String nextId = otModel.generateNextOtId();
        lblOtId.setText(nextId);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();
            cmbEID.getSelectionModel().clearSelection();
            txtOtHours.clear();
            txtRatePerHour.clear();
            txtOtDate.clear();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong while resetting!").show();
        }
    }

    public void saveOnAction(ActionEvent event) {
        String otId = lblOtId.getText();
        String empId = String.valueOf(cmbEID.getValue());
        String otHours = txtOtHours.getText();
        String ratePerHour = txtRatePerHour.getText();
        String otDate = txtOtDate.getText();

        OtDTO dto = new OtDTO(otId, otHours, otDate, ratePerHour, empId, null);

        try {
            boolean isSaved = otModel.saveOt(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "OT record saved!").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save OT record!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving!").show();
        }
    }

    public void updateOnAction(ActionEvent event) {
        String otId = lblOtId.getText();
        String empId = String.valueOf(cmbEID.getValue());
        String otHours = txtOtHours.getText();
        String ratePerHour = txtRatePerHour.getText();
        String otDate = txtOtDate.getText();

        OtDTO dto = new OtDTO(otId, otHours, otDate, ratePerHour, empId, null);

        try {
            boolean isUpdated = otModel.updateOt(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "OT record updated!").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update OT record!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating!").show();
        }
    }

    public void deleteOnAction(ActionEvent event) {
        String otId = lblOtId.getText();
        try {
            boolean isDeleted = otModel.deleteOt(otId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "OT record deleted!").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete OT record!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while deleting!").show();
        }
    }

    public void resetOnAction(ActionEvent event) {
        resetPage();
    }

    public void onClickTable(MouseEvent event) {
        OtTM selected = tblOtRecords.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblOtId.setText(selected.getOtId());
            cmbEID.setValue(selected.getEmployeeId());
            txtOtHours.setText(selected.getOtHours());
            txtRatePerHour.setText(selected.getRatePerHours());
            txtOtDate.setText(selected.getOtDate());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
