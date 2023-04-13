package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.ProductValidator;
import javaSample.sample.hibController.ProductHibController;
import javaSample.sample.model.Product;
import javaSample.sample.service.UserService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditProductFormWindow implements Initializable {
    @FXML
    private ChoiceBox productChoicesList;
    private List<Product> productList;
    @FXML
    private TextField productNameField,productPriceField;
    @FXML
    private TextArea productDescriptionStr;
    @FXML
    private Button deleteProductBtn, cancelBtn, saveChangesBtn;

    private Product selectedProduct = null;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    private SceneLoader sceneLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productList = productHibController.getProductsByAuthorId(UserService.getInstance().getLoggedInUser().getId());
        addProductsToChoiceBox();
        productChoicesList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                Product[] prodArr = new Product[productList.size()];
                productList.toArray(prodArr);

                selectedProduct=prodArr[new_value.intValue()];
                productNameField.setText(prodArr[new_value.intValue()].getName());
                productDescriptionStr.setText(prodArr[new_value.intValue()].getDescription());
                productPriceField.setText(Float.toString(prodArr[new_value.intValue()].getPrice()));
            }
        });
    }

    private void addProductsToChoiceBox(){
        for(Product product: productHibController.getProductsByAuthorId(UserService.getInstance().getLoggedInUser().getId())){
            productChoicesList.getItems().add(product);
        }
    }

    public EditProductFormWindow() {
        this.sceneLoader = new SceneLoader();
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception{
        if(event.getSource()==deleteProductBtn){
            if(selectedProduct!=null){
                productHibController.deleteProduct(selectedProduct.getId());
                UserService.getInstance().getProductsForSale().remove(selectedProduct);
                cancel();
            } else Alert.display("Error","Please select a product","Ok");
        }
        if(event.getSource()==saveChangesBtn){
            if(ProductValidator.validateProduct(productNameField.getText(),productDescriptionStr.getText(),productPriceField.getText())){
                selectedProduct.setName(productNameField.getText());
                selectedProduct.setDescription(productDescriptionStr.getText());
                selectedProduct.setPrice(Float.parseFloat(productPriceField.getText()));
                UserService.getInstance().updateProduct(selectedProduct);
                productHibController.updateProduct(selectedProduct);
                cancel();
            } else Alert.display("Error", "You have to fill all the fields","Ok");
        }
        if(event.getSource()==cancelBtn){
            cancel();
        }
    }

    private void cancel()throws IOException{
        sceneLoader.loadScene(cancelBtn,"../../../resources/fxml/MainMenuWindow.fxml");
    }
}
