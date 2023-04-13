package javaSample.sample.service;

import javaSample.sample.hibController.*;
import javaSample.sample.model.*;
import javaSample.sample.model.enumerations.OrderStatus;
import javaSample.sample.model.enumerations.PaymentStatus;
import javaSample.sample.window.Alert;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserService {
    private static UserService instance = new UserService();
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);
    private OrderHibController orderHibController = new OrderHibController(entityManagerFactory);
    private PaymentHibController paymentHibController = new PaymentHibController(entityManagerFactory);
    private ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);
    private User loggedInUser;

    public UserService() {
    }

    public static UserService getInstance(){
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String getUsername() {
        return loggedInUser.getUsername();
    }

    public String getEmail() {
        return loggedInUser.getEmail();
    }

    public String getPasswordHash() { return loggedInUser.getPassword(); }

    public List<Product> getProductsForSale(){
        return loggedInUser.getProductsForSale();
    }

    public ShoppingCart getShoppingCart(){
        return loggedInUser.getShoppingCart();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<Order> getOrderList() { return loggedInUser.getOrders(); }

    public void updateProduct(Product product){
        for(Product prd : getProductsForSale()){
            if(prd.getId()==product.getId()){
                getProductsForSale().remove(prd);
                getProductsForSale().add(product);
            }
        }
    }

    public Product findProductInCartById(int productId){
        for(Product product: getShoppingCart().getProducts()){
            if(product.getId()==productId){
                return product;
            }
        }
        return null;
    }

    public void addProductToCart(Product product){
        getShoppingCart().getProducts().add(product);
        product.setShoppingCart(getShoppingCart());
        calculateTotalPrice();
    }

    public void removeProductFromCart(Product product){
        getShoppingCart().getProducts().remove(product);
        product.setShoppingCart(null);
        calculateTotalPrice();
    }

    private void calculateTotalPrice(){
        float price = 0.0f;
        for(Product product: getShoppingCart().getProducts()){
            price += product.getPrice();
        }
        getShoppingCart().setTotalPrice(price);
    }

    public void checkout(){
        Order order = new Order(getShoppingCart().getProducts(),getLoggedInUser());
        try{
            makePayment(order);
        } catch (Error err){
            order.setOrderStatus(OrderStatus.CANCELLED);
            Alert.display("Error","Something went wrong. Your order has been cancelled.", "Ok");
            cancelOrder(order);
        }
    }

    private void makePayment(Order order) {
        Payment payment = new Payment(getShoppingCart().getTotalPrice(),getLoggedInUser(),order);
        getLoggedInUser().setBalance(getLoggedInUser().getBalance()-getShoppingCart().getTotalPrice());
        for(Product product: getShoppingCart().getProducts()){
            product.getAuthor().setBalance(product.getAuthor().getBalance()+ product.getPrice());
            userHibController.updateUser(product.getAuthor());
            product.setOrder(order);
            product.setShoppingCart(null);
            productHibController.updateProduct(product);
        }
        payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        order.setPayment(payment);
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderHibController.createOrder(order);
        paymentHibController.createPayment(payment);
        getShoppingCart().getProducts().clear();
        getShoppingCart().setTotalPrice(0.0f);
        shoppingCartHibController.updateShoppingCart(getShoppingCart());
        getLoggedInUser().getOrders().add(order);
        userHibController.updateUser(getLoggedInUser());
    }

    private void cancelOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        getShoppingCart().getProducts().clear();
        getShoppingCart().setTotalPrice(0.0f);
        shoppingCartHibController.updateShoppingCart(getShoppingCart());
    }

    public void logOut(){
        getInstance().setLoggedInUser(null);
    }
}
