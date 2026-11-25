import java.sql.*;

public class SqlInjectionDemo {
    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection(DbConfig.URL, DbConfig.USER, DbConfig.PASSWORD)) {
            String normalId = "student1";
            String craftedId = "' OR '1'='1' -- ";
            String wrongPassword = "notThePassword";

            System.out.println("Unsafe query with crafted ID:");
            unsafeLogin(con, craftedId, wrongPassword);

            System.out.println("\nPreparedStatement with the same input:");
            safeLogin(con, craftedId, wrongPassword);
        }
    }

    static void unsafeLogin(Connection con, String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM demo_users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println(rs.next() ? "Login accepted (unsafe)" : "Login rejected");
        }
    }

    static void safeLogin(Connection con, String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM demo_users WHERE id=? AND pwd=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println(rs.next() ? "Login accepted" : "Login rejected (safe)");
            }
        }
    }
}
