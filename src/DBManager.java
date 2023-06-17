import java.sql.*;
import java.util.HashMap;

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
                String email = rs.getString("account_email");
                String foundUsername = rs.getString("account_username");
                String password = rs.getString("account_pw");

                result = new HashMap<String, String>();
                result.put("foundUsername", foundUsername);
                result.put("email", email);
                result.put("password", password);
            }
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
                System.out.println(prodCount++ + " || Product name: " + rs.getString("product_name") + " || " + "Price: " + rs.getBigDecimal("product_price") + " || ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addOrder(){}

    public void findOrder(){}


}
