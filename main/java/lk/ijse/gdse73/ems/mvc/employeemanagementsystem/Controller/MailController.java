package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
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
        String password = "vatk dzdq eyqb dizt";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password.toCharArray());

            }


        };
        Session session = Session.getInstance(props);
        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            new Alert(Alert.AlertType.INFORMATION, "Mail Sent Successfully!").show();


        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to send Mail...!").show();
            e.printStackTrace();
        }


    }

}
