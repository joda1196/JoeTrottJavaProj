import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    public static void addToCart(LinkedHashMap<String, Product> temporaryCart, Product[] productList, String productName, int productQuantity) {
//        if product quantity not zero do below else product = null

//        get product
        for (int i = 0; i < productList.length; i++) {
            if (productList[i].productName.equalsIgnoreCase(productName)) {
                //        add to order
                temporaryCart.put(productList[i].productName, productList[i]);
            }
        }
//        return temporaryCart;
    }

    public static void main(String[] args) {
        Account acc = new Account();

//        Mock Products
        Product prod1 = new Product("Dove Soap", 6.00);
        Product prod2 = new Product("Hand Sanitizer", 5.49);
        Product prod3 = new Product("Plastic Bin", 8.99);

        Product[] productsList = {prod1, prod2, prod3};

        LinkedHashMap<String, Product> temporaryCart = new LinkedHashMap<>();


        boolean running = true;
        String command = "";

        while (running) {
            System.out.println("[p]roducts], [a]dd to cart, view [c]art [r]ecent orders] [v]iew account]");
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "p" -> {
//                    Show list of Products
                    System.out.println(prod1.getProduct());
                    System.out.println(prod2.getProduct());
                    System.out.println(prod3.getProduct());
                }
                case "a" -> {
//                    validation if order not null ad to hasmap, else set order.

                    System.out.println("Enter Product Name: ");
                    String productSearch = scanner.nextLine();

                    System.out.println("Quantity: ");
                    int productQuantity = scanner.nextInt();

                    addToCart(temporaryCart, productsList, productSearch, productQuantity);
                }
                case "c" -> {
                    for (Product each: temporaryCart.values()) {
                        System.out.println(each.getProduct());
                    }
                }
                case "r" -> {System.out.println(acc.viewRecentOrders());}
                case "v" -> {System.out.println(acc.getAccount());}
                case "q" -> {break;}
            }
        }

        System.out.println(acc.getAccount());
    }
}
