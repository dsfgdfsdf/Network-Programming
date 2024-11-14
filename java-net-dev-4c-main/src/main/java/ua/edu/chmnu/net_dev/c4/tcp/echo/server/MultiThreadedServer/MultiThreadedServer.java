package ua.edu.chmnu.net_dev.c4.tcp.echo.server.MultiThreadedServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedServer {
    private static final int PORT = 6710;
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(1000);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }


    public static void broadcastMessage(String message, ClientHandler sender) {
        String formattedMessage = sender.getNickname() + " says: " + message;
        for (ClientHandler client : clients) {
            if (client != sender && client.isConnected()) {
                client.sendMessage(formattedMessage);
            }
        }
    }


    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println(clientHandler.getNickname() + " has been removed from the online clients list.");
    }


    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String nickname;
        private boolean connected;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.connected = true;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                out.println("Enter your nickname: ");
                nickname = in.readLine();
                System.out.println(nickname + " has joined the chat.");


                broadcastMessage(nickname + " has joined the chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(nickname + " says: " + message);
                    // Форматування повідомлення перед його розсилкою іншим клієнтам
                    broadcastMessage(message, this);
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + e.getMessage());
            } finally {

                try {
                    connected = false;
                    in.close();
                    out.close();
                    clientSocket.close();
                    removeClient(this);
                    System.out.println(nickname + " has left the chat.");

                    broadcastMessage(nickname + " has left the chat.", this);
                } catch (IOException e) {
                    System.out.println("Error closing client connection: " + e.getMessage());
                }
            }
        }


        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }


        public boolean isConnected() {
            return connected;
        }


        public String getNickname() {
            return nickname;
        }
    }
}
