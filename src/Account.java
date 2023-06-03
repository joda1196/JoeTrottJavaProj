import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Account {
    Scanner scanner = new Scanner(System.in);
    public String userId;
    private String username;
    private String email;
    private String password;
    private HashMap<String, Order> orders;
    public Account() {
//        System.out.println("Create Account");
//        this.userId = generateId();
//
//        System.out.println("Username: ");
//        this.username = scanner.nextLine();
//
//        System.out.println("Email: ");
//        this.email = scanner.nextLine();
//
//        System.out.println("Password: ");
//        this.password = scanner.nextLine();
//
//        this.orders = new HashMap<>();

        this.userId = generateId();
        this.username = "username";
        this.email = "email@yahoo.com";
        this.password = "password";
        this.orders = new HashMap<>();
    }

    public String getAccount() {
        return this.username + "|" + this.email + "|" + this.password;
    }

    private String generateId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    public void addRecentOrder(Account acc, Order order) {
        this.orders.put(order.orderId, order);
    }

    public void viewRecentOrders() {
        if (this.orders.isEmpty()) {
            System.out.println("Orders: Empty");
        } else {System.out.println(this.orders.values());}
    }
}
