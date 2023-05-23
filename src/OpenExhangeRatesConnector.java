import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

class OpenExchangeRatesConnector {
    private static final String API_KEY_FILE_PATH = "api_key.txt";
    private static final String API_URL_BASE = "https://openexchangerates.org/api/latest.json?app_id=";

    public String getExchangeRateData() {
        StringBuilder response = new StringBuilder();
        try {
            // Read the API key from the file
            String apiKey = readAPIKey();

            // Build the API URL
            String apiUrl = API_URL_BASE + apiKey;

            // Create a URL object with the API URL
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                // Handle the error
                System.out.println("Error: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    private String readAPIKey() throws IOException {
        // Read the API key from the file using getResourceAsStream() and InputStreamReader, since the file is in the resources folder
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(API_KEY_FILE_PATH)
                        )
                )
        );
        // Ignore the first line
        reader.readLine();
        // Read the API key from the second line
        String apiKey = reader.readLine();
        reader.close();
        return apiKey;
    }
}