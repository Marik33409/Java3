import java.sql.*;

public class Lesson2 {
    private Connection conn;
    private Statement stmt;
    private PreparedStatement ps;

    public Statement connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = conn.createStatement();
        return stmt;
    }

    public void disconnect () throws SQLException {
        stmt.close();
        conn.close();
    }

    public void crateTable() throws SQLException {
//        stmt.execute("DROP TABLE IF EXISTS Products;");
        stmt.execute("CREATE TABLE IF NOT EXISTS Products (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, prodid TEXT NOT NULL, title TEXT NOT NULL, cost DECIMAL(10,2));");
    }

    public void fillTable() throws SQLException {
        stmt.execute("DELETE FROM Products;");
        conn.setAutoCommit(false);
        ps = conn.prepareStatement("INSERT INTO Products (prodid, title, cost) VALUES (?,?,?);");
        for (int i = 0; i < 10000; i++) {
            ps.setString(1, "id_товара " + (i + 1));
            ps.setString(2, "товар" + (i + 1));
            ps.setDouble(3, (i + 1) * 10);
            ps.addBatch();
        }
        ps.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        ps.close();
    }

    public static void main(String[] args) {
        Lesson2 l2 = new Lesson2();
        try {
            l2.connect();
            l2.crateTable();
            l2.fillTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                l2.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
