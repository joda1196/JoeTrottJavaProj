import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Account {
    DBManager dbManager = new DBManager();
    Scanner scanner = new Scanner(System.in);
    public String userId;
    private String username;
    private String email;
    private String password;
    private HashMap<String, Order> orders;
    public Account() {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.orders = orders;
    }

    public void assignAccount(String username, String email, String password) {
        this.userId = generateId();
        this.username = username;
        this.email = email;
        this.password = password;
        this.orders = new HashMap<>();
    }

    public void loginAccount(){
        System.out.println("username: ");
        String usernameInput = scanner.nextLine();

        System.out.println("password: ");
        String pwInput = scanner.nextLine();

        HashMap<String, String> acc = dbManager.findAccount(usernameInput, pwInput);
        if (acc != null) {
            String foundUsername = acc.get("foundUsername");
            String email = acc.get("email");
            String password = acc.get("password");

            assignAccount(foundUsername, email, password);
        } else {
            System.out.println("Account Not Found");
        }



    }
    public void createAccount(){
        System.out.println("Create Account");

        System.out.println("Email: ");
        String emailInput = scanner.nextLine();

        System.out.println("Username: ");
        String usernameInput = scanner.nextLine();

        System.out.println("Password: ");
        String pwInput = scanner.nextLine();

        HashMap<String, String> acc = dbManager.findAccount(usernameInput, pwInput);

        if (acc != null) {
            System.out.println("Username already exists");
        } else {
            assignAccount(usernameInput, emailInput, pwInput);
            dbManager.addAccount(this.email, this.username, this.password);
        }
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
