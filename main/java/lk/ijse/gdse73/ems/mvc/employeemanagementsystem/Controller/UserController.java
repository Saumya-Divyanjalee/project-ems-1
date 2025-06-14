// UserController.java
package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.UserDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.UserTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public Label lblUserId;
    public TextField txtUserName;
    public TextField txtMobile;
    public TextField txtEmail;
    public TextField txtRole;

    public TableView<UserTM> tblUser;
    public TableColumn<UserTM, String> colUserId;
    public TableColumn<UserTM, String> colUserName;
    public TableColumn<UserTM, String> colMobile;
    public TableColumn<UserTM, String> colEmail;
    public TableColumn<UserTM, String> colRole;


    private final UserModel userModel = new UserModel();
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String mobilePattern = "^\\d{10}$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("u_mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("u_email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("u_role"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops! Something went wrong").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<UserDTO> userDTOArrayList = userModel.getAllUsers();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDTO userDTO : userDTOArrayList) {
            UserTM userTM = new UserTM(
                    userDTO.getUser_id(),
                    userDTO.getUser_name(),
                    userDTO.getU_mobile(),
                    userDTO.getU_email(),
                    userDTO.getU_role()
            );
            userTMS.add(userTM);
        }
        tblUser.setItems(userTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
            btnReset.setDisable(true);

            txtUserName.setText("");
            txtMobile.setText("");
            txtEmail.setText("");
            txtRole.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops! Something went wrong.").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String name = txtUserName.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();
        String role = txtRole.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidMobile = mobile.matches(mobilePattern);

        txtUserName.setStyle("-fx-border-color: #7367F0;");
        txtEmail.setStyle("-fx-border-color: #7367F0;");
        txtMobile.setStyle("-fx-border-color: #7367F0;");

        if (!isValidName) txtUserName.setStyle("-fx-border-color: red;");
        if (!isValidEmail) txtEmail.setStyle("-fx-border-color: red;");
        if (!isValidMobile) txtMobile.setStyle("-fx-border-color: red;");

        UserDTO dto = new UserDTO(lblUserId.getText(), name, mobile, email, role);

        if (isValidName && isValidEmail && isValidMobile) {
            try {
                boolean isSaved = userModel.saveUser(dto);
                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "User saved successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save user.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to save user.").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        UserDTO dto = new UserDTO(
                lblUserId.getText(),
                txtUserName.getText(),
                txtMobile.getText(),
                txtEmail.getText(),
                txtRole.getText()
        );

        try {
            boolean isUpdated = userModel.updateUser(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update user.").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this user?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = userModel.deleteUser(lblUserId.getText());
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete user.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete user.").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = userModel.getNextUserId();
        lblUserId.setText(nextId);
    }

    public void onClickTable(MouseEvent event) {
        UserTM selected = tblUser.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblUserId.setText(selected.getUser_id());
            txtUserName.setText(selected.getUser_name());
            txtMobile.setText(selected.getU_mobile());
            txtEmail.setText(selected.getU_email());
            txtRole.setText(selected.getU_role());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }
    }
}
