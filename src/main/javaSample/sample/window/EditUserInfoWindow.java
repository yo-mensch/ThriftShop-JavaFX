package javaSample.sample.window;

import javaSample.sample.helpers.PasswordEncryptHelper;
import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.UserValidator;
import javaSample.sample.hibController.UserHibController;
import javaSample.sample.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditUserInfoWindow implements Initializable {
    @FXML
    private Button saveBtn, backBtn, addressManagementBtn;
    @FXML
    private TextField changeEmailTextField, oldPasswordField, newPasswordField, confirmedNewPasswordField;
    @FXML
    private Text balanceTextField, addressTitle;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    UserHibController userHibController = new UserHibController(entityManagerFactory);
    private SceneLoader sceneLoader;

    public EditUserInfoWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        balanceTextField.setText("$"+UserService.getInstance().getLoggedInUser().getBalance());
        if(UserService.getInstance().getLoggedInUser().getAddress()==null){
            addressTitle.setText("You haven't provided your address");
            addressManagementBtn.setText("Add address");
        } else {
            addressTitle.setText("Your address info:");
            addressManagementBtn.setText("Edit address");
        }
    }


    //needs refactoring
    public void handleButtonClick(javafx.event.ActionEvent event) throws IOException {
        if(event.getSource()==saveBtn){
            if(!isEmailFieldEmpty()){
                UserService.getInstance().getLoggedInUser().setEmail(changeEmailTextField.getText());
                userHibController.updateUser(UserService.getInstance().getLoggedInUser());
            }
            if(!areAllPasswordFieldsEmpty()){
                if(!arePasswordFieldsEmpty()){
                    String hashedOldPassword = PasswordEncryptHelper.encryptPassword(oldPasswordField.getText());
                    if(UserValidator.validateOldPassword(hashedOldPassword)){
                        if(UserValidator.validatePassword(newPasswordField.getText(),confirmedNewPasswordField.getText())){
                            String encryptedPassword = PasswordEncryptHelper.encryptPassword(newPasswordField.getText());
                            UserService.getInstance().getLoggedInUser().setPassword(encryptedPassword);
                            userHibController.updateUser(UserService.getInstance().getLoggedInUser());
                            backToMainMenu();
                        }
                    } else Alert.display("Error", "Your old password is incorrect", "Ok");
                } else Alert.display("Error", "Please fill in all password fields", "Ok");
            }
        }
        if(event.getSource()==addressManagementBtn){
            sceneLoader.loadScene(addressManagementBtn,"../../../resources/fxml/EditUserAddressWindow.fxml");
        }
        else backToMainMenu();
    }

    private boolean isEmailFieldEmpty() {
        return changeEmailTextField.getText().isEmpty();
    }

    private boolean areAllPasswordFieldsEmpty(){
        return oldPasswordField.getText().isEmpty() && newPasswordField.getText().isEmpty() && confirmedNewPasswordField.getText().isEmpty();
    }

    private boolean arePasswordFieldsEmpty(){
        return oldPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() || confirmedNewPasswordField.getText().isEmpty();
    }

    private void backToMainMenu() throws IOException{
        sceneLoader.loadScene(backBtn,"../../../resources/fxml/MainMenuWindow.fxml");
    }
}
