package lk.ijse.gmaisender.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gmaisender.SendText;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SenderFormController {
    public TextField txtTitle;
    @FXML
    private Hyperlink hypGmail;
    @FXML
    private TextArea ariaMessage;
    @FXML
    private TextField txtGmail;
    @FXML
    private ImageView btnSend;

    @FXML
    public void goToEmailSender(ActionEvent event) {
        try {

            Parent emailSenderView = FXMLLoader.load(getClass().getResource("/view/EmailSenderForm.fxml"));
            Scene emailSenderScene = new Scene(emailSenderView);


            Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(emailSenderScene);
            primaryStage.setTitle("Email Sender with Attachments");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load EmailSenderForm.fxml").show();
        }
    }

    @FXML
    public void goToInboxChecker(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InboxChecker.fxml"));
            Parent root = loader.load();
            Scene inboxCheckerScene = new Scene(root);


            Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(inboxCheckerScene);
            primaryStage.setTitle("Inbox Checker");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load InboxChecker.fxml").show();
        }
    }

    @FXML
    void initialize() {
        if (HomeFormController.gmail != null && !HomeFormController.gmail.isEmpty()) {
            hypGmail.setText(HomeFormController.gmail);
        } else {
            hypGmail.setText("No Gmail linked");
        }
    }

    public void mouseExit(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    public void mouseEnter(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            icon.setEffect(glow);
        }
    }

    public void btnSendOnAction(MouseEvent event) {
        if (txtTitle.getText().isEmpty() || txtGmail.getText().isEmpty() || ariaMessage.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
        } else {
            boolean success = sendMail(txtTitle.getText(), ariaMessage.getText(), txtGmail.getText());
            if (success) {
                new Alert(Alert.AlertType.CONFIRMATION, "E-mail sent successfully!").show();
            }
        }
    }

    private boolean sendMail(String title, String message, String gmail) {
        try {
            new SendText().sendMail(title, message, gmail);
            return true;
        } catch (IOException | MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send e-mail: " + e.getMessage()).show();
            return false;
        }
    }
}
