import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {


    private Connection connection;

    public Main(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Failed to connect to the HW_2 database.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the connection.");
            e.printStackTrace();
        }
    }

    public int addSeller(String name, String address) {
        int createdSeller = -1;
        try {
            String query = "INSERT INTO User (name, type, address) VALUES ('" + name + "', 'Seller', '" + address + "');";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    createdSeller = generatedKeys.getInt(1);
                    System.out.println("New Seller ID(inside the function): " + createdSeller);
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to add seller.");
            e.printStackTrace();
        }
        return createdSeller;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HW_2";
        String username = "root";
        String password = "26052708";

        Main main = new Main(url, username, password);
        if (main.connection != null) {
            System.out.println("Connected to the database!");

            int createdSeller = main.addSeller("Ali", "Dormitory 2");
            System.out.println("New Seller ID (Return of Function): " + createdSeller);

            main.closeConnection();
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
