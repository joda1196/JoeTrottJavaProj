import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Account acc = new Account();

//        Mock Products
        Product prod1 = new Product("Dove Soap", 6.00);
        Product prod2 = new Product("Hand Sanitizer", 5.49);
        Product prod3 = new Product("Plastic Bin", 8.99);

        boolean running = true;
        String command = "";

        while (running) {
            System.out.println("[p]roducts] [r]ecent orders] [v]iew account]");
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "p" -> {
//                    Show list of Products
                    System.out.println(prod1.getProduct());
                    System.out.println(prod2.getProduct());
                    System.out.println(prod3.getProduct());
//                    Option to add to cart
                }
                case "r" -> {System.out.println(acc.viewRecentOrders());}
                case "v" -> {System.out.println(acc.getAccount());}
                case "q" -> {break;}
            }
        }

        System.out.println(acc.getAccount());
    }
}
