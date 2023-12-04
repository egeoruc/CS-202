import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;



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



    /*This method is for adding new seller to User table
    Since the userId key is auto incremented. If we try to insert a new value wo. userId it will
    automoticly generate a key. We do this then return the genereted key.
     */
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


    /*This method is for adding new customer to User table
    Since the userId key is auto incremented. If we try to insert a new value wo. userId it will
    automoticly generate a key. We do this then return the genereted key.
     */
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

    /*In this method we are adding new peoduct to product table.
     * Since the productId is auto incremented sql will generate the productId and we return that genereted key.
     */
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

    /*this method is for custumization the payment method. Logic of the method is goes like this: 
     -first it checks the is the given user is a customer or seller.
     -if the user type is not customer it directly return false.
     -if the user type is customer then we check for the given paramater type rather 'add' or 'remove'
     -for the add part we just insert the payment table end return true
     -for the remove part we first check is there a such a method with given cardNumber if not we return immediately false
     if there is such a method then we delete that   
     */
    public boolean addRemovePaymentMethod(int userId, String type, String cardNumber){

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

    /* This method is for adding/romoving something to category table
    -our method first chechks if the given type is add or remove
    -if the given type is add then it insert the value with given name since the id is auto incremented it doesnt give an error
    -if the given type is remove than our method first cheks the if there is any category with given name if not then it returns false
    -if we have such a category with the given name than we delete it
    */
    public boolean addRemoveCategory(String name, String type) {
        boolean success = false;
    
        try {
            if ("add".equals(type)) {
                String addCategoryQuery = "INSERT INTO Category (name) VALUES ('" + name + "')";
                try (Statement addCategoryStatement = connection.createStatement()) {
                    addCategoryStatement.executeUpdate(addCategoryQuery);
                    success = true;
                    System.out.println("Category added.");
                }
            } else if ("remove".equals(type)) {
                String removeCategoryQuery = "DELETE FROM Category WHERE name = '" + name + "'";
                try (Statement removeCategoryStatement = connection.createStatement()) {
                    int rowsAffected = removeCategoryStatement.executeUpdate(removeCategoryQuery);
                    success = rowsAffected > 0;
                    System.out.println("Category " + (success ? "deleted." : "does not exist."));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to add or remove category.");
            e.printStackTrace();
        }
    
        return success;
    }

    /*This method is for removing the listing with given listing id.
     We use this method for the removeProduct method in case if the product is listed we remove it first then we remove the product.
     But if there is a order with that listingId we can not remove it due to integrity constraint
    */
    public void removeListing(int listingId) {
        try {
            String query = "DELETE FROM Listing WHERE listingId = " + listingId + ";";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            System.out.println("Failed to remove listing.");
            e.printStackTrace();
        }
    }

    /*This method is for removing the product with given product id.
    -first we check if the product is listed or not
    -if the product is listed we call remove listing method 
    -then we delete the product with given id
    */
    public boolean removeProduct(int productId) {
        boolean success = false;
        try {
            String query = "SELECT * FROM Listing WHERE productId = " + productId + ";";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    int listingId = resultSet.getInt("listingId");
                    removeListing(listingId);
                }
            }
            query = "DELETE FROM Product WHERE productId = " + productId + ";";
            try (Statement statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(query);
                success = rowsAffected > 0;
                System.out.println("Product " + (success ? "deleted." : "does not exist."));
            }
        } catch (SQLException e) {
            System.out.println("Failed to remove product.");
            e.printStackTrace();
        }
        return success;
    }

    /*This method is for changing the price of the listed product with given listingId */
    public void updatePrice(int listingId, int price) {
        try  {
            Statement statement = connection.createStatement();
            String query = "UPDATE Listing SET price = " + price + " WHERE listingId = " + listingId + ";";
            statement.executeUpdate(query);
            System.out.println("Price updated.");
        } catch (SQLException e) {
            System.out.println("Failed to update price.");
            e.printStackTrace();
        }
    }


    /*In rhis method we create a new listing with given productID, price and stock values since the id is auto incremented we are
    not giving any value to listingId it will be generated automatically and we return that value.
    -first we chec if the product is listed from same user or not 
    -if the product is listed from the given seller we call updatePrice method. UpdatePrice method will update the price of the product
    -We taugght we should also update the stock value but we didnt do it since in the fuctions doc it says it updates price there was no
    mention about the stock value.
    -if the product is not listed from the given seller we create a new listing with given values.
    -Due to integrity constrains createListing method should take an product and seller id which already exists in the tables
     */
    public int createListing(int productId, int sellerId, int price, int stock) {
        int listingId = -1;
    
        try  {
            Statement checkListingStatement = connection.createStatement();
            String checkListingQuery = "SELECT listingId FROM Listing WHERE productId = " + productId + " AND sellerId = " + sellerId + ";";
            ResultSet listingResultSet = checkListingStatement.executeQuery(checkListingQuery);
    
            if (listingResultSet.next()) {
                listingId = listingResultSet.getInt("listingId");
                updatePrice(listingId, price);
            } else {
                String createListingQuery = "INSERT INTO Listing (productId, sellerId, price, stock) VALUES (" + productId + ", " + sellerId + ", " + price + ", " + stock + ");";
                try (Statement createListingStatement = connection.createStatement()) {
                    createListingStatement.executeUpdate(createListingQuery, Statement.RETURN_GENERATED_KEYS);
    
                    ResultSet generatedKeys = createListingStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        listingId = generatedKeys.getInt(1);
                        System.out.println("New Listing ID (inside the function): " + listingId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to create or update listing.");
            e.printStackTrace();
        }
    
        return listingId;
    }


    /*In this method we get the average price per category*/
    public ArrayList<CategoryStats> getAveragePricePerCategory() {
        ArrayList<CategoryStats> statsList = new ArrayList<>();
    
        try  {
            Statement statement = connection.createStatement();
            String query = "SELECT c.categoryId, AVG(l.price) AS averagePrice " +
                           "FROM Category c " +
                           "LEFT JOIN Product p ON c.categoryId = p.categoryId " +
                           "LEFT JOIN Listing l ON p.productId = l.productId " +
                           "GROUP BY c.categoryId;";
            
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("categoryId");
                double averagePrice = resultSet.getDouble("averagePrice");
                CategoryStats categoryStats = new CategoryStats(categoryId, averagePrice);
                statsList.add(categoryStats);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get average price per category.");
            e.printStackTrace();
        }
    
        return statsList;
    }
    


    
    
    

    /* Lists all users in the User table. */
    public ArrayList<User> listAllUsers() {
        ArrayList<User> userList = new ArrayList<>();

        try  {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM User";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String address = resultSet.getString("address");
                User user = new User(id, name, type, address);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user table from database.");
            e.printStackTrace();
        }

        return userList;
    }


    /* Lists all products in the product table. */
    public ArrayList<Product> listAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM Product";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("name");
                Product product = new Product(productId, productName);
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get product table from the database.");
            e.printStackTrace();
        }

        return productList;
    }


    /* Lists all the payment methods for a given user.
    First, the method matches the given userId in the payment table
    and then retrieves every cardNumber value with the same userId. */
    public ArrayList<String> listPaymentMethods(int userId) {
        ArrayList<String> paymentMethods = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT cardNumber FROM Payment WHERE userId = " + userId;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String cardNumber = resultSet.getString("cardNumber");
                paymentMethods.add(cardNumber);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get payment methods from database.");
            e.printStackTrace();
        }

        return paymentMethods;
    }



    /* Lists all the categories in the category table */
    public ArrayList<Category> getCategories() {
        ArrayList<Category> categoryList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Category;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("categoryId");
                String categoryName = resultSet.getString("name");
                Category category = new Category(categoryId, categoryName);

                categoryList.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get category table from database.");
            e.printStackTrace();
        }

        return categoryList;
    }


    /*In this method we get all the listing with the given sellerID */
    public ArrayList<Listing> getListingsOfSeller(int sellerId) {
        ArrayList<Listing> sellerListings = new ArrayList<>();

        try {

            String query = "SELECT * FROM Listing WHERE sellerId = " + sellerId;


            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int listingId = resultSet.getInt("listingId");
                int productId = resultSet.getInt("productId");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");
                Listing listing = new Listing(listingId, sellerId, productId, price, stock);
                sellerListings.add(listing);
                }

        } catch (SQLException e) {
            System.out.println("Failed to get listings of the seller.");
            e.printStackTrace();
        }

        return sellerListings;
    }


    //In this method we try to get single listing with the given listingId
    public Listing getListing(int listingId) {
        Listing listing = null;

        try {
            String query = "SELECT * FROM Listing WHERE listingId = " + listingId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int sellerId = resultSet.getInt("sellerId");
                int productId = resultSet.getInt("productId");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");

                listing = new Listing(listingId, sellerId, productId, price, stock);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get the listing.");
            e.printStackTrace();
        }

        return listing;
    }

    public ArrayList<Order> getordersOfUser(int userId){
        ArrayList<Order> orders = new ArrayList<>();
        try{
            String query = "SELECT * FROM Orders WHERE userId = " + userId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int orderId = resultSet.getInt("orderId");
                int listingId = resultSet.getInt("listingId");
                Date date = resultSet.getDate("date");
                Order order = new Order(orderId, listingId, userId, date);
                orders.add(order);
            }
        }catch (SQLException e){
            System.out.println("Failed to get orders of the user");
            e.printStackTrace();
        }

        return orders;
    }

    /*In this method
     First we find the listing with given id
     Then we check for the stock if it has any stock we return true else false
     Since we wo not have any info about the quantity we only dicrease the stock by 1
     Also wo given any date value we couldnt manage to insert new order to the order table
     */
    public boolean buy(int listingId, int userId) {
        boolean canBuy = false;

        String checkStockQuery = "SELECT stock FROM Listing WHERE listingId = ?;";

        try (PreparedStatement checkStockStatement = connection.prepareStatement(checkStockQuery)) {
            checkStockStatement.setInt(1, listingId);

            ResultSet stockResultSet = checkStockStatement.executeQuery();

            if (stockResultSet.next()) {
                int stock = stockResultSet.getInt("stock");

                if (stock > 0) {
                    String updateStockQuery = "UPDATE Listing SET stock = stock - 1 WHERE listingId = ?;";
                    PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
                    updateStockStatement.setInt(1, listingId);
                    canBuy = true;
                    System.out.println("Product bought.");
                } else {
                    System.out.println("Product is out of stock.");
                }
            } else {
                System.out.println("Product cannot be found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to buy the product.");
            e.printStackTrace();
        }

        return canBuy;
    }

    /*In this method we list all the Seller by their revanue and we calculate the revenue with using the orders
    we assume overy order 1 quantity an with using orders we get the price
    and we order them by that way and with using limit we select the top k sellers
    */
    public ArrayList<User> listTopKSellers(int k) {
        ArrayList<User> topSellers = new ArrayList<>();

        String query = "SELECT u.userId, u.name, u.type, u.address, SUM(l.price) AS revenue " +
                       "FROM User u " +
                       "JOIN Listing l ON u.userId = l.sellerId " +
                       "JOIN Orders o ON l.listingId = o.listingId " +
                       "GROUP BY u.userId, u.name, u.type, u.address " +
                       "ORDER BY revenue DESC " +
                       "LIMIT " + k + ";";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String address = resultSet.getString("address");

                User seller = new User(userId, name, type, address);
                topSellers.add(seller);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get top sellers.");
            e.printStackTrace();
        }

        return topSellers;
    }


    /* We just implement a basic method to count the listing values where their stock is equal to zero */
    public int numberOfOutOfStock() {
        int count = 0;
    
        try  {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(*) AS outOfStockCount FROM Listing WHERE stock = 0;";
            ResultSet resultSet = statement.executeQuery(query);
    
            if (resultSet.next()) {
                count = resultSet.getInt("outOfStockCount");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get the number of out-of-stock listings.");
            e.printStackTrace();
        }
    
        return count;
    }


    /* we tried to find from orders get the listing and from there the find the product and count that way the orders */
    public Product topSellingProduct() {
        Product topProduct = null;
    
        try  {
            Statement statement = connection.createStatement();
            String query = "SELECT l.productId, p.name, COUNT(*) AS orderCount " +
                           "FROM Listing l " +
                           "JOIN Orders o ON l.listingId = o.listingId " +
                           "JOIN Product p ON l.productId = p.productId " +
                           "GROUP BY l.productId " +
                           "ORDER BY orderCount DESC " +
                           "LIMIT 1;";
    
            ResultSet resultSet = statement.executeQuery(query);
    
            if (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("name");
                topProduct = new Product(productId, productName);

                System.out.println("Top selling product: " + productName);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get the top selling product.");
            e.printStackTrace();
        }
    
        return topProduct;
    }
    




    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HW_2";
        String username = "root";
        String password = "268";

        Main main = new Main(url, username, password);
        if (main.connection != null) {
            System.out.println("Connected to the database!");

            ArrayList<Order> orders = main.getordersOfUser(2);
            for (Order order : orders) {
                System.out.println("OrderID: " + order.getOrderId() + ", " +"ListingID: " + order.getListingId() + ", " + "UserID: " + order.getUserId() + ", " + "Date: "+ order.getDate());
            }

            Listing listing = main.getListing(1);
            System.out.println("ListingID: " + listing.getListingId() + ", " +"SellerID: " + listing.getSellerId() + ", " + "ProductID: " + listing.getProductId() + ", " + "Price: "+ listing.getPrice() + ", " + "Stock: "+ listing.getStock());

            /* 
            int createdSeller = main.addSeller("Ali", "Dormitory 2");
            System.out.println("New Seller ID (Return of Function): " + createdSeller);

            int createdCustomer = main.addCustomer("Veli", "Dormitory 1");
            System.out.println("New Customer ID (Return of Function): " + createdCustomer);

            int createdProduct = main.addProduct("Book", "A book about JDBC", 1);
            System.out.println("New Product ID (Return of Function): " + createdProduct);

            boolean paymentAddTest = main.addRemovePaymentMethod(16, "add", "33454343");
            System.out.println(paymentAddTest);

            ArrayList<String> paymentMethods = main.listPaymentMethods(16);
            for (String cardNumber : paymentMethods) {
                System.out.println("Card Number: " + cardNumber);
            }

            boolean categoryAddTest = main.addRemoveCategory("sdsda", "add");
            System.out.println(categoryAddTest);

            main.removeListing(1);
            */

            //main.removeProduct(3);

           
            
            /* 
            ArrayList<User> userList = main.listAllUsers();
            for (User user : userList) {
                System.out.println("UserID: " + user.getUserId() + ", " +"User Name: " + user.getName() + ", " + "User Type: " + user.getType() + ", " + "User Addres: "+ user.getAddress());
            }
        

            ArrayList<Product> productList = main.listAllProducts();
            for (Product product : productList) {
                System.out.println("ProductID: " + product.getProductId() + ", " +"Product Name: " + product.getName());
            }

            ArrayList<Category> categoryList = main.getCategories();
            for (Category category : categoryList) {
                System.out.println("CategoryID: " + category.getCategoryId() + ", " +"Category Name: " + category.getName());
            }

             ArrayList<Category> categoryList = main.getCategories();
            for (Category category : categoryList) {
                System.out.println("CategoryID: " + category.getCategoryId() + ", " +"Category Name: " + category.getName());
            }
           */
            

            main.closeConnection();
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
