import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InsertBenchmark {
    static List<String[]> makeRows(int n) {
        List<String[]> rows = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            rows.add(new String[] {
                "B" + n + "_" + i, "REG" + i, "Student" + i, String.valueOf(i % 20 + 1),
                "Stop" + (i % 10), "2027-03-31", String.valueOf(2500.0 + (i % 1000))
            });
        }
        return rows;
    }

    static void clear(Connection c) throws SQLException {
        try (Statement s = c.createStatement()) { s.executeUpdate("DELETE FROM bus_pass"); }
    }

    static long timeStatement(Connection c, List<String[]> rows) throws SQLException {
        clear(c);
        long t = System.nanoTime();
        try (Statement s = c.createStatement()) {
            for (String[] p : rows) {
                String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,boarding_stop,validity_date,amount) VALUES ('"
                    + p[0] + "','" + p[1] + "','" + p[2] + "'," + p[3] + ",'" + p[4] + "','" + p[5] + "'," + p[6] + ")";
                s.executeUpdate(sql);
            }
        }
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t);
    }

    static long timePrepared(Connection c, List<String[]> rows) throws SQLException {
        clear(c);
        String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,boarding_stop,validity_date,amount) VALUES (?,?,?,?,?,?,?)";
        long t = System.nanoTime();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (String[] p : rows) {
                bind(ps, p);
                ps.executeUpdate();
            }
        }
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t);
    }

    static long timeBatch(Connection c, List<String[]> rows) throws SQLException {
        clear(c);
        long t = System.nanoTime();
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,boarding_stop,validity_date,amount) VALUES (?,?,?,?,?,?,?)")) {
            c.setAutoCommit(false);
            int i = 0;
            for (String[] p : rows) {
                bind(ps, p);
                ps.addBatch();
                if (++i % 1000 == 0) ps.executeBatch();
            }
            ps.executeBatch();
            c.commit();
        } finally {
            c.setAutoCommit(true);
        }
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t);
    }

    static void bind(PreparedStatement ps, String[] p) throws SQLException {
        ps.setString(1,p[0]); ps.setString(2,p[1]); ps.setString(3,p[2]);
        ps.setInt(4,Integer.parseInt(p[3])); ps.setString(5,p[4]);
        ps.setDate(6,Date.valueOf(p[5])); ps.setDouble(7,Double.parseDouble(p[6]));
    }

    static void inspect(Connection c) throws SQLException {
        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM bus_pass LIMIT 1")) {
            ResultSetMetaData md = rs.getMetaData();
            System.out.println("Columns: " + md.getColumnCount());
            for (int i=1; i<=md.getColumnCount(); i++)
                System.out.println(md.getColumnName(i) + " | " + md.getColumnTypeName(i));
        }
    }

    public static void main(String[] args) throws Exception {
        try (Connection c = DriverManager.getConnection(DbConfig.URL, DbConfig.USER, DbConfig.PASSWORD)) {
            for (int n : new int[] {1000, 10000, 100000}) {
                List<String[]> rows = makeRows(n);
                System.out.println("\\nRows: " + n);
                System.out.println("Statement ms: " + timeStatement(c, rows));
                System.out.println("PreparedStatement ms: " + timePrepared(c, rows));
                System.out.println("Batch+transaction ms: " + timeBatch(c, rows));
                inspect(c);
            }
        }
    }
}
