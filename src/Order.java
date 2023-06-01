import java.util.HashMap;

public class Order {
    String orderId;
    Account userId;
    HashMap<String, Product> products;
    double totalCost;
    public Order(String orderId, Account userId, double totalCost) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = new HashMap<String, Product>();
        this.totalCost = totalCost;
    }
}
