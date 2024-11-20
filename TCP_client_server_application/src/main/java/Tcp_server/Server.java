package Tcp_server;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("The server is running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client has connected.");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());

                while (true) {

                    byte[] imageBytes = (byte[]) in.readObject();
                    System.out.println("Image received, sending to other clients...");


                    for (ClientHandler client : clients) {
                        if (client != this) {
                            client.sendImage(imageBytes);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        public void sendImage(byte[] imageBytes) {
            try {
                out.writeObject(imageBytes);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
