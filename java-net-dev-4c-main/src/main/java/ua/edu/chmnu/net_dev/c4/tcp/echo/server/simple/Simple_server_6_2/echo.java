package ua.edu.chmnu.net_dev.c4.tcp.echo.server.simple.Simple_server_6_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class echo {

    private final static int DEFAULT_PORT = 6710;
    private static Map<String, Socket> clients = new HashMap<>();

    private static void processClient(Socket socket) {
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connection from: " + socket.getRemoteSocketAddress());

            out.println("Enter your nick:");
            String nick = in.readLine();
            clients.put(nick, socket);

            System.out.println("Client nick: " + nick);

            while (!socket.isClosed()) {
                out.println("Enter command (TEXT, FILE, or Q to quit):");
                String command = in.readLine();
                if ("Q".equalsIgnoreCase(command)) {
                    break;
                }

                switch (command.toUpperCase()) {
                    case "TEXT" -> {
                        out.println("Enter message:");
                        String message = in.readLine();
                        System.out.println("Received text from " + nick + ": " + message);
                    }
                    case "FILE" -> receiveAndForwardFile(nick, socket);
                    default -> out.println("Unknown command.");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            clients.remove(socket);
        }
    }

    private static void receiveAndForwardFile(String senderNick, Socket senderSocket) throws IOException {
        try (var inStream = senderSocket.getInputStream()) {
            var out = new PrintWriter(senderSocket.getOutputStream(), true);
            out.println("Enter recipient nick:");
            String recipientNick = new BufferedReader(new InputStreamReader(inStream)).readLine();
            Socket recipientSocket = clients.get(recipientNick);

            if (recipientSocket == null) {
                out.println("Recipient not connected.");
                return;
            }

            out.println("Enter file name:");
            String fileName = new BufferedReader(new InputStreamReader(inStream)).readLine();
            out.println("Start sending file data.");

            try (var fileOut = new BufferedOutputStream(recipientSocket.getOutputStream())) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);
                }
                fileOut.flush();
            }

            System.out.println("File " + fileName + " sent from " + senderNick + " to " + recipientNick);
        }
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        try (var serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                // Приймає клієнтів послідовно, без створення нових потоків
                Socket socket = serverSocket.accept();
                processClient(socket); // Обробка клієнта в основному потоці
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}