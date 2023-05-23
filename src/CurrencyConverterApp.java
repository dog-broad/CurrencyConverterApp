/*
    * This is the main class of the Currency Converter application.
    * It is responsible for connecting to the Open Exchange Rates API
    * and getting the exchange rate data.
 */

import java.util.Scanner;
import java.util.List;

public class CurrencyConverterApp {
    double amount;
    String fromCurrency;
    String toCurrency;
    double result;

    public static void main(String[] args) {
        // Try to connect to the Open Exchange Rates API
        try {
            OpenExchangeRatesConnector connector = new OpenExchangeRatesConnector();
            String exchangeRateData = connector.getExchangeRateData();

            if (exchangeRateData != null && !exchangeRateData.isEmpty()) {
                System.out.println("Connection to Open Exchange Rates API successful!");
                // Continue with currency conversion logic or further processing
                // Call the CurrencyConverter method to convert the currency
                CurrencyConverter();
            } else {
                System.out.println("Connection to Open Exchange Rates API failed!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method is responsible for converting the currency
    public static void CurrencyConverter() {
        OpenExchangeRatesConnector connector = new OpenExchangeRatesConnector();
        // Ask the user for the amount to convert
        // Ask the user for the currency to convert from
        // Ask the user for the currency to convert to
        // Call the CurrencyConverter method to convert the currency
        // Display the result
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the amount to convert: ");
        double amount = scanner.nextDouble();

        System.out.println("Enter the   currency to convert from: ");
        String fromCurrency = scanner.next();
        System.out.println("Enter the currency to convert to: ");
        String toCurrency = scanner.next();

        // Write the logic to convert the currency
        // Call the getExchangeRate method to get the exchange rate
        double exchangeRate = connector.getExchangeRate(fromCurrency, toCurrency);
        // Calculate the result
        double result = amount * exchangeRate;

        // Display the result
        System.out.println("The result is: " + result);
    }
}
