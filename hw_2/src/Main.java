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
    public int addProduct(String productName, String productDescription, int categoryId) {
        int createdProduct = -1;
        try {
            String query = "INSERT INTO Product (categoryId, name, description) VALUES (" + categoryId + ", '" + productName + "', '" + productDescription + "');";
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

    //Add or remove a payment method from Payment table
    public boolean customizePaymentMethod(int userId, String type, String cardNumber){

        boolean success = false;

        String userTypeQuery = "SELECT type FROM User WHERE userId = " + userId + ";";
        try (Statement userTypeStatement = connection.createStatement()){
            
            ResultSet userTypeResult = userTypeStatement.executeQuery(userTypeQuery);

            if(userTypeResult.next()){
                String userType = userTypeResult.getString("type");

                if(userType.equals("Customer")){
                    if(type.equals("add")){
                        String addPaymentMethod= "INSERT INTO Payment (userId, cardNumber) VALUES (" + userId + ", '" + cardNumber + "');";
                        try (Statement addPayment = connection.createStatement()){
                            addPayment.executeUpdate(addPaymentMethod);
                            success = true;

                        } catch (Exception e) {
                            System.out.println("Fallied to add new payment method.");
                            success = true;


                        }
                    }else if(userType.equals("remove")){
                        String removePaymentQuery = "DELETE FROM Payment WHERE userId = " + userId + " AND cardNumber = '" + cardNumber + "';";

                        try (Statement removePaymentStatement = connection.createStatement()) {
                            int rowsAffected = removePaymentStatement.executeUpdate(removePaymentQuery);
                            success = rowsAffected > 0;
                            System.out.println("Payment method successfully removed.");
                        }
                    }



                }else if(userType.equals("Seller")){
                    
                    success = false;
                    System.out.println("Provided userId was a seller.");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to customize the peyment method.");
        }

        return success;
    }
    
    

    //lists all users in the User table
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

    //lists all products in product table
    public ArrayList<Product> listAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Product;";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int productId = resultSet.getInt("productId");
                    String productName = resultSet.getString("name");
                    Product product = new Product(productId, productName);
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get product table from database.");
            e.printStackTrace();
        }
        return productList;
    }

    public ArrayList<String> listPaymentMethods(int userId) {
        ArrayList<String> paymentMethods = new ArrayList<>();
        try {
            String query = "SELECT * FROM Payment WHERE userId = " + userId;
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String cardNumber = resultSet.getString("cardNumber");
                    paymentMethods.add(cardNumber);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get payment methods from database.");
            e.printStackTrace();
        }
        return paymentMethods;
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

            int createdCustomer = main.addCustomer("Veli", "Dormitory 1");
            System.out.println("New Customer ID (Return of Function): " + createdCustomer);

            int createdProduct = main.addProduct("Book", "A book about JDBC", 1);
            System.out.println("New Product ID (Return of Function): " + createdProduct);

            boolean addTest = main.customizePaymentMethod(16, "add", "33454343");
            System.out.println(addTest);
            
            ArrayList<String> paymentMethods = main.listPaymentMethods(16);
            for (String cardNumber : paymentMethods) {
                System.out.println("Card Number: " + cardNumber);
            }

            /* 
            ArrayList<User> userList = main.listAllUsers();
            for (User user : userList) {
                System.out.println("UserID: " + user.getUserId() + ", " +"User Name: " + user.getName() + ", " + "User Type: " + user.getType() + ", " + "User Addres: "+ user.getAddress());
            }
            */

            /* 
            ArrayList<Product> productList = main.listAllProducts();
            for (Product product : productList) {
                System.out.println("ProductID: " + product.getProductId() + ", " +"Product Name: " + product.getName());
            }
           */

            main.closeConnection();
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
