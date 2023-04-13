package javaSample.sample;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../../resources/fxml/WelcomeWindow.fxml")));
        primaryStage.setTitle("Welcome to Thrift Shop");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
