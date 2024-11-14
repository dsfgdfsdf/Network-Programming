package ua.edu.chmnu.net_dev.c4.url.simple_downloader.FileDownloaderApp;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDownloaderApp extends JFrame {
    private JTextArea logArea;
    private JButton startButton;
    private JButton stopButton;
    private ExecutorService executorService;
    private List<DownloadTask> tasks;

    public FileDownloaderApp() {
        setTitle("File Downloader");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        startButton = new JButton("Start Download");
        stopButton = new JButton("Stop Download");
        stopButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        executorService = Executors.newFixedThreadPool(2);

        startButton.addActionListener(e -> startDownloads());
        stopButton.addActionListener(e -> stopDownloads());
    }

    private void startDownloads() {
        String[] urls = {
                "https://ash-speed.hetzner.com/100MB.bin",
                "https://ash-speed.hetzner.com/1GB.bin"
        };

        tasks = new ArrayList<>();

        for (String url : urls) {
            DownloadTask task = new DownloadTask(url, "download/");
            tasks.add(task);
            executorService.submit(task);
            logArea.append("Started download for: " + url + "\n");
        }

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void stopDownloads() {
        for (DownloadTask task : tasks) {
            task.cancel(true);
        }
        logArea.append("Downloads stopped by user.\n");
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private class DownloadTask extends SwingWorker<Void, String> {
        private final String fileUrl;
        private final String destinationDir;

        public DownloadTask(String fileUrl, String destinationDir) {
            this.fileUrl = fileUrl;
            this.destinationDir = destinationDir;
        }

        @Override
        protected Void doInBackground() {
            try {
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                File destinationFile = new File(destinationDir + fileName);

                if (destinationFile.exists()) {
                    HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
                    int remoteFileSize = connection.getContentLength();
                    if (destinationFile.length() == remoteFileSize) {
                        publish("File " + fileName + " already exists with the same content. Skipping download.");
                        return null;
                    }
                }

                HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
                connection.setRequestProperty("Range", "bytes=" + destinationFile.length() + "-");

                int totalSize = connection.getContentLength();
                long startTime = System.currentTimeMillis();

                try (InputStream in = connection.getInputStream();
                     OutputStream out = new BufferedOutputStream(Files.newOutputStream(destinationFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    int downloadedSize = (int) destinationFile.length();
                    int lastDownloadedSize = downloadedSize;

                    while ((bytesRead = in.read(buffer)) != -1) {
                        if (isCancelled()) { // Перевірка на скасування завантаження
                            publish("Download of " + fileName + " stopped.");
                            break;
                        }
                        out.write(buffer, 0, bytesRead);
                        downloadedSize += bytesRead;

                        // Розрахунок швидкості завантаження та прогресу
                        long currentTime = System.currentTimeMillis();
                        double elapsedTimeSec = (currentTime - startTime) / 1000.0;
                        double speedKbps = (downloadedSize - lastDownloadedSize) / 1024.0 / elapsedTimeSec;
                        lastDownloadedSize = downloadedSize;
                        int progress = (int) (((double) downloadedSize / totalSize) * 100);

                        String logMessage = String.format("%s – %.1f MiB/%.1f MiB [%d%%, %.1f KiB/s]",
                                fileName,
                                downloadedSize / (1024.0 * 1024.0),
                                totalSize / (1024.0 * 1024.0),
                                progress,
                                speedKbps);
                        publish(logMessage);

                        startTime = currentTime;
                    }
                }

                if (!isCancelled()) {
                    publish("Download completed: " + fileName);
                }

            } catch (Exception e) {
                publish("Error downloading " + fileUrl + ": " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String message : chunks) {
                logArea.append(message + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileDownloaderApp app = new FileDownloaderApp();
            app.setVisible(true);
        });
    }
}
