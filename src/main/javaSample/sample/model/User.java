package javaSample.sample.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Table(name = "Users")
@Entity
public class User implements Serializable {
    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Username", nullable = false)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Email", nullable = false)
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> productsForSale;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Order> orders;
    @OneToOne(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Address address;
    @OneToOne(mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private ShoppingCart shoppingCart;
    @Column(name = "Balance")
    private float balance;

    public User(String username, String password, String email) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.username = username;
        this.password = password;
        this.email = email;
        this.productsForSale = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.balance = 100;
        this.shoppingCart = new ShoppingCart(new ArrayList<>(),0, this);
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getProductsForSale() {
        return productsForSale;
    }

    public void setProductsForSale(List<Product> productsForSale) {
        this.productsForSale = productsForSale;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
