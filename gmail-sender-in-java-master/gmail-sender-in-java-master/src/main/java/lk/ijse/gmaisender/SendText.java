package lk.ijse.gmaisender;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SendText {

    private final Gmail service; // Gmail API клієнт
    private static final String APPLICATION_NAME = "Gmail API Java";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    public SendText() throws GeneralSecurityException, IOException {
        // Ініціалізація Gmail API
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory gsonFactory = GsonFactory.getDefaultInstance();
        this.service = new Gmail.Builder(HTTP_TRANSPORT, gsonFactory, getCredentials(HTTP_TRANSPORT, gsonFactory))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, GsonFactory gsonFactory)
            throws IOException {

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(gsonFactory,
                new InputStreamReader(SendText.class.getResourceAsStream("/api/client_secret_43223520100-emdpjsjuiih9cjh593v6jgu38k38jo58.apps.googleusercontent.com.json")));


        List<String> scopes = Arrays.asList(
                GmailScopes.GMAIL_SEND,
                GmailScopes.GMAIL_READONLY,
                GmailScopes.GMAIL_MODIFY
        );


        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, gsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get(TOKENS_DIRECTORY_PATH).toFile()))
                .setAccessType("offline")
                .build();


        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void sendMail(String subject, String messageBody, String recipientEmail) throws IOException, MessagingException {
        // Створення текстового листа
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress("mukolabarda777@gmail.com")); // Змініть на свою адресу
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
        email.setSubject(subject);
        email.setText(messageBody);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);


        try {
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message sent successfully! ID: " + message.getId());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Permission denied: " + error.getMessage());
            } else {
                throw e;
            }
        }
    }

    public void sendMailWithAttachments(String subject, String messageBody, String recipientEmail, List<File> attachments)
            throws IOException, MessagingException {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress("mukolabarda777@gmail.com")); // Змініть на свою адресу
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
        email.setSubject(subject);

        Multipart multipart = new MimeMultipart();


        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(messageBody, "text/plain");
        multipart.addBodyPart(textPart);


        for (File file : attachments) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(file);
            multipart.addBodyPart(attachmentPart);
        }

        email.setContent(multipart);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);


        try {
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message sent successfully with attachments! ID: " + message.getId());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            System.err.println("Error occurred: " + error.getMessage());
            throw e;
        }
    }

    public Gmail getService() {
        return service;
    }
}
