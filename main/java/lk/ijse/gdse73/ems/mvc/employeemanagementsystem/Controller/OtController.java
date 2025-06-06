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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OtController implements Initializable {

    public Label lblOtId;
    public ComboBox<String> cmbEID;
    public TextField txtOtHours;
    public TextField txtRatePerHour;
    public DatePicker dateOt;

    public TableView<OtTM> tblOtRecords;
    public TableColumn<OtTM, String> colOtId;
    public TableColumn<OtTM, String> colEmployeeId;
    public TableColumn<OtTM, String> colOtHours;
    public TableColumn<OtTM, String> colRatePerHour;
    public TableColumn<OtTM, String> colOtDate;
    public TableColumn<OtTM, String> colPayment;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final OtModel otModel = new OtModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        try {
            loadOtRecords();
            cmbEID.setItems(EmployeeModel.getAllEmployeeid());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error initializing OT data!").show();
        }
        resetPage();
    }

    private void setCellValueFactory() {
        colOtId.setCellValueFactory(new PropertyValueFactory<>("otId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
        colRatePerHour.setCellValueFactory(new PropertyValueFactory<>("ratePerHours"));
        colOtDate.setCellValueFactory(new PropertyValueFactory<>("otDate"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("overtimePayment"));
    }

    private void loadOtRecords() throws SQLException, ClassNotFoundException {
        ObservableList<OtTM> list = FXCollections.observableArrayList();
        for (OtDTO dto : otModel.getAllOtRecords()) {
            double payment = dto.getOtHours() * dto.getRatePerHours();
            list.add(new OtTM(
                    dto.getOtId(),
                    dto.getEmployeeId(),
                    String.valueOf(dto.getOtHours()),
                    String.valueOf(dto.getRatePerHours()),
                    dto.getOtDate(),
                    String.format("%.2f", payment)
            ));
        }
        tblOtRecords.setItems(list);
    }


    private void resetPage() {
        lblOtId.setText(otModel.generateNextOtId());
        cmbEID.setValue(null);
        txtOtHours.clear();
        txtRatePerHour.clear();
        dateOt.setValue(null);

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }



    private boolean validateInput() {
        if (cmbEID.getValue() == null || cmbEID.getValue().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select an employee ID!").show();
            return false;
        }

        if (txtOtHours.getText().isEmpty() || txtRatePerHour.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter OT hours and rate!").show();
            return false;
        }

        if (dateOt.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a date!").show();
            return false;
        }

        try {
            Double.parseDouble(txtOtHours.getText());
            Double.parseDouble(txtRatePerHour.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "OT Hours and Rate must be valid numbers!").show();
            return false;
        }

        return true;
    }

    public void onClickTable(MouseEvent mouseEvent) {
        OtTM selected = tblOtRecords.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                lblOtId.setText(selected.getOtId());
                cmbEID.setValue(selected.getEmployeeId());
                txtOtHours.setText(selected.getOtHours());
                txtRatePerHour.setText(selected.getRatePerHours());
                dateOt.setValue(LocalDate.parse(selected.getOtDate()));

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Invalid data selected!").show();
                e.printStackTrace();
            }
        }

    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            double otHours = Double.parseDouble(txtOtHours.getText());
            double ratePerHour = Double.parseDouble(txtRatePerHour.getText());
            double payment = otHours * ratePerHour;

            OtDTO dto = new OtDTO(
                    lblOtId.getText(),
                    cmbEID.getValue(),
                    otHours,
                    ratePerHour,
                    dateOt.getValue().toString(),
                    payment
            );

            if (otModel.saveOtRecord(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "OT record saved successfully!").show();
                loadOtRecords();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save OT record!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error saving OT record!").show();
            e.printStackTrace();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            double otHours = Double.parseDouble(txtOtHours.getText());
            double ratePerHour = Double.parseDouble(txtRatePerHour.getText());
            double payment = otHours * ratePerHour;

            OtDTO dto = new OtDTO(
                    lblOtId.getText(),
                    cmbEID.getValue(),
                    otHours,
                    ratePerHour,
                    dateOt.getValue().toString(),
                    payment
            );

            if (otModel.updateOtRecord(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "OT record updated successfully!").show();
                loadOtRecords();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating OT record!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            String otId = lblOtId.getText();
            if (otModel.deleteOtRecord(otId)) {
                new Alert(Alert.AlertType.INFORMATION, "OT record deleted successfully!").show();
                loadOtRecords();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error deleting OT record!").show();
            e.printStackTrace();
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }
}
