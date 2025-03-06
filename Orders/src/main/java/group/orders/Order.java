package group.orders;

public class Order {
    private String description;

    @Override
    public String toString() {
        return "Order [description = " + description + "]";
    }
    public Order(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Order() {}
}
