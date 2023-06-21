import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Account {
    DBManager dbManager = new DBManager();
    Scanner scanner = new Scanner(System.in);
    Helper mrHelper = new Helper();
    public String userId;
    public String username;
    public String email;
    public String password;

    public Account() {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void assignAccount(String id, String username, String email, String password) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void loginAccount(){
        boolean running = true;
        while (running) {
            System.out.println("username: ");
            String usernameInput = scanner.nextLine();

            System.out.println("password: ");
            String pwInput = scanner.nextLine();

            HashMap<String, String> acc = dbManager.findAccount(usernameInput, pwInput);
            if (acc != null) {
                String id = acc.get("id");
                String foundUsername = acc.get("username");
                String email = acc.get("email");
                String password = acc.get("password");

                assignAccount(id, foundUsername, email, password);
                running = false;
            } else {
                System.out.println("Account Not Found");
            }
        }




    }
    public void createAccount(){

        System.out.println("Create Account");

//        System.out.println("Email: ");
        String emailInput = mrHelper.validString("Email: ");

//        System.out.println("Username: ");
        String usernameInput = mrHelper.validString("Username: ");

//        System.out.println("Password: ");
        String pwInput = mrHelper.validString("Password: ");

        HashMap<String, String> acc = dbManager.findAccount(usernameInput, pwInput);

        if (acc != null) {
            System.out.println("Username already exists");
        } else {
            dbManager.addAccount(emailInput, usernameInput, pwInput);
            HashMap<String, String> addedAcc = dbManager.findAccount(usernameInput, pwInput);

            assignAccount(addedAcc.get("id"), addedAcc.get("username"), addedAcc.get("email"), addedAcc.get("password"));
        }
    }

    public void getAccount() {
        System.out.println("Username: " + this.username);
        System.out.println("Email: " + this.email);
        System.out.println("Password: " + this.password);
    }

    public void viewRecentOrders() {
        dbManager.getOrders(Integer.parseInt(this.userId));
    }
}
