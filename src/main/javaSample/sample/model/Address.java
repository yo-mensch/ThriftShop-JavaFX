package javaSample.sample.model;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Street", nullable = false)
    private String street;
    @Column(name = "City", nullable = false)
    private String city;
    @Column(name = "Postal_Code", nullable = false)
    private int postalCode;
    @Column(name = "Country", nullable = false)
    private String country;
    @OneToOne
    private User user;

    public Address(String street, String city, int postalCode, String country, User user) {
        this.id = new Random(System.currentTimeMillis()).nextInt();
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.user = user;
    }

    public Address() {
    }

    public int getId() { return id; }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }
}
