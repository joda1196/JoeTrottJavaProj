import java.util.UUID;

public class Product {
    String productId;
    String productName;
    double price;
    public Product(String productName, double price) {
        this.productId = generateId();
        this.productName = productName;
        this.price = price;
    }

    public String getProduct() {
        return this.productName;
    }

    private String generateId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }
}
