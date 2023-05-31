public class Order {
    String orderId;
    User userId;
    float totalCost;
    public Order(String orderId, User userId, float totalCost) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalCost = totalCost;
    }
}
