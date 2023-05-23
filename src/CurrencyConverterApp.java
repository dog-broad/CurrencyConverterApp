/*
    * This is the main class of the Currency Converter application.
    * It is responsible for connecting to the Open Exchange Rates API
    * and getting the exchange rate data.
 */

public class CurrencyConverterApp {
    public static void main(String[] args) {
        OpenExchangeRatesConnector connector = new OpenExchangeRatesConnector();
        String exchangeRateData = connector.getExchangeRateData();

        if (exchangeRateData != null && !exchangeRateData.isEmpty()) {
            System.out.println("Connection to Open Exchange Rates API successful!");
            // Continue with currency conversion logic or further processing
        } else {
            System.out.println("Connection to Open Exchange Rates API failed!");
            // Handle the case when the connection to the API fails
        }
    }
}
