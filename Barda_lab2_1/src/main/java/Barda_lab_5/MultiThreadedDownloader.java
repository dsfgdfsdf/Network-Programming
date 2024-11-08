package Barda_lab_5;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedDownloader {

    private static final int THREAD_COUNT = 4;
    private static final String fileURL = "https://raw.githubusercontent.com/dsfgdfsdf/Network-Programming/refs/heads/main/Barda_lab2/src/main/java/Barda_lab_5/Main.java";
    private static final String savePath = "C:\\Users\\Коля\\OneDrive\\Рабочий стол\\3курс\\4 курс\\Network Programming\\downloaded_Main.java";

    public static void main(String[] args) {
        try {
            new MultiThreadedDownloader().downloadFile(fileURL, savePath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void downloadFile(String fileURL, String savePath) throws Exception {
        URL url = new URL(fileURL);
        URLConnection connection = url.openConnection();
        int fileSize = connection.getContentLength();

        if (fileSize <= 0) {
            throw new IOException("File size is zero or unavailable.");
        }

        System.out.println("File size: " + fileSize + " bytes");


        int partSize = fileSize / THREAD_COUNT;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);


        for (int i = 0; i < THREAD_COUNT; i++) {
            int startByte = i * partSize;
            int endByte = (i == THREAD_COUNT - 1) ? fileSize : startByte + partSize - 1;
            executor.execute(new DownloadTask(fileURL, savePath + ".part" + i, startByte, endByte));
        }


        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


        mergeFiles(savePath);
        System.out.println("File downloaded successfully: " + savePath);
    }


    static class DownloadTask implements Runnable {
        private final String fileURL;
        private final String savePath;
        private final int startByte;
        private final int endByte;

        public DownloadTask(String fileURL, String savePath, int startByte, int endByte) {
            this.fileURL = fileURL;
            this.savePath = savePath;
            this.startByte = startByte;
            this.endByte = endByte;
        }

        @Override
        public void run() {
            try (BufferedInputStream in = new BufferedInputStream(openConnection().getInputStream());
                 RandomAccessFile writer = new RandomAccessFile(savePath, "rw")) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                writer.seek(0);
                while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                    writer.write(buffer, 0, bytesRead);
                }

                System.out.println("Downloaded part: " + savePath);
            } catch (IOException e) {
                System.out.println("Error downloading part " + savePath + ": " + e.getMessage());
            }
        }

        private URLConnection openConnection() throws IOException {
            URL url = new URL(fileURL);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
            return connection;
        }
    }


    private void mergeFiles(String savePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            for (int i = 0; i < THREAD_COUNT; i++) {
                String partFileName = savePath + ".part" + i;
                try (FileInputStream fis = new FileInputStream(partFileName)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }

                new File(partFileName).delete();
            }
        }
    }
}
