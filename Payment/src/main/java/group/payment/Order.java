package group.payment;

public class Order {
    private java.lang.String description;

    @Override
    public java.lang.String toString() {
        return "Order [description = " + description + "]";
    }
    public Order(java.lang.String description) {
        this.description = description;
    }
    public java.lang.String getDescription() {
        return description;
    }
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

}
