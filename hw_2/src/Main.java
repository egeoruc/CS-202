import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HW_2";
        String username = "root";
        String password = "26052708";

       
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null){
               System.out.println("Connected to the database!");
               User user = new User(3, "Ege", "Seller", "Dorm 6");
               addUser(connection, user);
            }
            else{
                System.out.println("Failed to make connection!");
            }
            
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
    
    
    private static void addUser(Connection connection, User user) {
        try {

            String query = "INSERT INTO User VALUES (" + user.getUserId() + ", '" + user.getName() + "', '" + user.getType() + "', '" + user.getAddress() + "');";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);

            } 

        } catch (SQLException e){
                System.out.println("Failed to add user.");
                e.printStackTrace();
            }
    }
}

    
    


