import java.util.HashMap;

public class Account {
    private String userId;
    private String username;
    private String email;
    private String password;
    private HashMap<String, Order> orders;
    public Account(String userId, String username, String email, String password, HashMap<String, Order> orders) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.orders = orders;
    }
}
