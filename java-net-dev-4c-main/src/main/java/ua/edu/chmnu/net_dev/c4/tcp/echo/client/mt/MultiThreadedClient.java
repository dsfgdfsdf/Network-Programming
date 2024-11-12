package ua.edu.chmnu.net_dev.c4.tcp.echo.client.mt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedClient {
    private final static int DEFAULT_PORT = 6710;
    private final static String DEFAULT_HOST = "localhost";
    private final static int CLIENT_SESSIONS = 1000;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < CLIENT_SESSIONS; i++) {
            executorService.submit(() -> {
                try {
                    runClientSession();
                } catch (IOException e) {
                    System.out.println("Error in client session: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("All client sessions completed.");
    }

    private static void runClientSession() throws IOException {
        try (Socket clientSocket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {


            String randomString = generateRandomString();
            long startTime = System.currentTimeMillis();


            writer.println(randomString);


            String response = reader.readLine();
            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;
            System.out.println("Sent: " + randomString + " | Received: " + response + " | Duration: " + duration + " ms");
        }
    }

    private static String generateRandomString() {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        int targetStringLength = 10;
        Random random = new Random();

        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
