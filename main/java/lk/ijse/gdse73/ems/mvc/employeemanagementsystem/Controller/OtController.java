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
    public Label lblOt;
    public TextField txtOtDate;
    public TextField txtEmployeeId;
    public TextField txtSalaryId;
    public TextField txtOtHours;
    public TextField txtRatePerHour;

    public TableView<OtTM> tblOtRecords;
    public TableColumn<OtTM, String> colOtId;
    public TableColumn<OtTM, String> colEmployeeId;
    public TableColumn<OtTM, String> colSalaryId;
    public TableColumn<OtTM, String> colOtHours;
    public TableColumn<OtTM, String> colRatePerHour;
    public TableColumn<OtTM, String> colOtDate;



    private final OtModel otModel = new OtModel();
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;


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
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<OtDTO> otArrayList = otModel.getAllOt();
        ObservableList<OtTM> otTMS = FXCollections.observableArrayList();

        for (OtDTO dto : otArrayList) {
            OtTM otTM = new OtTM(
                    dto.getOtId(),
                    dto.getEmployeeId(),
                    dto.getSalaryId(),
                    dto.getOtHours(),
                    dto.getRatePerHours(),
                    dto.getOtDate()
            );otTMS.add(otTM);
        }

        tblOtRecords.setItems(otTMS);
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
        String otId = txtOtId.getText();
        String employeeId = txtEmployeeId.getText();
        String salaryId = txtSalaryId.getText();
        String otHours = txtOtHours.getText();
        String ratePerHour = txtRatePerHour.getText();
        String otDate = dpOtDate.getValue() != null ? dpOtDate.getValue().format(DateTimeFormatter.ISO_DATE) : "";

        OtDTO dto = new OtDTO(otId, otHours, otDate, ratePerHour, employeeId, salaryId);

        try {
            boolean isSaved = otModel.saveOt(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "OT saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save OT record!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String otId = txtOtId.getText();
        String employeeId = txtEmployeeId.getText();
        String salaryId = txtSalaryId.getText();
        String otHours = txtOtHours.getText();
        String ratePerHour = txtRatePerHour.getText();
        String otDate = dpOtDate.getValue() != null ? dpOtDate.getValue().format(DateTimeFormatter.ISO_DATE) : "";

        OtDTO dto = new OtDTO(otId, otHours, otDate, ratePerHour, employeeId, salaryId);

        try {
            boolean isUpdated = otModel.updateOt(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "OT updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update OT!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String otId = txtOtId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = otModel.deleteOt(otId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "OT deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete OT!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page!").show();
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
