package main.java.sample.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeWindow implements Initializable {
    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception {
        if(event.getSource()==loginBtn){
            loadLoginWindow();
        } else {
            loadRegisterWindow();
        }
    }

    private void loadLoginWindow() throws IOException {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("../../../resources/fxml/LoginWindow.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void loadRegisterWindow() throws IOException {
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("../../../resources/fxml/RegisterWindow.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
