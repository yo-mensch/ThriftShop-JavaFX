package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.model.Order;
import javaSample.sample.model.Product;
import javaSample.sample.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailWindow implements Initializable {
    @FXML
    private ListView<Product> orderedProductList;
    @FXML
    private Button viewProductDetailsBtn, backBtn;
    @FXML
    private Text orderStatusText, paymentStatusText, paymentPriceText;
    private Order selectedOrder;
    private SceneLoader sceneLoader;

    public OrderDetailWindow() {
        this.sceneLoader = new SceneLoader();
    }

    public void passOrder(Order order){
        selectedOrder = order;
        System.out.println(order.getOrderStatus());
        setTextFields();
        loadOrderedProducts();
    }

    private void loadOrderedProducts() {
        ObservableList<Product> availableProducts = FXCollections.observableArrayList();
        for(Product product: selectedOrder.getProducts()){
            availableProducts.add(product);
        }
        orderedProductList.setItems(availableProducts);
        orderedProductList.setCellFactory(param -> new ListCell<Product>() {
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

    private void setTextFields() {
        orderStatusText.setText(String.valueOf(selectedOrder.getOrderStatus()));
        paymentStatusText.setText(String.valueOf(selectedOrder.getPayment().getPaymentStatus()));
        paymentPriceText.setText(selectedOrder.getPayment().getAmount()+"$");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==backBtn){
            sceneLoader.loadScene(backBtn,"../../../resources/fxml/MainMenuWindow.fxml");
        } else if(event.getSource()==viewProductDetailsBtn){
            Product selected = orderedProductList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                sceneLoader.loadProductDetails(viewProductDetailsBtn,"../../../resources/fxml/ProductViewWindow.fxml", selected, selectedOrder);
            } else {
                Alert.display("Error", "You must select a product to continue!", "Ok");
            }
        }
    }
}
