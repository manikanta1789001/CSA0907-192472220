
import javax.swing.*;
import java.awt.*;

public class LibrarySearchScreen extends JFrame {

    JTextField searchBox;
    JComboBox<String> categoryBox;
    JTextArea results;
    JLabel status;

    public LibrarySearchScreen() {

        setTitle("Campus Library Search");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchBox = new JTextField(20);

        String[] categories = {"All", "Title", "Author", "Subject"};
        categoryBox = new JComboBox<>(categories);

        JButton searchButton = new JButton("Search");

        searchPanel.add(searchBox);
        searchPanel.add(categoryBox);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        results = new JTextArea();
        results.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(results);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton reserveButton = new JButton("Reserve");

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(reserveButton);

        status = new JLabel("Ready");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(status, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {

            String text = searchBox.getText();
            String category = categoryBox.getSelectedItem().toString();

            results.setText("Search results for: " + text);
            results.append("\nCategory: " + category);

            status.setText("Search completed");
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LibrarySearchScreen();
        });
    }
}
