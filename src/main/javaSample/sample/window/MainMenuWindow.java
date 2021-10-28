package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.hibController.OrderHibController;
import javaSample.sample.hibController.ProductHibController;
import javaSample.sample.hibController.ShoppingCartHibController;
import javaSample.sample.model.Order;
import javaSample.sample.model.Product;
import javaSample.sample.service.UserService;
import javaSample.sample.window.viewModels.OrderTableViewModel;
import javaSample.sample.window.viewModels.UserProductTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuWindow implements Initializable {
    @FXML
    private Button logoutBtn, editInfoBtn, viewShoppingCartBtn, addToCartBtn;
    @FXML
    private Text yourOrderHeading, yourProductHeading;
    @FXML
    private Button addNewProductBtn, manageProductBtn;
    @FXML
    private ListView<Product> availableProductList;
    @FXML
    private TableView<OrderTableViewModel> yourOrderTable;
    @FXML
    private TableColumn<OrderTableViewModel,Integer> orderId;
    @FXML
    private TableColumn<OrderTableViewModel,Float> totalPrice;
    @FXML
    private TableColumn<OrderTableViewModel,String> orderStatus;
    @FXML
    private TableView<UserProductTableViewModel> yourProducts;
    @FXML
    private TableColumn<UserProductTableViewModel,Integer> productID;
    @FXML
    private TableColumn<UserProductTableViewModel,String> productName, productDescr;
    @FXML
    private TableColumn<UserProductTableViewModel, Float> productPrice;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);
    ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    OrderHibController orderHibController = new OrderHibController(entityManagerFactory);
    private SceneLoader sceneLoader;

    public MainMenuWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setHeadings();
        addData();
    }

    private void addData(){
        loadAvailableProducts();
        loadYourProductTable();
        loadOrderTable();
    }

    private void loadOrderTable() {
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        ObservableList<OrderTableViewModel> yourOrders = FXCollections.observableArrayList();
        for(Order order: orderHibController.getOrdersByUserId(UserService.getInstance().getLoggedInUser().getId())){
            OrderTableViewModel orderInTheTable = new OrderTableViewModel(order.getId(),order.getPayment().getAmount(),order.getOrderStatus().name());
            yourOrders.add(orderInTheTable);
        }
        yourOrderTable.setItems(yourOrders);
    }

    private void loadAvailableProducts() {
        ObservableList<Product> availableProducts = FXCollections.observableArrayList();
        for(Product product: productHibController.getAvailableProducts(UserService.getInstance().getLoggedInUser().getId())){
            availableProducts.add(product);
        }
        availableProductList.setItems(availableProducts);
        availableProductList.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " \t"+ item.getDescription()+ " \t" +item.getPrice());
                }
            }
        });
    }

    private void loadYourProductTable() {
        productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productDescr.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        yourProducts.setItems(getYourProducts());
    }

    private ObservableList<UserProductTableViewModel> getYourProducts(){
        ObservableList<UserProductTableViewModel> foundProducts = FXCollections.observableArrayList();
        for(Product product: productHibController.getProductsByAuthorId(UserService.getInstance().getLoggedInUser().getId())){
            UserProductTableViewModel productInTheTable = new UserProductTableViewModel(product.getId(),product.getName(),product.getDescription(),product.getPrice());
            foundProducts.add(productInTheTable);
        }
        return foundProducts;
    }

    private void setHeadings(){
        if(UserService.getInstance().getOrderList().isEmpty()){
            yourOrderHeading.setText("You have no orders...");
        } else {
            yourOrderHeading.setText("Your orders:");
        }
        if(UserService.getInstance().getProductsForSale().isEmpty()){
            yourProductHeading.setText("You have no products...");
        } else {
            yourProductHeading.setText("Your products:");
        }
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==logoutBtn){
            logOut();
        }
        if(event.getSource()==editInfoBtn){
            editUserInfo();
        }
        if(event.getSource()==viewShoppingCartBtn){
            loadShoppingCart();
        }
        if(event.getSource()==addToCartBtn){
            UserService.getInstance().addProductToCart(availableProductList.getSelectionModel().getSelectedItem());
            productHibController.updateProduct(availableProductList.getSelectionModel().getSelectedItem());
            shoppingCartHibController.updateShoppingCart(UserService.getInstance().getShoppingCart());
            Alert.display("Info","Product added to your cart!", "Ok");
        }
    }

    public void handleProductButtonClick(javafx.event.ActionEvent event) throws IOException {
        if(event.getSource()==manageProductBtn){
            sceneLoader.loadScene(manageProductBtn,"../../../resources/fxml/EditProductFormWindow.fxml");
        }
        if(event.getSource()==addNewProductBtn){
            sceneLoader.loadScene(addNewProductBtn, "../../../resources/fxml/NewProductFormWindow.fxml");
        }
    }

    private void loadShoppingCart() throws IOException{
        sceneLoader.loadScene(viewShoppingCartBtn,"../../../resources/fxml/ShoppingCartWindow.fxml");
    }


    private void editUserInfo() throws IOException {
        sceneLoader.loadScene(editInfoBtn,"../../../resources/fxml/EditUserInfoWindow.fxml");
    }

    private void logOut() throws IOException {
        UserService.getInstance().logOut();
        sceneLoader.loadScene(logoutBtn,"../../../resources/fxml/WelcomeWindow.fxml");
    }
}
