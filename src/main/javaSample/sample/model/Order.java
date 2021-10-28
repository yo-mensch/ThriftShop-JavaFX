package javaSample.sample.model;

import javaSample.sample.model.enumerations.OrderStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "Id")
    private int id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> products;
    @OneToOne(mappedBy = "order")
    private Payment payment;
    @Enumerated(EnumType.STRING)
    @Column(name = "Order_Status",nullable = false)
    private OrderStatus orderStatus;
    @ManyToOne
    private User buyer;

    public Order(List<Product> products, User buyer) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.products = products;
        this.payment = null;
        this.orderStatus = OrderStatus.PROCESSING;
        this.buyer = buyer;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public User getBuyer() {
        return buyer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
