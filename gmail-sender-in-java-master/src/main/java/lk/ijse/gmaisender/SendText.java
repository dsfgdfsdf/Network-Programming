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
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class SendText {
    public static  String TEST_MAIL;
    private Gmail service;

    public SendText() throws GeneralSecurityException, IOException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory gsonFactory=GsonFactory.getDefaultInstance();
        service = new Gmail.Builder(HTTP_TRANSPORT, gsonFactory, getCredentials(HTTP_TRANSPORT,gsonFactory))
                .setApplicationName("sendmail")
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT,GsonFactory gsonFactory)
            throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(gsonFactory,
                new InputStreamReader(SendText.class.getResourceAsStream
                        ("/api/client_secret_43223520100-emdpjsjuiih9cjh593v6jgu38k38jo58.apps.googleusercontent.com (1).json"))); // enter your credential
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, gsonFactory, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");


    }
    public void sendMail(String subject, String massage,String TEST_MAIL) throws  IOException, MessagingException {
        this.TEST_MAIL=TEST_MAIL;


        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(TEST_MAIL));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(TEST_MAIL));
        email.setSubject(subject);
        email.setText(massage);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {

            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Draft id: " + msg.getId());
            System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to create draft: " + e.getDetails());
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
        email.setFrom(new InternetAddress("mukolabarda777@gmail.com")); // Замініть на свою пошту
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
        email.setSubject(subject);

        Multipart multipart = new MimeMultipart();


        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(messageBody, "text/html");
        multipart.addBodyPart(htmlPart);


        for (File file : attachments) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(file);
            multipart.addBodyPart(attachmentPart);
        }

        email.setContent(multipart);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);

        try {

            message = service.users().messages().send("me", message).execute();
            System.out.println("Message sent successfully! ID: " + message.getId());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            System.err.println("Error occurred: " + error.getMessage());
            throw e;
        }
    }

}