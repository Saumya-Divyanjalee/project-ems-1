package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EquipmentDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.EquipmentTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EquipmentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {
    public Label lblEquipment;
    public TextField txtEquipmentName;
    public TextField txtEmployeeId;

    public TableView<EquipmentTM> tblID;
    public TableColumn<EquipmentTM, String> colEqId;
    public TableColumn<EquipmentTM, String> colEqName;
    public TableColumn<EquipmentTM, String> colEmployeeId;



    private final EquipmentModel equipmentModel = new EquipmentModel();
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colEqId.setCellValueFactory(new PropertyValueFactory<>("equipmentId"));
        colEqName.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<EquipmentDTO> equipmentDTOArrayList = equipmentModel.getAllEquipment();
        ObservableList<EquipmentTM> equipmentTMS = FXCollections.observableArrayList();

        for (EquipmentDTO dto : equipmentDTOArrayList) {
            EquipmentTM  equipmentTM = new EquipmentTM(
                    dto.getEquipmentId(),
                    dto.getEquipmentName(),
                    dto.getEmployeeId()
            );
            equipmentTMS.add(equipmentTM);
        }

        tblID.setItems(equipmentTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtEquipmentName.setText("");
            txtEmployeeId.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }


    public void saveOnAction(ActionEvent actionEvent) {
        String id = lblEquipment.getText();
        String name = txtEquipmentName.getText();
        String empId = txtEmployeeId.getText();

        EquipmentDTO dto = new EquipmentDTO(id, name, empId);

        try {
            boolean isSaved = equipmentModel.saveEquipment(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Equipment saved successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save equipment.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving equipment.").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String id = lblEquipment.getText();
        String name = txtEquipmentName.getText();
        String empId = txtEmployeeId.getText();

        EquipmentDTO dto = new EquipmentDTO(id, name, empId);

        try {
            boolean isUpdated = equipmentModel.updateEquipment(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Equipment updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update equipment.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating equipment.").show();
        }
    }


    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String equipmentId = lblEquipment.getText();
            try {
                boolean isDeleted = equipmentModel.deleteEquipment(equipmentId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Equipment deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete equipment.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error while deleting equipment.").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }



    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = equipmentModel.getNextEquipmentId();
        lblEquipment.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        EquipmentTM selectedItem = tblID.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblEquipment.setText(selectedItem.getEquipmentId());
            txtEquipmentName.setText(selectedItem.getEquipmentName());
            txtEmployeeId.setText(selectedItem.getEmployeeId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
