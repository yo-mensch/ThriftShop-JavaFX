package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.ReviewValidator;
import javaSample.sample.hibController.ProductHibController;
import javaSample.sample.hibController.ReviewHibController;
import javaSample.sample.model.Order;
import javaSample.sample.model.Product;
import javaSample.sample.model.Review;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewReviewCreateForm implements Initializable {
    @FXML
    private Button backBtn, submitReviewBtn;
    @FXML
    private TextField ratingField;
    @FXML
    private TextArea messageField;
    private Product product;
    private Order order;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    ReviewHibController reviewHibController = new ReviewHibController(entityManagerFactory);
    ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    private SceneLoader sceneLoader;

    public NewReviewCreateForm() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void passEntities(Product prod, Order ord){
        product = prod;
        order = ord;
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==backBtn){
            sceneLoader.loadProductDetails(backBtn,"../../../resources/fxml/ProductViewWindow.fxml",product,order);
        } else {
            if(ReviewValidator.validateRatingRange(Integer.parseInt(ratingField.getText()))){
                Review review = new Review(product, messageField.getText(), Integer.parseInt(ratingField.getText()));
                List<Review> reviews = product.getReviews();
                reviews.add(review);
                product.setReviews(reviews);
                reviewHibController.createReview(review);
                productHibController.updateProduct(product);
                sceneLoader.loadProductDetails(backBtn,"../../../resources/fxml/ProductViewWindow.fxml",product,order);
            } else {
                Alert.display("Error", "The rating must be between 1 and 10","Ok");
            }
        }
    }
}
