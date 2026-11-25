
import java.awt.*;
import java.awt.event.*;

public class Main extends Frame {

    private TextField regNo = new TextField(15);
    private TextField amount = new TextField(10);
    private Label status = new Label("Enter fee details.");

    private FeeService service = new FeeService();


    class FeeService {

        String pay(String reg, String amt) {

            if (reg.trim().isEmpty()) {
                return "Enter registration number.";
            }

            try {
                double value = Double.parseDouble(amt);

                if (value <= 0) {
                    return "Amount must be greater than zero.";
                }

                return "Payment successful! Rs. " + value;

            } catch (NumberFormatException ex) {
                return "Enter a valid amount.";
            }
        }
    }

    public Main() {
        super("Fee Payment");
        setLayout(new GridLayout(4, 2, 8, 8));

        add(new Label("Registration No:"));
        add(regNo);

        add(new Label("Amount:"));
        add(amount);

        Button pay = new Button("Pay");
        Button clear = new Button("Clear");
        Button receipt = new Button("Receipt");

        add(pay);
        add(clear);
        add(receipt);
        add(status);

        pay.addActionListener(e ->
            status.setText(service.pay(regNo.getText(), amount.getText()))
        );

        clear.addActionListener(e -> {
            regNo.setText("");
            amount.setText("");
            status.setText("Cleared.");
        });

        receipt.addActionListener(e ->
            status.setText("Receipt: Payment demo completed.")
        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(380, 180);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Main());
    }
}
