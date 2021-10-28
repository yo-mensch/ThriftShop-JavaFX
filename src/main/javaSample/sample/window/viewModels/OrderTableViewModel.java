package javaSample.sample.window.viewModels;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderTableViewModel {
    private SimpleIntegerProperty orderId;
    private SimpleFloatProperty totalPrice;
    private SimpleStringProperty orderStatus;

    public OrderTableViewModel(int orderId, float totalPrice, String orderStatus) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.totalPrice = new SimpleFloatProperty(totalPrice);
        this.orderStatus = new SimpleStringProperty(orderStatus);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public float getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleFloatProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getOrderStatus() {
        return orderStatus.get();
    }

    public SimpleStringProperty orderStatusProperty() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus.set(orderStatus);
    }
}
