import java.sql.*;
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

        String sqlStatement = "SELECT account_username, account_email, account_pw FROM Accounts WHERE account_username = ? AND account_pw = ?;";

        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement st = connection.prepareStatement(sqlStatement);
            st.setString(1, username);
            st.setString(2, pw);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("account_id");
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


    public void getProducts() {
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

    public LinkedHashMap<String, Double> findProduct(String productName) {
        LinkedHashMap<String, Double> pulledProduct = null;

        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement st = connection.prepareStatement("SELECT product_name, product_price FROM Products WHERE product_name = ?;");
            st.setString(1, productName);
            ResultSet rs = st.executeQuery();

            pulledProduct = new LinkedHashMap<String, Double>();

            if (rs.next()) {
                System.out.println("Product Found!");
                String product_name = rs.getString(1);
                Double product_price = rs.getDouble(2);
                pulledProduct.put(product_name, product_price);
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

    public void findOrders(int userId){
        String sqlStatement = "SELECT Orders.order_id, Orders.order_date_created, Orders.order_total_cost, Products.product_name, Products.product_price, OrderItems.order_item_quantity\n" +
                "FROM OrderItems\n" +
                "INNER JOIN Orders  ON Orders.orders_id = OrderItems.order_items_orders_id\n" +
                "INNER JOIN Products ON Products.product_id = OrderItems.order_items_product_id\n" +
                "WHERE Orders.order_account_id = ?\n" +
                "ORDER BY order_date_created DESC;";

        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement st = connection.prepareStatement(sqlStatement);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String order_id = rs.getString("order_id");
                String order_date_created = rs.getString("order_date_created");
                String order_total_cost = rs.getString("order_total_cost");
                String product_name = rs.getString("product_name");
                String product_price = rs.getString("product_price");
                String order_item_quantity = rs.getString("order_item_quantity");

                System.out.println("-------------------------------------------------------");
                System.out.println(order_id);
                System.out.println(order_date_created);
                System.out.println(order_total_cost);
                System.out.println(product_name);
                System.out.println(product_price);
                System.out.println(order_item_quantity);
            } else {
                System.out.println("No Orders Found");
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
