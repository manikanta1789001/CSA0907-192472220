import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class MigrationTool {
    private static final String INSERT = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,boarding_stop,validity_date,amount) VALUES (?,?,?,?,?,?,?)";

    public static void migrate(Path input, Connection con) throws IOException, SQLException {
        List<String> failures = new ArrayList<>();
        boolean oldAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        int accepted = 0;
        int lineNo = 0;

        try (BufferedReader reader = Files.newBufferedReader(input);
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                String[] f = line.split(",", -1);
                if (f.length != 7) {
                    failures.add("Line " + lineNo + ": expected 7 fields");
                    continue;
                }
                try {
                    int route = Integer.parseInt(f[3]);
                    Date validity = Date.valueOf(f[5]);
                    double amount = Double.parseDouble(f[6]);
                    ps.setString(1, f[0]);
                    ps.setString(2, f[1]);
                    ps.setString(3, f[2]);
                    ps.setInt(4, route);
                    ps.setString(5, f[4]);
                    ps.setDate(6, validity);
                    ps.setDouble(7, amount);
                    ps.addBatch();
                    accepted++;
                    if (accepted % 1000 == 0) ps.executeBatch();
                } catch (IllegalArgumentException ex) {
                    failures.add("Line " + lineNo + ": invalid field - " + ex.getMessage());
                }
            }
            ps.executeBatch();
            con.commit();
        } catch (SQLException | IOException ex) {
            try { con.rollback(); } catch (SQLException rb) { ex.addSuppressed(rb); }
            throw ex;
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }

        System.out.println("Valid records submitted: " + accepted);
        System.out.println("Validation failures: " + failures.size());
        for (String failure : failures) System.out.println(failure);
    }

    static void printMetadata(Connection con) throws SQLException {
        DatabaseMetaData md = con.getMetaData();
        System.out.println("Database product: " + md.getDatabaseProductName());
        System.out.println("Database version: " + md.getDatabaseProductVersion());
        System.out.println("Driver: " + md.getDriverName());
        System.out.println("Driver version: " + md.getDriverVersion());
        try (ResultSet tables = md.getTables(con.getCatalog(), null, "%", new String[]{"TABLE"})) {
            System.out.println("Tables:");
            while (tables.next()) System.out.println(" - " + tables.getString("TABLE_NAME"));
        }
    }

    public static void main(String[] args) throws Exception {
        Path input = Path.of("sample_passes.csv");
        try (Connection con = DriverManager.getConnection(DbConfig.URL, DbConfig.USER, DbConfig.PASSWORD)) {
            printMetadata(con);
            migrate(input, con);
        }
    }
}
