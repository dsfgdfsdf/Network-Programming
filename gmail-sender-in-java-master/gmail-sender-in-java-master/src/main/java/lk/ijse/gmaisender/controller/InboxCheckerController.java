package lk.ijse.gmaisender.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lk.ijse.gmaisender.SendText;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Timer;
import java.util.TimerTask;

public class InboxCheckerController {

    @FXML
    private ListView<String> inboxListView;

    private SendText sendText;

    public InboxCheckerController() {
        try {
            sendText = new SendText();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            showError("Failed to initialize Gmail API.");
        }
    }

    @FXML
    public void initialize() {
        startPeriodicCheck(60);}


    private void checkInbox() {
        try {

            var messagesResponse = sendText.getService().users().messages().list("me")
                    .setQ("is:unread")
                    .execute();

            if (messagesResponse.getMessages() == null || messagesResponse.getMessages().isEmpty()) {
                System.out.println("There are no new leaves.");
                return;
            }

            for (var message : messagesResponse.getMessages()) {
                var fullMessage = sendText.getService().users().messages()
                        .get("me", message.getId())
                        .execute();

                String sender = "No name";
                String subject = "None Tems";

                for (var header : fullMessage.getPayload().getHeaders()) {
                    if (header.getName().equalsIgnoreCase("From")) {
                        sender = header.getValue();
                    }
                    if (header.getName().equalsIgnoreCase("Subject")) {
                        subject = header.getValue();
                    }
                }

                String emailInfo = "Sender: " + sender + ", Tem: " + subject;


                Platform.runLater(() -> inboxListView.getItems().add(emailInfo));


                sendText.getService().users().messages().modify("me", message.getId(),
                                new com.google.api.services.gmail.model.ModifyMessageRequest()
                                        .setRemoveLabelIds(java.util.List.of("UNREAD")))
                        .execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Platform.runLater(() -> showError("Exeption at the hour of re-verification: " + e.getMessage()));
        }
    }


    private void startPeriodicCheck(int interval) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkInbox();
            }
        }, 0, interval * 1000L);
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }
    @FXML
    private void refreshInbox(ActionEvent event) {
        checkInbox();
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