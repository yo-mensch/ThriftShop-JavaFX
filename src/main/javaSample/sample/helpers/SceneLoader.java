package javaSample.sample.helpers;

import javaSample.sample.model.Order;
import javaSample.sample.model.Product;
import javaSample.sample.window.NewReviewCreateForm;
import javaSample.sample.window.OrderDetailWindow;
import javaSample.sample.window.ProductViewWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public SceneLoader() {
    }

    public void loadScene(Button button, String pathToFxml) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource(pathToFxml));
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Thrift Shop");
        stage.setScene(scene);
        stage.show();
    }

    public void loadOrderDetails(Button button, String pathToFxml, Order order) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathToFxml));
        Parent root;
        root = loader.load();
        OrderDetailWindow orderDetailWindow = loader.getController();
        orderDetailWindow.passOrder(order);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Thrift Shop");
        stage.setScene(scene);
        stage.show();
    }

    public void loadProductDetails(Button button, String pathToFxml, Product product, Order order) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathToFxml));
        Parent root;
        root = loader.load();
        ProductViewWindow productViewWindow = loader.getController();
        productViewWindow.passProduct(product, order);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Thrift Shop");
        stage.setScene(scene);
        stage.show();
    }

    public void loadReviewCreateForm(Button button, String pathToFxml, Product product, Order order) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathToFxml));
        Parent root;
        root = loader.load();
        NewReviewCreateForm newReviewCreateForm = loader.getController();
        newReviewCreateForm.passEntities(product,order);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Thrift Shop");
        stage.setScene(scene);
        stage.show();
    }
}
