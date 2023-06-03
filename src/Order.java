import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class Order {
    String orderId;
    String userId;
    LinkedHashMap<String, Product> cartOfProducts;
    double totalCost;
    public Order(String userId) {
        this.orderId = generateId();
        this.userId = userId;
        this.cartOfProducts = new LinkedHashMap<>();
        this.totalCost = calculateCost();
    }

    private String generateId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    public double calculateCost () {
        double totalCost = 0;
        for (int i = 0; i < this.cartOfProducts.size(); i++) {
            totalCost += this.cartOfProducts.get(i).price;
        }
        return totalCost;
    }
}
