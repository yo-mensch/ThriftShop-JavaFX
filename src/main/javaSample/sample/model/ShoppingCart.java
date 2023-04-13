package javaSample.sample.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Shopping_Carts")
public class ShoppingCart {
    @Id
    @Column(name = "Id")
    private int id;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> products;
    @Column(name = "Total_price", nullable = false)
    private float totalPrice;
    @OneToOne
    private User owner;

    public ShoppingCart(List<Product> products, float totalPrice, User owner) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.products = products;
        this.totalPrice = totalPrice;
        this.owner = owner;
    }

    public ShoppingCart() {
    }

    public int getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
