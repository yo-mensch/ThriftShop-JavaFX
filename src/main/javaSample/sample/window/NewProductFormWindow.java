package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.ProductValidator;
import javaSample.sample.hibController.ProductHibController;
import javaSample.sample.model.Product;
import javaSample.sample.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewProductFormWindow implements Initializable {
    @FXML
    private Button createProductBtn, cancelBtn;
    @FXML
    private TextField newProductNameField, newProductPriceField;
    @FXML
    private TextArea newProductDescriptionStr;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    private SceneLoader sceneLoader;

    public NewProductFormWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==cancelBtn){
            cancel();
        }
        else if (event.getSource()==createProductBtn){
            if(ProductValidator.validateProduct(newProductNameField.getText(),newProductDescriptionStr.getText(),newProductPriceField.getText())){
                Product product = new Product(newProductNameField.getText(),newProductDescriptionStr.getText(),Float.parseFloat(newProductPriceField.getText()), UserService.getInstance().getLoggedInUser());
                UserService.getInstance().getLoggedInUser().getProductsForSale().add(product);
                productHibController.createProduct(product);
                cancel();
            }
        }
    }

    private void cancel() throws IOException {
        sceneLoader.loadScene(cancelBtn,"../../../resources/fxml/MainMenuWindow.fxml");
    }
}
