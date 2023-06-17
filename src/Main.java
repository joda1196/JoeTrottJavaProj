import java.util.*;
import java.sql.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void createOrLogin(Account acc) {
        boolean running = true;
        while (running) {
            System.out.println("[l]ogin or [c]reate an account: ");
            String choice = scanner.nextLine();
            if (choice.equals("l")) {
                acc.loginAccount();
                running = false;
            } else if (choice.equals("c")) {
                acc.createAccount();
                running = false;
            } else {
                System.out.println("Invalid input: Create or Login");
            }
        }
    }


    public static void addToCart(LinkedHashMap<String, Product> temporaryCart, ArrayList<Product> productList, String productName, int productQuantity) {
//        if product quantity not zero do below else product = null

//        get product
        boolean foundProduct = false;

        for (Product product : productList) {
            if (product.productName.equalsIgnoreCase(productName)) {
                product.quantity = productQuantity;
                temporaryCart.put(product.productName, product);
                foundProduct = true;
//                break;
            }
        }

        if (!foundProduct) {
            System.out.println("Product Not Found");
        }
    }

    public static void viewCart(LinkedHashMap<String, Product> temporaryCart) {
        if (temporaryCart.isEmpty()) {
            System.out.println("Cart is Empty");
        } else {
            for (Product each: temporaryCart.values()) {
                System.out.println(each.productName);
                System.out.println(each.price);
                System.out.println("Quantity: " + each.quantity);
            }
        }
    }

    public static void checkout(Account acc, LinkedHashMap<String, Product> temporaryCart) {
        if (!temporaryCart.isEmpty()){
            Order order = new Order(acc.userId);
//        copy temporary cart to order cart
            order.cartOfProducts = temporaryCart;
//        calculate prices and quantities of cartOfProducts
            order.calculateCost();
//        print totalCost

            System.out.println("Total: " + order.totalCost);
//        add order to Account.orders
            acc.addRecentOrder(acc, order);
//        clear temp cart
            temporaryCart.clear();
        } else {
            System.out.println("Cart is Empty");
        }

    }

    public static void main(String[] args) {
        DBManager db = new DBManager();
        Account acc = new Account();

        createOrLogin(acc);

        ArrayList<Product> productsList = new ArrayList<>();
//        Arrays.asList()

        LinkedHashMap<String, Product> temporaryCart = new LinkedHashMap<>();

        //DO NOT TOUCH
        boolean running = true;
        boolean allowMessage = true;
        String command = "";

        while (running) {

            if (allowMessage) {
                allowMessage = false;
                System.out.println("[p]roducts], [a]dd to cart, view [c]art, [ch]eckout, [r]ecent orders] [v]iew account]");
            } else {
                allowMessage = true;
            }

            command = scanner.nextLine().toLowerCase();

            if (!command.equals("")) {
//-----------------------------------------------------------------------------------------------------------------
                switch (command) {
                    case "p" -> {
                        String[] commands = {"a", "asc", "desc"};
                        String userInput = "";


                        while (!Arrays.asList(commands).contains(userInput)) {
                            System.out.println("Show [a]ll | Sort by price [asc] | [desc]");

                            userInput = scanner.nextLine().toLowerCase();
                            switch (userInput) {
                                case "a" -> db.getProducts();
                                case "asc" -> db.sortProductsPriceAsc();
                                case "desc" -> db.sortProductsPriceDesc();
                                default -> System.out.println("Invalid Input");
                            }
                        }
                    }
                    case "a" -> {
                        System.out.println("Enter Product Name: ");
                        String productSearch = scanner.nextLine().toLowerCase();

                        System.out.println("Quantity: ");
                        int productQuantity = scanner.nextInt();

                        addToCart(temporaryCart, productsList, productSearch, productQuantity);
                    }
                    case "c" -> {viewCart(temporaryCart);}
                    case "ch" -> {checkout(acc, temporaryCart);}
                    case "r" -> {acc.viewRecentOrders();}
                    case "v" -> {acc.getAccount();}
                    case "q" -> {running = false;}
                    default -> {System.out.println("Invalid Input");}
                }
            }
        }
        scanner.close();
    }
}
