package lk.ijse.gmaisender.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.gmaisender.SendText;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class EmailSenderController {

    @FXML
    private TextField txtRecipients;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextArea txtContent;

    @FXML
    private ListView<String> fileListView;

    private final List<File> attachmentFiles = new ArrayList<>();
    private SendText sendText;

    public EmailSenderController() {
        try {

            sendText = new SendText();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            showError("Failed to initialize Gmail API.");
        }
    }

    @FXML
    public void attachFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            attachmentFiles.addAll(selectedFiles);
            for (File file : selectedFiles) {
                fileListView.getItems().add(file.getAbsolutePath());
            }
        }
    }

    @FXML
    void sendEmail() {
        String recipients = txtRecipients.getText();
        String title = txtTitle.getText();
        String content = txtContent.getText();

        if (recipients.isEmpty() || title.isEmpty() || content.isEmpty()) {
            showWarning("Please fill in all fields!");
            return;
        }

        try {
            sendMail(recipients, title, content, attachmentFiles);
            showConfirmation("Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to send email: " + e.getMessage());
        }
    }

    private void sendMail(String recipients, String title, String content, List<File> attachments)
            throws IOException, MessagingException {

        String[] recipientList = recipients.split(",");

        for (String recipient : recipientList) {
            sendText.sendMailWithAttachments(title, content, recipient.trim(), attachments);
        }
    }

    private void showWarning(String message) {
        new Alert(Alert.AlertType.WARNING, message).show();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void showConfirmation(String message) {
        new Alert(Alert.AlertType.CONFIRMATION, message).show();
    }
    @FXML
    public void goBack(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sender_form.fxml"));
            Parent root = loader.load();
            Scene senderFormScene = new Scene(root);


            Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(senderFormScene);
            primaryStage.setTitle("Sender Form");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load SenderForm.fxml").show();
        }
    }
}

