package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailController {

    @FXML
    private TextArea txtMessage;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTo;

    @Setter
    private String employeeEmail;

    @FXML
    void btnSendOnAction(ActionEvent event) {
        System.out.println(employeeEmail);
        String toMail = employeeEmail;
        String subject = txtSubject.getText();
        String message = txtMessage.getText();

        if (toMail == null || subject == null || message == null) {
            return;
        }

        String from = "saumyaz2020@gmail.com";
        String password = "vatkdzdqeyqbdizt";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            new Alert(Alert.AlertType.INFORMATION, "Mail Sent Successfully!").show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send Mail!").show();
            e.printStackTrace();
        }
    }


    public void setReceiverDetails(String email, String s) {
        this.employeeEmail = email;
        txtTo.setText(email);
        txtTo.setEditable(false);
    }
}
