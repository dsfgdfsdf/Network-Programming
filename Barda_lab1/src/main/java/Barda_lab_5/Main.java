package Barda_lab_5;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String url = "https://example.com/search?query=java&lang=en";
        extractQueryParameters(url);
    }

    public static void extractQueryParameters(String url) {
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();
            Map<String, String> queryParams = new HashMap<>();

            // Split query parameters and add them to a map
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key = pair[0];
                String value = pair.length > 1 ? pair[1] : "";

                // Replace "java" with "джава" for "query" parameter
                if (key.equals("query") && value.equals("java")) {
                    value = "java   ";
                }

                queryParams.put(key, value);
            }


            queryParams.forEach((key, value) ->
                    System.out.println("● " + key + ": " + value)
            );

        } catch (URISyntaxException e) {
            System.out.println("Invalid URL format");
        }
    }
}
