package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.UserValidator;
import javaSample.sample.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {
    @FXML
    private Button loginBtn, cancelBtn, registerBtn;
    @FXML
    private TextField usernameField, passwordField;
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

    private void login() throws IOException{
        if(!areFieldsEmpty()) {
            if (UserValidator.validateUserLogin(usernameField.getText(), passwordField.getText())) {
                sceneLoader.loadScene(loginBtn, "../../../resources/fxml/MainMenuWindow.fxml");
            } else {
                Alert.display("Error", "username or password not correcto", "Ok");
            }
        } else {
            Alert.display("Error", "All fields must be filled", "Ok");
        }
    }

    private void cancel() throws IOException{
        sceneLoader.loadScene(cancelBtn, "../../../resources/fxml/WelcomeWindow.fxml");
    }

    private boolean areFieldsEmpty(){
        return usernameField.getText().isEmpty() || passwordField.getText().isEmpty();
    }
}
