import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);


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
        Account acc = new Account();

//        Mock Products
        Product prod1 = new Product("Dove Soap", 6.00);
        Product prod2 = new Product("Hand Sanitizer", 5.49);
        Product prod3 = new Product("Plastic Bin", 8.99);

        ArrayList<Product> productsList = new ArrayList<>(Arrays.asList(prod1, prod2, prod3));

        LinkedHashMap<String, Product> temporaryCart = new LinkedHashMap<>();


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
                switch (command) {
                    case "p" -> {
//                    Show list of Products
                        for (Product product: productsList) {
                            product.getProduct();
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
                    case "v" -> {System.out.println(acc.getAccount());}
                    case "q" -> {running = false;}
                    default -> {System.out.println("Invalid Input");}
                }
            }
        }
        scanner.close();
    }
}
