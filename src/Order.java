import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class Order {
    int orderId;
    String userId;
    LinkedHashMap<String, Product> cartOfProducts;
    double totalCost;
    public Order(int id, String userId) {
        this.orderId = id;
        this.userId = userId;
        this.cartOfProducts = new LinkedHashMap<>();
        this.totalCost = calculateCost();
    }

    public double calculateCost () {
        for (Product each: cartOfProducts.values()) {
            this.totalCost += each.price;
        }
        return totalCost;
    }
}
