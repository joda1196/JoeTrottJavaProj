import java.util.UUID;

public class Product {
    String productId;
    String productName;
    double price;
    int quantity;
    public Product(String productName, double price, int quantity) {
        this.productId = generateId();
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public void getProduct() {
        System.out.println("-----------------------");
        System.out.println(this.productName);
        System.out.println(this.price);
    }

    private String generateId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }
}
