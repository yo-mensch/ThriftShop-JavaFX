package javaSample.sample.model;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @Column(name = "Id")
    private int id;
    @ManyToOne
    private Product product;
    @Column(name = "message")
    private String message;
    @Column(name = "rating")
    private int rating;

    public Review(Product product, String message, int rating) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.product = product;
        this.message = message;
        this.rating = rating;
    }

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
