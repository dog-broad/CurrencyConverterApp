/*
    * This is the main class of the Currency Converter application.
    * It is responsible for connecting to the Open Exchange Rates API
    * and getting the exchange rate data.
 */

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.Currency;
import java.util.Locale;

public class CurrencyConverterApp {
    // Implement GUI using swing
    private JTextField amountTextField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JLabel resultLabel;

    // Create a new instance of the OpenExchangeRatesConnector class
    private final OpenExchangeRatesConnector openExchangeRatesConnector = new OpenExchangeRatesConnector();

    public CurrencyConverterApp(){
        // Make swing use dark theme
        JFrame.setDefaultLookAndFeelDecorated(true);
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());

            // Create a new JFrame
            JFrame frame = new JFrame("Currency Converter ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(350, 400);

            // Add the GUI components to the JFrame
            // amount label
            JLabel amountLabel = new JLabel("Amount");
            amountLabel.setBounds(50, 50, 100, 30);
            frame.add(amountLabel);

            // amount text field
            amountTextField = new JTextField();
            amountTextField.setBounds(150, 50, 100, 30);
            frame.add(amountTextField);

            // from currency label
            JLabel fromCurrencyLabel = new JLabel("From Currency");
            fromCurrencyLabel.setBounds(50, 100, 100, 30);
            frame.add(fromCurrencyLabel);

            // generate All currency list
            String[] allCurrencyList = openExchangeRatesConnector.getAllCurrencies();

            // from currency combo box
            fromCurrencyComboBox = new JComboBox<>(allCurrencyList);
            fromCurrencyComboBox.setBounds(150, 100, 100, 30);
            // Limit combo box to only show the first 5 items
            fromCurrencyComboBox.setMaximumRowCount(5);
            frame.add(fromCurrencyComboBox);

            // to currency label
            JLabel toCurrencyLabel = new JLabel("To Currency");
            toCurrencyLabel.setBounds(50, 150, 100, 30);
            frame.add(toCurrencyLabel);

            // to currency combo box
            toCurrencyComboBox = new JComboBox<>(allCurrencyList);
            toCurrencyComboBox.setBounds(150, 150, 100, 30);
            toCurrencyComboBox.setMaximumRowCount(5);
            frame.add(toCurrencyComboBox);

            // convert button
            JButton convertButton = new JButton("Convert");
            convertButton.setBounds(60, 200, 100, 30);
            frame.add(convertButton);

            // result label
            resultLabel = new JLabel();
            // centralize the result label
            resultLabel.setHorizontalAlignment(JLabel.CENTER);
            // set label bounds
            resultLabel.setBounds(0, 250, 350, 30);
            // set label font size
            resultLabel.setFont(resultLabel.getFont().deriveFont(20f));
            frame.add(resultLabel);

            // Add listener to the convert button
            convertButton.addActionListener(e -> {
                // check if amount text field is empty
                if (amountTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter an amount to convert");
                    return;
                }
                // Get the amount from the amount text field
                double amount = Double.parseDouble(amountTextField.getText());
                // Get the fromCurrency from the fromCurrency combo box
                String fromCurrency = (String) fromCurrencyComboBox.getSelectedItem();
                // Get the toCurrency from the toCurrency combo box
                String toCurrency = (String) toCurrencyComboBox.getSelectedItem();
                // Get the exchange rate from the OpenExchangeRatesConnector class
                double exchangeRate = openExchangeRatesConnector.getExchangeRate(fromCurrency, toCurrency);
                // Calculate the result
                double result = amount * exchangeRate;
                // Get the currency symbol for the toCurrency
                Currency currency = Currency.getInstance(toCurrency);
                String currencySymbol = currency.getSymbol(Locale.getDefault());
                // Get the currency symbol for the fromCurrency
                Currency currency2 = Currency.getInstance(fromCurrency);
                String currencySymbol2 = currency2.getSymbol(Locale.getDefault());

                // Display the result
                resultLabel.setText(String.format("%s %.2f = %s %.2f", currencySymbol2, amount, currencySymbol, result));
            });

            // Get the currency code selected in the fromCurrencyComboBox
            String fromCurrencyCode = (String) fromCurrencyComboBox.getSelectedItem();
            // Get the currency code selected in the toCurrencyComboBox
            String toCurrencyCode = (String) toCurrencyComboBox.getSelectedItem();

            // Fetch the flag icons for the currency codes
            ImageIcon fromCurrencyIcon = getFlagIcon(fromCurrencyCode);
            ImageIcon toCurrencyIcon = getFlagIcon(toCurrencyCode);

            // Create JLabels to display the flag icons
            JLabel fromCurrencyIconLabel = new JLabel(fromCurrencyIcon);
            fromCurrencyIconLabel.setBounds(260, 100, 30, 30);
            frame.add(fromCurrencyIconLabel);

            JLabel toCurrencyIconLabel = new JLabel(toCurrencyIcon);
            toCurrencyIconLabel.setBounds(260, 150, 30, 30);
            frame.add(toCurrencyIconLabel);

            // Create a Swap button in between the left of fromCurrencyComboBox and toCurrencyComboBox
            JButton swapButton = new JButton("Swap");
            swapButton.setBounds(170, 200, 100, 30);
            frame.add(swapButton);

            // Update the flag icons when the fromCurrencyComboBox selection changes
            fromCurrencyComboBox.addActionListener(e -> {
                // Get the currency code selected in the fromCurrencyComboBox
                String newFromCurrencyCode = (String) fromCurrencyComboBox.getSelectedItem();
                // Fetch the flag icon for the currency code
                ImageIcon newFromCurrencyIcon = getFlagIcon(newFromCurrencyCode);
                // Update the fromCurrencyIconLabel
                fromCurrencyIconLabel.setIcon(newFromCurrencyIcon);
            });

            // Update the flag icons when the toCurrencyComboBox selection changes
            toCurrencyComboBox.addActionListener(e -> {
                // Get the currency code selected in the toCurrencyComboBox
                String newToCurrencyCode = (String) toCurrencyComboBox.getSelectedItem();
                // Fetch the flag icon for the currency code
                ImageIcon newToCurrencyIcon = getFlagIcon(newToCurrencyCode);
                // Update the toCurrencyIconLabel
                toCurrencyIconLabel.setIcon(newToCurrencyIcon);
            });

            // Swap the fromCurrencyComboBox and toCurrencyComboBox selections when the swap button is clicked
            swapButton.addActionListener(e -> {
                // Get the currency code selected in the fromCurrencyComboBox
                String fromCurrencyCode1 = (String) fromCurrencyComboBox.getSelectedItem();
                // Get the currency code selected in the toCurrencyComboBox
                String toCurrencyCode1 = (String) toCurrencyComboBox.getSelectedItem();
                // Set the fromCurrencyComboBox selection to the toCurrencyComboBox selection
                fromCurrencyComboBox.setSelectedItem(toCurrencyCode1);
                // Set the toCurrencyComboBox selection to the fromCurrencyComboBox selection
                toCurrencyComboBox.setSelectedItem(fromCurrencyCode1);
                // call convert button action listener
                convertButton.doClick();
            });

            // If comboBox is selected and amount is entered, call convert button action listener
            fromCurrencyComboBox.addActionListener(e -> {
                if (!amountTextField.getText().isEmpty()) {
                    convertButton.doClick();
                }
            });
            toCurrencyComboBox.addActionListener(e -> {
                if (!amountTextField.getText().isEmpty()) {
                    convertButton.doClick();
                }
            });

            // Display the JFrame
            frame.setLayout(null);
            frame.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private ImageIcon getFlagIcon(String countryCode) {
        countryCode = getCountryCodeFromCurrency(countryCode);
        if (countryCode == null) {
            return getDefaultFlagIcon();
        }

        String flagUrl = "https://flagcdn.com/w20/" + countryCode.toLowerCase() + ".png";
        try {
            URL url = new URL(flagUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                Image image = ImageIO.read(inputStream);
                inputStream.close();

                System.out.println("Loaded flag icon from " + flagUrl + " successfully");
                return new ImageIcon(image);
            } else {
                System.out.println("Failed to load flag icon from " + flagUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getDefaultFlagIcon();
    }

    private ImageIcon getDefaultFlagIcon() {
        // Return a default flag icon
        URL defaultFlagUrl = getClass().getResource("default_flag.png");
        if (defaultFlagUrl != null) {
            try {
                Image defaultImage = ImageIO.read(defaultFlagUrl);
                return new ImageIcon(defaultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String getCountryCodeFromCurrency(String currencyCode) {
        if (currencyCode == null) {
            return null;
        }
        else if (currencyCode.equals("USD")){
            return "US";
        }
        // Get the country code from the currency code
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (locale.getCountry().equals("") && locale.getVariant().equals("")) {
                continue;
            }
            try {
                Currency currency = Currency.getInstance(locale);
                if (currencyCode.equals(currency.getCurrencyCode())) {
                    return locale.getCountry();
                }
            } catch (Exception e) {
                // Ignore unparseable locales
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverterApp();
            }
        });
    }
}
