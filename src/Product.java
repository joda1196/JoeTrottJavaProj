import java.util.UUID;

public class Product {
    int productId;
    String productName;
    double price;
    int quantity;
    public Product(int id, String productName, double price, int quantity) {
        this.productId = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public void getProduct() {
        System.out.println("-----------------------");
        System.out.println(this.productName);
        System.out.println(this.price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
