package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeWindow implements Initializable {
    @FXML
    private Button loginBtn, registerBtn;
    private SceneLoader sceneLoader;

    public WelcomeWindow() {
        this.sceneLoader = new SceneLoader();
    }

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
        sceneLoader.loadScene(loginBtn, "../../../resources/fxml/LoginWindow.fxml");
    }

    private void loadRegisterWindow() throws IOException {
        sceneLoader.loadScene(registerBtn,"../../../resources/fxml/RegisterWindow.fxml");
    }
}
