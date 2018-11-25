import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConsoleApp {
    private static String str;
    private static Statement stmt;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lesson2 l2 = new Lesson2();
        try {
            stmt = l2.connect();
            while (true) {
                System.out.print("Введите запрос (для выхода наберите /end): ");
                str = scanner.nextLine();
                if (str.equals("/end")) break;
                String[] query = str.split("\\s");
                try {
                    if (str.startsWith("/цена ") && query.length == 2) {
                        showPrice(query[1]);
                    } else if (str.startsWith("/сменитьцену ") && query.length == 3) {
                        changePrice(query[1], Double.parseDouble(query[2]));
                    } else if (str.startsWith("/товарыпоцене ") && query.length == 3) {
                        priceRange(Double.parseDouble(query[1]), Double.parseDouble(query[2]));
                    } else System.out.println("Неверный запрос");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный запрос");
                }
            }
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

    public static void showPrice(String title) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT cost FROM Products WHERE title = '" + title + "';");
        if (!rs.next()) {
            System.out.println("Такого товара нет");
            return;
        } else {
            System.out.println(rs.getDouble("cost"));
        }
    }

    public static void changePrice(String title, Double cost) throws SQLException {
        int a = stmt.executeUpdate("UPDATE Products SET cost = '" + cost + "' WHERE title = '" + title + "';");
        if (a == 1) System.out.println("Изменение произведено успешно");
        else System.out.println("Такого товара нет");
    }

    public static void priceRange(Double cost1, Double cost2) throws SQLException {
        double min, max;
        if (cost1 < cost2) {
            min = cost1;
            max = cost2;
        } else {
            min = cost2;
            max = cost1;
        }
        ResultSet rs = stmt.executeQuery("SELECT * FROM Products WHERE cost BETWEEN  '" + min + "' AND  '" + max + "';");
        if (!rs.next()) {
            System.out.println("В заданном ценовом диапазоне товары не найдены");
            return;
        }
        do {
            System.out.println(rs.getString("title") + " " + rs.getDouble("cost"));
        } while (rs.next());
    }
}
