//package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
//import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.OtTM;
//import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
//import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.OtModel;
//
//import java.net.URL;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ResourceBundle;
//
//public class OtController implements Initializable {
//
//    public Label lblOtId;
//    public ComboBox<String> cmbEID;
//    public TextField txtOtHours;
//    public TextField txtRatePerHour;
//    public DatePicker dateOt;
//
//    public TableView<OtTM> tblOtRecords;
//    public TableColumn<OtTM, String> colOtId;
//    public TableColumn<OtTM, String> colEmployeeId;
//    public TableColumn<OtTM, String> colOtHours;
//    public TableColumn<OtTM, String> colRatePerHour;
//    public TableColumn<OtTM, String> colOtDate;
//    public TableColumn<OtTM, String> colPayment;
//
//    public Button btnSave;
//    public Button btnUpdate;
//    public Button btnDelete;
//    public Button btnReset;
//
//    private final OtModel otModel = new OtModel();
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        setCellValueFactory();
//        loadOtRecords();
//
//        try {
//            cmbEID.setItems(EmployeeModel.getAllEmployeeid());
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Error loading employee IDs!").show();
//        }
//
//        resetPage();
//    }
//
//    private void setCellValueFactory() {
//        colOtId.setCellValueFactory(new PropertyValueFactory<>("otId"));
//        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
//        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
//        colRatePerHour.setCellValueFactory(new PropertyValueFactory<>("ratePerHours"));   // Fixed property name: ratePerHours
//        colOtDate.setCellValueFactory(new PropertyValueFactory<>("otDate"));
//        colPayment.setCellValueFactory(new PropertyValueFactory<>("overtimePayment"));    // Fixed property name: overtimePayment
//    }
//
//    private void loadOtRecords() {
//        ObservableList<OtTM> list = FXCollections.observableArrayList();
//        for (OtDTO dto : otModel.getAllOtRecords()) {
//            double payment = dto.getOtHours() * dto.getRatePerHour();
//            list.add(new OtTM(
//                    dto.getOtId(),
//                    dto.getEmployeeId(),
//                    String.valueOf(dto.getOtHours()),
//                    String.valueOf(dto.getRatePerHour()),
//                    dto.getOtDate().toString(),
//                    payment  // pass double directly
//            ));
//        }
//        tblOtRecords.setItems(list);
//    }
//
//    public void btnSaveOnAction(ActionEvent event) {
//        try {
//            OtDTO dto = new OtDTO(
//                    lblOtId.getText(),
//                    cmbEID.getValue(),
//                    Double.parseDouble(txtOtHours.getText()),
//                    Double.parseDouble(txtRatePerHour.getText()),
//                    dateOt.getValue().toString(), // ✅ convert date to String
//                    Double.parseDouble(txtOtHours.getText()) * Double.parseDouble(txtRatePerHour.getText()) // ✅ 6th value: payment
//            );
//
//            if (otModel.saveOtRecord(dto)) {
//                new Alert(Alert.AlertType.INFORMATION, "Saved!").show();
//                loadOtRecords();
//                resetPage();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Failed to save!").show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
//        }
//    }
//
//    public void btnUpdateOnAction(ActionEvent event) {
//        try {
//            OtDTO dto = new OtDTO(
//                    lblOtId.getText(),
//                    cmbEID.getValue(),
//                    Double.parseDouble(txtOtHours.getText()),
//                    Double.parseDouble(txtRatePerHour.getText()),
//                    dateOt.getValue().toString(), // ✅ convert date to String
//                    Double.parseDouble(txtOtHours.getText()) * Double.parseDouble(txtRatePerHour.getText()) // ✅ 6th value: payment
//            );
//
//            if (otModel.updateOtRecord(dto)) {
//                new Alert(Alert.AlertType.INFORMATION, "Updated!").show();
//                loadOtRecords();
//                resetPage();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Update failed!").show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
//        }
//    }
//
//    public void btnDeleteOnAction(ActionEvent event) {
//        String otId = lblOtId.getText();
//        if (otModel.deleteOtRecord(otId)) {
//            new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
//            loadOtRecords();
//            resetPage();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
//        }
//    }
//
//    public void btnResetOnAction(ActionEvent event) {
//        resetPage();
//    }
//
//    private void resetPage() {
//        lblOtId.setText(otModel.generateNextOtId());
//        cmbEID.setValue(null);
//        txtOtHours.clear();
//        txtRatePerHour.clear();
//        dateOt.setValue(null);
//
//        btnSave.setDisable(false);
//        btnUpdate.setDisable(true);
//        btnDelete.setDisable(true);
//    }
//
//    public void onRowClick(MouseEvent event) {
//        OtTM selected = tblOtRecords.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            lblOtId.setText(selected.getOtId());
//            cmbEID.setValue(selected.getEmployeeId());
//            txtOtHours.setText(selected.getOtHours());
//            txtRatePerHour.setText(selected.getRatePerHours());  // Fixed: getRatePerHours()
//            dateOt.setValue(LocalDate.parse(selected.getOtDate()));
//
//            btnSave.setDisable(true);
//            btnUpdate.setDisable(false);
//            btnDelete.setDisable(false);
//        }
//    }
//}
