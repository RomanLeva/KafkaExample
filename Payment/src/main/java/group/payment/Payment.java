package group.payment;

public class Payment {
    private Order orderDescription;

    public Payment(Order orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Order getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(Order orderDescription) {
        this.orderDescription = orderDescription;
    }
}
