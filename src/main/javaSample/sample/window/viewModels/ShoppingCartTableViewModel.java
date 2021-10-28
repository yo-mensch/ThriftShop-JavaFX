package javaSample.sample.window.viewModels;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShoppingCartTableViewModel {
    private SimpleIntegerProperty productId;
    private SimpleStringProperty productName;
    private SimpleStringProperty productDescription;
    private SimpleFloatProperty productPrice;

    public ShoppingCartTableViewModel(int productId, String productName, String productDescription, float productPrice) {
        this.productId = new SimpleIntegerProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.productDescription = new SimpleStringProperty(productDescription);
        this.productPrice = new SimpleFloatProperty(productPrice);
    }

    public int getProductId() {
        return productId.get();
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getProductDescription() {
        return productDescription.get();
    }

    public SimpleStringProperty productDescriptionProperty() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription.set(productDescription);
    }

    public float getProductPrice() {
        return productPrice.get();
    }

    public SimpleFloatProperty productPriceProperty() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice.set(productPrice);
    }
}
