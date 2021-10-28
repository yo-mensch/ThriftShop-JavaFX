package javaSample.sample.helpers;

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
}
