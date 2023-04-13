package javaSample.sample.window;

import javaSample.sample.helpers.PasswordEncryptHelper;
import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.UserValidator;
import javaSample.sample.hibController.UserHibController;
import javaSample.sample.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindow implements Initializable {
    @FXML
    private Button cancelBtn, registerBtn;
    @FXML
    private TextField username, password, confirmedPassword, email;
    private SceneLoader sceneLoader;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public RegisterWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws Exception {
        if(event.getSource()==registerBtn){
            if(!areFieldsEmpty()) {
                registerUser();
                cancel();
            }
            else Alert.display("Error", "All fields must be filled", "Ok");
        }
        else cancel();
    }

    public void registerUser(){
        if(UserValidator.validateUserRegistration(username.getText(),email.getText(),password.getText(),confirmedPassword.getText())){
            String encryptedPassword = PasswordEncryptHelper.encryptPassword(password.getText());
            User user = new User(username.getText(),encryptedPassword,email.getText());
            userHibController.createUser(user);
        }
    }

    private void cancel() throws IOException {
        sceneLoader.loadScene(cancelBtn, "../../../resources/fxml/WelcomeWindow.fxml");
    }

    private boolean areFieldsEmpty(){
        return username.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() || confirmedPassword.getText().isEmpty();
    }
}
