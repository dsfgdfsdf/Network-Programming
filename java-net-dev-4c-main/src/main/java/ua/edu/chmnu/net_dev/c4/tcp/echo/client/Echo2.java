package ua.edu.chmnu.net_dev.c4.tcp.echo.client;

import ua.edu.chmnu.net_dev.c4.tcp.core.client.EndPoint;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Echo2 {
    private final static int DEFAULT_PORT = 6710;

    public static void main(String[] args) throws IOException {
        EndPoint endPoint;

        if (args.length > 0) {
            endPoint = new EndPoint(args[0]);
        } else {
            endPoint = new EndPoint("localhost", DEFAULT_PORT);
        }

        try (Socket clientSocket = new Socket(endPoint.getHost(), endPoint.getPort())) {

            System.out.println("Establish connection to " + endPoint.getHost() + ":" + endPoint.getPort());

            try (
                    var scanner = new Scanner(System.in);
                    var writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                // Enter Nickname
                String promptNick = reader.readLine();
                System.out.print(promptNick);
                var nick = scanner.nextLine();
                writer.println(nick);

                while (!clientSocket.isClosed()) {
                    System.out.println("Enter command (TEXT, FILE, or Q to quit):");
                    String command = scanner.nextLine();

                    if (command.equalsIgnoreCase("Q")) {
                        System.out.println("Disconnecting...");
                        break;
                    }

                    writer.println(command);

                    if (command.equalsIgnoreCase("TEXT")) {
                        handleTextMessage(scanner, writer, reader);
                    } else if (command.equalsIgnoreCase("FILE")) {
                        handleFileTransfer(scanner, writer, clientSocket);
                    } else {
                        System.out.println("Unknown command.");
                    }
                }
            }
        }
    }

    private static void handleTextMessage(Scanner scanner, PrintWriter writer, BufferedReader reader) throws IOException {
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        writer.println(message);

        System.out.println("Waiting for response...");
        String response = reader.readLine();
        System.out.println("Received response: " + response);
    }

    private static void handleFileTransfer(Scanner scanner, PrintWriter writer, Socket clientSocket) throws IOException {
        System.out.print("Enter recipient nick: ");
        String recipientNick = scanner.nextLine();
        writer.println(recipientNick);

        System.out.print("Enter path to image file: ");
        String filePath = scanner.nextLine();
        writer.println(filePath);

        // Send file to the server
        File file = new File(filePath);
        try (var fileInput = new FileInputStream(file);
             var socketOut = clientSocket.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            System.out.println("Sending file...");
            while ((bytesRead = fileInput.read(buffer)) != -1) {
                socketOut.write(buffer, 0, bytesRead);
            }
            socketOut.flush();
            System.out.println("File sent successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
