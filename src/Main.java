import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DBManager db = new DBManager();

    static Helper mrHelper = new Helper();

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
        temporaryCart.put(productName.toLowerCase(), product);
        System.out.println(productName + " Added To Cart");
    }

    public static void removeFromCart(LinkedHashMap<String, Product> cart, String productName) {
        for (Iterator<Product> iterator = cart.values().iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (productName.equals(product.productName)) {
                iterator.remove();
                System.out.println(product.productName + " Removed From Cart");
            }
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

    public static double calculateCost (LinkedHashMap<String, Product> temporaryCart) {
        double totalCost = 0;
        for (Product each: temporaryCart.values()) {
            totalCost += each.price * each.quantity;
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
                System.out.println("[p]roducts], [a]dd to cart, [rem]ove from cart, view [c]art, [ch]eckout, [r]ecent orders] [v]iew account] [q]uit");
            } else {
                allowMessage = true;
                System.out.println("[p]roducts], [a]dd to cart, [rem]ove from cart, view [c]art, [ch]eckout, [r]ecent orders] [v]iew account] [q]uit");
            }

            command = scanner.nextLine().toLowerCase();

            if (!command.equals("")) {
//---------------------------------------------MAIN COMMANDS--------------------------------------------------------------------
                switch (command) {
                    case "p" -> {
                        String[] commands = {"a", "asc", "desc", "back"};
                        String userInput = "";

                        while (!Arrays.asList(commands).contains(userInput)) {
                            System.out.println("Show [a]ll | Sort by price [asc] | [desc] | [back]");
                            userInput = scanner.nextLine().toLowerCase();
                            switch (userInput) {
                                case "a" -> db.getAllProducts();
                                case "asc" -> db.sortProductsPriceAsc();
                                case "desc" -> db.sortProductsPriceDesc();
                                case "back" -> {break;}
                                default -> System.out.println("Invalid Input");
                            }
                        }
                    }
                    case "a" -> {
                        System.out.println("Enter Product Name: ");
                        String productSearch = scanner.nextLine().toLowerCase();

                        int productQuantity = mrHelper.validNumber("Quantity: ");

                        LinkedHashMap<String, Product> foundProduct = db.findProduct(productSearch, productQuantity);

                        if (foundProduct.isEmpty()) {
                            break;
                        }
                        Product product = foundProduct.get(productSearch);
                        String productName = product.productName;

                        addToCart(temporaryCart, productName, product);
                    }
                    case "rem" -> {
                        if (temporaryCart.isEmpty()) {
                            System.out.println("Cart is Empty");
                        } else {
                            System.out.println("Enter Product Name: ");
                            String productNameRemove = scanner.nextLine().toLowerCase();
                            removeFromCart(temporaryCart, productNameRemove);
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
