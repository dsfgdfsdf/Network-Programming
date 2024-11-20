package Tcp_client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class Client1 {
    private static final int PORT = 12345;
    private static final String SERVER_ADDRESS = "localhost";
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JFrame frame;
    private JLabel imageLabel;

    public Client1() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            createGUI();


            new Thread(() -> listenForImages()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGUI() {
        frame = new JFrame("Client images1");
        JButton chooseButton = new JButton("Select an image");
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAndSendImage();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(chooseButton, BorderLayout.NORTH);
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private void chooseAndSendImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());
                out.writeObject(imageBytes);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void listenForImages() {
        try {
            while (true) {
                byte[] imageBytes = (byte[]) in.readObject();
                ImageIcon imageIcon = new ImageIcon(imageBytes);
                imageLabel.setIcon(imageIcon);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client1::new);
    }
}
