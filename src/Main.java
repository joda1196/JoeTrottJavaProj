import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
//        make acccount
//        [products] [create order] [recent orders] [view account]

//        Product prod = new Product("p001", "Toilet Paper", 2.00);
//        Order order = new Order("#001", acc, 2);


        Account acc = new Account();
        boolean running = true;
        String command = "";

        while (running) {
            System.out.println("[p]roducts] [c]reate order] [r]ecent orders] [v]iew account]");
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "p" -> {}
                case "c" -> {}
                case "r" -> {}
                case "v" -> {}
            }
        }

        System.out.println(acc.getAccount());
    }
}
