public class Order {
    String orderId;
    Account userId;
    float totalCost;
    public Order(String orderId, Account userId, float totalCost) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalCost = totalCost;
    }
}
