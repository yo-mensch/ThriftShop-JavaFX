package javaSample.sample.model;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "Id")
    private int id;
    @Column (name = "Name")
    private String name;
    @Column (name = "Description")
    private String description;
    @ManyToOne
    private User author;
    @Column(name = "Price",nullable = false)
    private float price;
    @ManyToOne
    private Order order;
    @ManyToOne
    private ShoppingCart shoppingCart;

    public Product(String name, String description, float price, User author) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.order = null;
        this.shoppingCart = null;
    }

    public Product() {
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public User getAuthor() { return author; }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
