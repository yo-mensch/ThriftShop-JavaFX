package javaSample.sample.model;

import javaSample.sample.model.enumerations.PaymentStatus;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Amount")
    private float amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "Payment_status", nullable = false)
    private PaymentStatus paymentStatus;
    @ManyToOne
    private User user;
    @OneToOne
    private Order order;

    public Payment(float amount, User user, Order order) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PROCESSING;
        this.user = user;
        this.order = order;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
