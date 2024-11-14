package ua.edu.chmnu.net_dev.c4.tcp.echo.client.MultiThreadedClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiThreadedClient {
    private final static int DEFAULT_PORT = 6710;
    private final static String DEFAULT_HOST = "localhost";

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            // Запит нікнейму
            System.out.print("Enter your nickname: ");
            String nickname = consoleReader.readLine();
            writer.println(nickname); // Надсилаємо нікнейм на сервер

            // Потік для обробки вхідних повідомлень від сервера
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed by server.");
                }
            }).start();

            // Потік для обробки введення повідомлень користувача
            while (true) {
                System.out.print("Enter message (or type 'exit' to quit): ");
                String userInput = consoleReader.readLine();
                if (userInput == null || userInput.equalsIgnoreCase("exit")) {
                    break; // Вихід, якщо користувач вводить "exit"
                }
                writer.println(userInput); // Відправляємо повідомлення на сервер
            }

            System.out.println("Client session ended.");
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}