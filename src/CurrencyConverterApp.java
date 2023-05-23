/*
    * This is the main class of the Currency Converter application.
    * It is responsible for connecting to the Open Exchange Rates API
    * and getting the exchange rate data.
 */

import javax.swing.*;
// Import flatlaf library
import com.formdev.flatlaf.FlatDarkLaf;

public class CurrencyConverterApp {
    // Implement GUI using swing
    private JTextField amountTextField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JButton convertButton;
    private JLabel resultLabel;

    // Create a new instance of the OpenExchangeRatesConnector class
    private OpenExchangeRatesConnector openExchangeRatesConnector = new OpenExchangeRatesConnector();

    public CurrencyConverterApp(){
        // Make swing use dark theme
        JFrame.setDefaultLookAndFeelDecorated(true);
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());

            // Create a new JFrame
            JFrame frame = new JFrame("Currency Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 400);

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
            frame.add(fromCurrencyComboBox);

            // to currency label
            JLabel toCurrencyLabel = new JLabel("To Currency");
            toCurrencyLabel.setBounds(50, 150, 100, 30);
            frame.add(toCurrencyLabel);

            // to currency combo box
            toCurrencyComboBox = new JComboBox<>(allCurrencyList);
            toCurrencyComboBox.setBounds(150, 150, 100, 30);
            frame.add(toCurrencyComboBox);

            // convert button
            convertButton = new JButton("Convert");
            convertButton.setBounds(50, 200, 100, 30);
            frame.add(convertButton);

            // result label
            resultLabel = new JLabel();
            resultLabel.setBounds(50, 250, 100, 30);
            // set label font size
            resultLabel.setFont(resultLabel.getFont().deriveFont(20f));
            frame.add(resultLabel);

            // Add listener to the convert button
            convertButton.addActionListener(e -> {
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
                // Set the result label text
                resultLabel.setText(String.valueOf(result));
            });

            // Display the JFrame
            frame.setLayout(null);
            frame.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
        }

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
