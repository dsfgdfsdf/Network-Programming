package Barda_lab_5;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] args) {

        String fileURL = "https://raw.githubusercontent.com/dsfgdfsdf/Network-Programming/refs/heads/main/README.md";
        String savePath = "C:\\Users\\Коля\\OneDrive\\Рабочий стол\\3курс\\4 курс\\Network Programming\\downloaded_README.md";

        downloadFile(fileURL, savePath);
    }

    public static void downloadFile(String fileURL, String savePath) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {

            URL url = new URL(fileURL);

            URLConnection urlConnection = url.openConnection();


            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            outputStream = new FileOutputStream(savePath);

            byte[] buffer = new byte[1024];
            int bytesRead;


            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File downloaded successfully: " + savePath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                System.out.println("Error closing streams: " + ex.getMessage());
            }
        }
    }
}
