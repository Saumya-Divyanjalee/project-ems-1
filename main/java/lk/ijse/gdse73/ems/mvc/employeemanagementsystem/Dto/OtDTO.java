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
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.OtModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OtController implements Initializable {

    public TextField txtOtId;
    public TextField txtEmployeeId;
    public TextField txtSalaryId;
    public TextField txtOtHours;
    public TextField txtRatePerHour;
    public DatePicker dpOtDate;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<OtTM> tblOtRecords;
    public TableColumn<OtTM, String> colOtId;
    public TableColumn<OtTM, String> colEmployeeId;
    public TableColumn<OtTM, String> colSalaryId;
    public TableColumn<OtTM, String> colOtHours;
    public TableColumn<OtTM, String> colRatePerHour;
    public TableColumn<OtTM, String> colOtDate;
    public Button btnReset;

    private final OtModel otModel = new OtModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOtId.setCellValueFactory(new PropertyValueFactory<>("otId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
        colRatePerHour.setCellValueFactory(new PropertyValueFactory<>("ratePerHours"));
        colOtDate.setCellValueFactory(new PropertyValueFactory<>("otDate"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading OT data").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<OtDTO> dtoList = otModel.getAllOt();
        ObservableList<OtTM> obList = FXCollections.observableArrayList();

        for (OtDTO dto : dtoList) {
            obList.add(new OtTM(
                    dto.getOtId(),
                    dto.getEmployeeId(),
                    dto.getSalaryId(),
                    dto.getOtHours(),
                    dto.getRatePerHours(),
                    dto.getOtDate()
            ));
        }

        tblOtRecords.setItems(obList);
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        txtOtId.setText(otModel.getNextOtId());
        txtEmployeeId.clear();
        txtSalaryId.clear();
        txtOtHours.clear();
        txtRatePerHour.clear();
        dpOtDate.setValue(null);

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        loadTableData();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtOtId.getText();
        String empId = txtEmployeeId.getText();
        String salaryId = txtSalaryId.getText();
        String hours = txtOtHours.getText();
        String rate = txtRatePerHour.getText();
        String date = dpOtDate.getValue() != null ? dpOtDate.getValue().format(DateTimeFormatter.ISO_DATE) : "";

        OtDTO dto = new OtDTO(id, hours, date, rate, empId, salaryId);

        try {
            boolean isSaved = otModel.saveOt(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "OT Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error during saving").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String id = txtOtId.getText();
        String empId = txtEmployeeId.getText();
        String salaryId = txtSalaryId.getText();
        String hours = txtOtHours.getText();
        String rate = txtRatePerHour.getText();
        String date = dpOtDate.getValue() != null ? dpOtDate.getValue().format(DateTimeFormatter.ISO_DATE) : "";

        OtDTO dto = new OtDTO(id, hours, date, rate, empId, salaryId);

        try {
            boolean isUpdated = otModel.updateOt(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "OT Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error during update").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String id = txtOtId.getText();

            try {
                boolean isDeleted = otModel.deleteOt(id);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "OT Deleted Successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Failed!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error during deletion").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error during reset").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        OtTM selected = tblOtRecords.getSelectionModel().getSelectedItem();

        if (selected != null) {
            txtOtId.setText(selected.getOtId());
            txtEmployeeId.setText(selected.getEmployeeId());
            txtSalaryId.setText(selected.getSalaryId());
            txtOtHours.setText(selected.getOtHours());
            txtRatePerHour.setText(selected.getRatePerHours());
            dpOtDate.setValue(java.time.LocalDate.parse(selected.getOtDate()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
