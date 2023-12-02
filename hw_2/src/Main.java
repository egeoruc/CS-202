import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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



    //Add a new seller to the User table
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


    //Add a new customer to the User table
    public int addCustomer(String name, String address) {
    int createdCustomer = -1;
    try {
        String query = "INSERT INTO User (name, type, address) VALUES ('" + name + "', 'Customer', '" + address + "');";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdCustomer = generatedKeys.getInt(1);
                System.out.println("New Customer ID (inside the function): " + createdCustomer);
            }
        }

    } catch (SQLException e) {
        System.out.println("Failed to add a new customer to Customer table.");
        e.printStackTrace();
    }
    return createdCustomer;
    }

    //Add a new product to the Product table
    public int addProduct(String productName, String productDescription) {
        int createdProduct = -1;
        try {
            String query = "INSERT INTO Product (name, description) VALUES ('" + productName + "', '" + productDescription + "');";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
    
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    createdProduct = generatedKeys.getInt(1);
                    System.out.println("New Product ID (inside the function): " + createdProduct);
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Failed to add a new product to Product table.");
            e.printStackTrace();
        }
        return createdProduct;
    }
    

   
    public ArrayList<User> listAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            String query = "SELECT * FROM User;";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("userId");
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    String address = resultSet.getString("address");
                    User user = new User(id, name, type, address);
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user table from database.");
            e.printStackTrace();
        }
        return userList;
    }


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HW_2";
        String username = "root";
        String password = "268";

        Main main = new Main(url, username, password);
        if (main.connection != null) {
            System.out.println("Connected to the database!");

            int createdSeller = main.addSeller("Ali", "Dormitory 2");
            System.out.println("New Seller ID (Return of Function): " + createdSeller);

            int createdCustomer = main.addCustomer("Veli", "Dormitory 1");
            System.out.println("New Customer ID (Return of Function): " + createdCustomer);

            int createdProduct = main.addProduct("Book", "A book about JDBC");
            System.out.println("New Product ID (Return of Function): " + createdProduct);

            /* 
            ArrayList<User> userList = main.listAllUsers();
            for (User user : userList) {
                System.out.println("UserID: " + user.getUserId() + ", " +"User Name: " + user.getName() + ", " + "User Type: " + user.getType() + ", " + "User Addres: "+ user.getAddress());
            }
            */
           
            main.closeConnection();
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
