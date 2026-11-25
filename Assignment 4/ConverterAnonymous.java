import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConverterAnonymous extends JFrame {

    JTextField amount = new JTextField(8);
    JComboBox<String> currency = new JComboBox<>(new String[]{"USD", "EUR"});
    JLabel result = new JLabel("Result");

    public ConverterAnonymous() {

        setTitle("Currency Converter");
        setLayout(new FlowLayout());

        JButton convert = new JButton("Convert");

        add(new JLabel("INR"));
        add(amount);
        add(currency);
        add(convert);
        add(result);


        convert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        amount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        pack();
        setVisible(true);
    }

    void convertCurrency() {
        try {
            double value = Double.parseDouble(amount.getText());
            double rate;

            if (currency.getSelectedItem().equals("USD")) {
                rate = 0.012;
            } else {
                rate = 0.011;
            }

            double converted = value * rate;
            result.setText(String.format("%.2f", converted));

        } catch (Exception e) {
            result.setText("Invalid amount");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConverterAnonymous());
    }
}