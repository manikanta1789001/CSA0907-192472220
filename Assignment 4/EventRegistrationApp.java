import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EventRegistrationApp extends JFrame {

    JTextField studentId = new JTextField(12);
    JComboBox<String> event = new JComboBox<>(
        new String[]{"Tech Talk", "Cultural Fest", "Workshop"}
    );

    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Student ID", "Event"}, 0
    );
    JTable table = new JTable(model);
    JLabel status = new JLabel("Ready");

    String url = "jdbc:mysql://localhost:3306/campus_events";
    String user = "root", password = "password";

    public EventRegistrationApp() {
        super("Campus Event Registration");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel();
        JButton register = new JButton("Register");
        JButton filter = new JButton("Filter");
        JButton export = new JButton("Export CSV");

        form.add(new JLabel("Student ID"));
        form.add(studentId);
        form.add(event);
        form.add(register);
        form.add(filter);
        form.add(export);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> dispose());
        menu.add(exit);
        bar.add(menu);
        setJMenuBar(bar);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        register.addActionListener(e -> register());
        filter.addActionListener(e -> load(event.getSelectedItem().toString()));
        export.addActionListener(e -> exportCsv());

        load(null);
        setSize(720, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void register() {
        String id = studentId.getText().trim();
        String ev = event.getSelectedItem().toString();

        if (id.isEmpty()) {
            status.setText("Student ID is required.");
            return;
        }

        new SwingWorker<String, Void>() {
            protected String doInBackground() throws Exception {
                try (Connection c = DriverManager.getConnection(url, user, password)) {

                    PreparedStatement check = c.prepareStatement(
                        "SELECT 1 FROM registrations WHERE student_id=? AND event_name=?"
                    );
                    check.setString(1, id);
                    check.setString(2, ev);

                    ResultSet rs = check.executeQuery();
                    if (rs.next())
                        return "Already registered.";

                    PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO registrations(student_id,event_name) VALUES (?,?)"
                    );
                    ps.setString(1, id);
                    ps.setString(2, ev);
                    ps.executeUpdate();

                    return "Registration successful.";
                }
            }

            protected void done() {
                try {
                    status.setText(get());
                    load(null);
                } catch (Exception e) {
                    status.setText("Error: " + e.getMessage());
                }
            }
        }.execute();
    }

    void load(String selected) {
        model.setRowCount(0);

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() throws Exception {
                try (Connection c = DriverManager.getConnection(url, user, password)) {

                    String sql = "SELECT student_id,event_name FROM registrations";
                    if (selected != null)
                        sql += " WHERE event_name=?";

                    PreparedStatement ps = c.prepareStatement(sql);
                    if (selected != null)
                        ps.setString(1, selected);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next())
                        model.addRow(new Object[]{rs.getString(1), rs.getString(2)});
                } catch (Exception e) {
                    status.setText("Load error: " + e.getMessage());
                }
                return null;
            }
        }.execute();
    }

    void exportCsv() {
        try (java.io.PrintWriter out = new java.io.PrintWriter("registrations.csv")) {
            for (int i = 0; i < model.getRowCount(); i++)
                out.println(model.getValueAt(i, 0) + "," + model.getValueAt(i, 1));

            status.setText("CSV Exported.");
        } catch (Exception e) {
            status.setText("Export error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventRegistrationApp());
    }
}