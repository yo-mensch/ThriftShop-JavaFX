package main.java.sample.window;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.java.sample.helpers.SceneLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {
    @FXML
    private Button loginBtn, cancelBtn, registerBtn;
    private SceneLoader sceneLoader;

    public LoginWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception {
        if(event.getSource()==loginBtn){
            login();
        } else if (event.getSource()==registerBtn){
            loadRegisterWindow();
        } else {
            cancel();
        }
    }

    private void loadRegisterWindow() throws IOException{
        sceneLoader.loadScene(registerBtn, "../../../resources/fxml/RegisterWindow.fxml");
    }

    private void login() {
    }

    private void cancel() throws IOException{
        sceneLoader.loadScene(cancelBtn, "../../../resources/fxml/WelcomeWindow.fxml");
    }
}
