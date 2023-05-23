import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

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

    public double getExchangeRate(String fromCurrency, String toCurrency) {
        double exchangeRate = 0;
        try {
            // Create URL
            URL url = new URL(API_URL_BASE + readAPIKey());

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(response.toString());

                // print the JSON response
                System.out.println(jsonObject.toString());


                // Get the "rates" object from the JSON response
                JSONObject ratesObject = jsonObject.getJSONObject("rates");
                // Get the exchange rate for the fromCurrency and toCurrency
                double fromRate = ratesObject.getDouble(fromCurrency);
                double toRate = ratesObject.getDouble(toCurrency);

                // Calculate the exchange rate
                exchangeRate = toRate / fromRate;
            } else {
                // Handle the error
                System.out.println("Error: " + responseCode);
            }
            // Disconnect the connection
            connection.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    public String[] getAllCurrencies() {
        String[] currencies = null;
        try {
            // Create URL
            URL url = new URL(API_URL_BASE + readAPIKey());
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set request method to GET
            connection.setRequestMethod("GET");
            // Get response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(response.toString());

                // print the JSON response
                System.out.println(jsonObject.toString());


                // Get the "rates" object from the JSON response
                JSONObject ratesObject = jsonObject.getJSONObject("rates");

                JSONArray names = ratesObject.names();
                currencies = new String[names.length()];

                for (int i = 0; i < names.length(); i++) {
                    currencies[i] = names.getString(i);
                }
            } else {
                // Handle the error
                System.out.println("Error: " + responseCode);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }
}