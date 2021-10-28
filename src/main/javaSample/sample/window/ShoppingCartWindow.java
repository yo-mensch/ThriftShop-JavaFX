package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.hibController.ProductHibController;
import javaSample.sample.hibController.ShoppingCartHibController;
import javaSample.sample.model.Product;
import javaSample.sample.service.UserService;
import javaSample.sample.window.viewModels.ShoppingCartTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartWindow implements Initializable {
    @FXML
    private Text yourCartText, priceText;
    @FXML
    private TableView<ShoppingCartTableViewModel> shoppingCartProductTable;
    @FXML
    private TableColumn<ShoppingCartTableViewModel,Integer> productId;
    @FXML
    private TableColumn<ShoppingCartTableViewModel,String> productName, productDescription;
    @FXML
    private TableColumn<ShoppingCartTableViewModel, Float> productPrice;
    @FXML
    private Button backBtn, checkoutBtn, removeItemBtn;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);
    ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    private SceneLoader sceneLoader;

    public ShoppingCartWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setHeading();
        loadShoppingCartTable();
        setPriceText();
    }

    private void loadShoppingCartTable() {
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        shoppingCartProductTable.setItems(getYourProducts());
    }

    private ObservableList<ShoppingCartTableViewModel> getYourProducts(){
        ObservableList<ShoppingCartTableViewModel> foundProducts = FXCollections.observableArrayList();
        for(Product product: shoppingCartHibController.getCartByUserId(UserService.getInstance().getLoggedInUser().getId()).getProducts()){
            ShoppingCartTableViewModel productInTheTable = new ShoppingCartTableViewModel(product.getId(),product.getName(),product.getDescription(),product.getPrice());
            foundProducts.add(productInTheTable);
        }
        return foundProducts;
    }

    private void setPriceText() {
        priceText.setText("$"+UserService.getInstance().getShoppingCart().getTotalPrice());
    }

    private void setHeading() {
        if(isCartEmpty()){
            yourCartText.setText("Your Cart is empty!");
        } else {
            yourCartText.setText("Your Cart:");
        }
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==backBtn){
            backToMainMenu();
        }
        if(event.getSource()==checkoutBtn){
            checkout();
            backToMainMenu();
        }
        if(event.getSource()==removeItemBtn){
            removeItem();
            backToMainMenu();
        }
    }

    private void removeItem() {
        Product selectedProduct = UserService.getInstance().findProductInCartById(shoppingCartProductTable.getSelectionModel().getSelectedItem().getProductId());
        UserService.getInstance().removeProductFromCart(selectedProduct);
        productHibController.updateProduct(selectedProduct);
        shoppingCartHibController.updateShoppingCart(UserService.getInstance().getShoppingCart());
        Alert.display("Info","Product removed from your cart!", "Ok");
    }

    private void checkout() throws Exception{
        if (!isCartEmpty()){
            if (UserService.getInstance().getLoggedInUser().getBalance()<UserService.getInstance().getShoppingCart().getTotalPrice()){
                Alert.display("Checkout failed", "You don't have enough mopney in your balance!","Ok");
            } else if (UserService.getInstance().getLoggedInUser().getAddress()==null){
                Alert.display("Checkout failed","You haven't provided your address.\n Add your address and try again.","Ok");
                sceneLoader.loadScene(checkoutBtn,"../../../resources/fxml/EditUserAddressWindow.fxml");
            } else {
                UserService.getInstance().checkout();
            }

        } else Alert.display("Checkout failed", "You cannot checkout with an empty cart!", "Ok");
    }

    private void backToMainMenu() throws IOException {
        sceneLoader.loadScene(backBtn,"../../../resources/fxml/MainMenuWindow.fxml");
    }

    private boolean isCartEmpty(){
        return UserService.getInstance().getShoppingCart().getProducts().isEmpty();
    }
}
