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
            // Завантаження EmailSenderForm.fxml
            Parent emailSenderView = FXMLLoader.load(getClass().getResource("/view/EmailSenderForm.fxml"));

            // Створення нової сцени
            Scene emailSenderScene = new Scene(emailSenderView);

            // Отримання поточного вікна (Stage) і заміна сцени
            Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(emailSenderScene);
            primaryStage.setTitle("Email Sender with Attachments");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        hypGmail.setText(HomeFormController.gmail);
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
        if (event.getSource() instanceof javafx.scene.image.ImageView) {
            javafx.scene.image.ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
//            glow.setColor(Color.valueOf("#EF233C"));
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            icon.setEffect(glow);
        }
    }

    public void btnSendOnAction(MouseEvent event) {
        if (txtTitle.getText().equals("") || txtGmail.getText().equals("") || ariaMessage.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"please insert detail !").show();
        }else {
            boolean b = sendMail(txtTitle.getText(), ariaMessage.getText(), txtGmail.getText());
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION,"e-mail send !").show();
            }
        }
    }
    private boolean sendMail(String title,String message,String gmail){
        try {
            new SendText().sendMail(title,message,gmail);
            return true;
        } catch (IOException | MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }
}
