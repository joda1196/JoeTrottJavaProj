import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DBManager db = new DBManager();

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


    public static void addToCart(LinkedHashMap<String, Product> temporaryCart, String productName, Product product) {
//        Product product = new Product(productId, productName, productPrice, productQuantity);
        temporaryCart.put(productName, product);
        System.out.println("Product Added To Cart!");
        viewCart(temporaryCart);
    }

    public static void removeFromCart(LinkedHashMap<String, Product> temporaryCart, String productName) {
        temporaryCart.remove(productName);
        System.out.println("Product Removed To Cart!");
        viewCart(temporaryCart);
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

    public static double calculateCost (LinkedHashMap<String, Product> temporaryCart) {
        double totalCost = 0;
        for (Product each: temporaryCart.values()) {
            totalCost += each.price;
        }
        return totalCost;
    }

    public static void checkout(Account acc, LinkedHashMap<String, Product> temporaryCart) {
        if (!temporaryCart.isEmpty()){
            double orderTotalCost = calculateCost(temporaryCart);
            db.addOrder(orderTotalCost, Integer.parseInt(acc.userId));

            int orderId = db.findMostRecentOrder();

            for (Product each: temporaryCart.values()) {
                db.addOrderItems(each.productId, orderId, each.quantity);
            }

            System.out.println("Total: " + orderTotalCost);
            temporaryCart.clear();
        } else {
            System.out.println("Cart is Empty");
        }
    }

    public static void main(String[] args) {

        Account acc = new Account();

        createOrLogin(acc);

        LinkedHashMap<String, Product> temporaryCart = new LinkedHashMap<>();

        //DO NOT TOUCH
        boolean running = true;
        boolean allowMessage = true;
        String command = "";

        while (running) {

            if (allowMessage) {
                allowMessage = false;
                System.out.println("[p]roducts], [a]dd to cart, [rem]ove from cart, view [c]art, [ch]eckout, [r]ecent orders] [v]iew account]");
            } else {
                allowMessage = true;
                System.out.println("[p]roducts], [a]dd to cart, [rem]ove from cart, view [c]art, [ch]eckout, [r]ecent orders] [v]iew account]");
            }

            command = scanner.nextLine().toLowerCase();

            if (!command.equals("")) {
//---------------------------------------------MAIN COMMANDS--------------------------------------------------------------------
                switch (command) {
                    case "p" -> {
                        String[] commands = {"a", "asc", "desc"};
                        String userInput = "";

                        while (!Arrays.asList(commands).contains(userInput)) {
                            System.out.println("Show [a]ll | Sort by price [asc] | [desc]");
                            userInput = scanner.nextLine().toLowerCase();
                            switch (userInput) {
                                case "a" -> db.getAllProducts();
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

                        LinkedHashMap<String, Product> foundProduct = db.findProduct(productSearch, productQuantity);

                        String productName = foundProduct.keySet().toString();
                        Product product = foundProduct.get(productSearch);

                        addToCart(temporaryCart, productName, product);
                    }
                    case "rem" -> {
                        System.out.println("Enter Product Name: ");
                        String productSearch = scanner.nextLine().toLowerCase();
                        if (temporaryCart.containsKey(productSearch)) {
                            removeFromCart(temporaryCart, productSearch);
                        } else {
                            System.out.println("Not Found in Cart");
                        }
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
