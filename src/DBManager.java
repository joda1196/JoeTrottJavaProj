import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DBManager {
    final String url = "jdbc:postgresql://localhost:5432/MiniEcommerce";

    public void addAccount(String email,String username,String password){
        String sqlStatement = "INSERT INTO Accounts (account_email, account_username, account_pw) VALUES (?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement(sqlStatement);

            st.setString(1, email);
            st.setString(2, username);
            st.setString(3, password);

            int exec_st = st.executeUpdate();

            if (exec_st == 1) {
                System.out.println("Account Created Successfully!");
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> findAccount(String username, String pw) {
        HashMap<String, String> result = null;

        String sqlStatement = "SELECT account_id, account_username, account_email, account_pw FROM Accounts WHERE account_username = ? AND account_pw = ?;";

        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement st = connection.prepareStatement(sqlStatement);
            st.setString(1, username);
            st.setString(2, pw);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String id = rs.getString("account_id");
                String email = rs.getString("account_email");
                String foundUsername = rs.getString("account_username");
                String password = rs.getString("account_pw");

                result = new HashMap<String, String>();
                result.put("id", String.valueOf(id));
                result.put("username", foundUsername);
                result.put("email", email);
                result.put("password", password);
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void getAllProducts() {
        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Products;");
            ResultSet rs = st.executeQuery();
            int prodCount = 0;

            while (rs.next()) {
                prodCount++;
                System.out.println(prodCount + " || Product name: " + rs.getString("product_name") + " || " + "Price: " + rs.getBigDecimal("product_price") + " || ");
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, Product> findProduct(String productNameSearch, int quantity) {
        LinkedHashMap<String, Product> pulledProduct = null;

        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement("SELECT product_id, product_name, product_price FROM Products WHERE product_name = ? LIMIT 1;");
            st.setString(1, productNameSearch);
            ResultSet rs = st.executeQuery();

            pulledProduct = new LinkedHashMap<String, Product>();

            if (rs.next()) {
                System.out.println("Product Found!");
                int id = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                Double productPrice = rs.getDouble("product_price");

                Product p = new Product(id, productName, productPrice, quantity);

                pulledProduct.put(productName, p);
            } else {
                System.out.println("Product Not Found");
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pulledProduct;
    }

    public void sortProductsPriceAsc() {
        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Products ORDER BY product_price ASC;");
            ResultSet rs = st.executeQuery();
            int prodCount = 0;

            while (rs.next()) {
                prodCount++;
                System.out.println(prodCount++ + " || Product name: " + rs.getString("product_name") + " || " + "Price: " + rs.getBigDecimal("product_price") + " || ");
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortProductsPriceDesc() {
        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Products ORDER BY product_price DESC;");
            ResultSet rs = st.executeQuery();
            int prodCount = 0;

            while (rs.next()) {
                prodCount++;
                System.out.println(prodCount++ + " || Product name: " + rs.getString("product_name") + " || " + "Price: " + rs.getBigDecimal("product_price") + " || ");
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addOrder(double totalCost, int accId){
        String sqlStatement = "INSERT INTO Orders (order_total_cost, order_account_id) VALUES (?, ?);";
        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement(sqlStatement);

            st.setDouble(1, totalCost);
            st.setInt(2, accId);

            int exec_st = st.executeUpdate();

            if (exec_st == 1) {
                System.out.println("Order Receipt: ");
                System.out.println("$" + totalCost);
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int findMostRecentOrder(){
        String sqlStatement = """
                SELECT *
                FROM Orders
                ORDER BY order_id DESC
                LIMIT 1;
                """;

        int orderId = 0;

        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement(sqlStatement);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                orderId = rs.getInt("order_id");

            } else {
                System.out.println("ERROR: Recently made order not found");
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public void addOrderItems(int productId, int orderId, int orderQuantity) {
        String sqlStatement = "INSERT INTO OrderItems (order_items_product_id, order_items_order_id, order_items_quantity) VALUES (?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement(sqlStatement);

            st.setInt(1, productId);
            st.setInt(2, orderId);
            st.setInt(3, orderQuantity);

            int exec_st = st.executeUpdate();

            if (exec_st == 1) {
                System.out.println("Order Items Added Successfully!");
            } else {
                System.out.println("ERROR: Order Items unable to be added");
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getOrders(int userId){
        String sqlStatement = "SELECT Orders.order_id, Orders.order_date_created, Orders.order_total_cost, Products.product_name, Products.product_price, OrderItems.order_items_order_id, OrderItems.order_items_quantity\n" +
                "FROM OrderItems\n" +
                "INNER JOIN Orders ON Orders.order_id = OrderItems.order_items_order_id\n" +
                "INNER JOIN Products ON Products.product_id = OrderItems.order_items_product_id\n" +
                "WHERE Orders.order_account_id = ?\n" +
                "ORDER BY order_date_created DESC;";

        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement(sqlStatement);

            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            int tempOrderId = 0;

            if (!rs.next()) {
                System.out.println("No Orders Found");
            } else {
                do {
                    if (tempOrderId != rs.getInt("order_id")) {
                        tempOrderId = rs.getInt("order_id");
                        System.out.println("=========================ORDER==========================");
                        System.out.println("Order ID: " + rs.getString("order_id"));
                        System.out.println("Order DATE: " + rs.getString("order_date_created"));
                        System.out.println("Order Total: " + rs.getString("order_total_cost"));
                    }
                    System.out.println("----------------------------PRODUCT---------------------------");
                    System.out.println("Order Product: " + rs.getString("product_name"));
                    System.out.println("Product Price: " + rs.getString("product_price"));
                    System.out.println("Product Quantity: " + rs.getString("order_items_quantity"));

                } while (rs.next());
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
