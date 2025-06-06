package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeController {

    @FXML
    private ImageView imgQRCode;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtNetSalary;

    @FXML
    private TextArea txtReport;


    @Setter
    private Stage primaryStage;

    @FXML
    void generateQRCodeOnAction(ActionEvent event) {
        try {
            String empId = txtEmpId.getText().trim();
            String month = txtMonth.getText().trim();
            String netSalary = txtNetSalary.getText().trim();

            if(empId.isEmpty() || month.isEmpty() || netSalary.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please fill all fields!");
                return;
            }

            String qrText = "Employee ID: " + empId + "\nMonth: " + month + "\nNet Salary: " + netSalary;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            Image qrImage = SwingFXUtils.toFXImage(bufferedImage, null);

            imgQRCode.setImage(qrImage);
            txtReport.clear();

        } catch (WriterException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error generating QR code!");
        }
    }

    @FXML
    void scanQRCodeOnAction(ActionEvent event) {
        if (primaryStage == null) {
            showAlert(Alert.AlertType.ERROR, "Primary stage is not set!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open QR Code Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);

                txtReport.setText(result.getText());
            } catch (IOException | NotFoundException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Failed to scan QR Code!");
            } catch (ChecksumException e) {
                throw new RuntimeException(e);
            } catch (FormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String msg) {
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
