# Currency Converter App

The Currency Converter App is a Java application that allows users to convert currencies using real-time exchange rates obtained from the OpenExchangeRates API. The app provides a user-friendly graphical interface built with Swing.

## Features

- **Currency Conversion:** Users can enter an amount, select the source currency, and choose the target currency to convert. The app retrieves the latest exchange rates from the OpenExchangeRates API and calculates the converted amount.

- **Currency Selection:** The app provides a dropdown menu to select the source and target currencies from a list of available currencies. Users can easily choose the desired currencies for conversion.

- **Real-time Exchange Rates:** The app connects to the OpenExchangeRates API to fetch up-to-date exchange rates. This ensures accurate and reliable currency conversion.

- **Flag Icons:** The app displays flag icons next to the currency selection fields, providing visual cues to help users identify the chosen currencies.

- **Swap Functionality:** With a single click on the "Swap" button, users can interchange the source and target currencies. This feature enables quick conversion in both directions.

## Usage

Follow the steps below to run the Currency Converter App:

1. Ensure that you have Java Development Kit (JDK) installed on your system.

2. Download or clone the repository to your local machine.

3. Open a command prompt or terminal and navigate to the project directory.

4. Compile the source files using the following command:

   ```
   javac CurrencyConverterApp.java OpenExchangeRatesConnector.java
   ```

5. Run the application with the following command:

   ```
   java CurrencyConverterApp
   ```

6. The Currency Converter GUI will appear, allowing you to enter an amount, select source and target currencies, and convert between them.

7. Make sure you have an active internet connection for the app to fetch the latest exchange rates from the OpenExchangeRates API.

8. Enjoy using the Currency Converter App for all your currency conversion needs!

## Configuration

Before running the app, make sure to provide your OpenExchangeRates API key in the `api_key.txt` file located in the project directory. The API key should be placed on the second line of the file.

## Dependencies

The Currency Converter App relies on the following libraries:

- javax.swing: Provides the necessary classes for building the GUI.
- com.formdev.flatlaf: Applies the FlatDarkLaf theme to the Swing components.
- java.awt.Image: Used for handling images.
- java.io: Provides classes for input/output operations.
- java.net: Used for networking operations, including API requests.
- javax.imageio.ImageIO: Enables reading and writing of images.
- javax.swing.ImageIcon: Represents an image icon for Swing components.
- java.util.Currency: Provides currency-related functionalities.
- java.util.Locale: Used for obtaining the default locale information.

## Contributing

Contributions to the Currency Converter App are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request on the project's GitHub repository.

## License

The Currency Converter App is released under the [MIT License](LICENSE).

## Credits

The Currency Converter App was developed by [dog-broad](https://github.com/dog-broad). Special thanks to the creators of the OpenExchangeRates API for providing access to real-time exchange rate data.

