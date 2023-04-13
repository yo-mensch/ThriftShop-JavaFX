package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.hibController.ProductHibController;
import javaSample.sample.hibController.ReviewHibController;
import javaSample.sample.model.Order;
import javaSample.sample.model.Product;
import javaSample.sample.model.Review;
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

public class ProductViewWindow implements Initializable {
    @FXML
    Text productName, productDescription,productPrice, reviewsTitle;
    @FXML
    private Button addReviewBtn, backBtn;
    @FXML
    ListView<Review> reviewListView;
    private Product selectedProduct;
    private SceneLoader sceneLoader;
    private Order productOrder;

    public ProductViewWindow() {
        this.sceneLoader = new SceneLoader();
    }

    public void passProduct(Product product, Order order){
        selectedProduct = product;
        productOrder = order;
        setTextFields();
        loadReviews();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void setTextFields() {
        productName.setText(selectedProduct.getName());
        productDescription.setText(selectedProduct.getDescription());
        productPrice.setText(selectedProduct.getPrice()+"$");
    }

    private void loadReviews(){
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        for(Review review: selectedProduct.getReviews()){
            reviews.add(review);
        }
        reviewListView.setItems(reviews);
        reviewListView.setCellFactory(param -> new ListCell<Review>() {
            @Override
            protected void updateItem(Review item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null ) {
                    setText(null);
                } else {
                    setText(item.getRating() + " \t"+ item.getMessage());
                }
            }
        });
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==backBtn){
            sceneLoader.loadOrderDetails(backBtn,"../../../resources/fxml/OrderDetailWindow.fxml",productOrder);
        } else {
            sceneLoader.loadReviewCreateForm(addReviewBtn,"../../../resources/fxml/NewReviewCreateForm.fxml",selectedProduct,productOrder);
        }
    }
}
