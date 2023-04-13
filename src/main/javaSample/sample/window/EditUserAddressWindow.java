package javaSample.sample.window;

import javaSample.sample.helpers.SceneLoader;
import javaSample.sample.helpers.validator.AddressValidator;
import javaSample.sample.hibController.AddressHibController;
import javaSample.sample.hibController.UserHibController;
import javaSample.sample.model.Address;
import javaSample.sample.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditUserAddressWindow implements Initializable {
    @FXML
    private Button cancelBtn, saveChangesBtn;
    @FXML
    private TextField streetTextField, cityTextField, postalCodeTextField, countryTextField;

    private Address userAddress = UserService.getInstance().getLoggedInUser().getAddress();
    private SceneLoader sceneLoader;
    private AddressValidator addressValidator = new AddressValidator();
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    UserHibController userHibController = new UserHibController(entityManagerFactory);
    AddressHibController addressHibController = new AddressHibController(entityManagerFactory);

    public EditUserAddressWindow() {
        this.sceneLoader = new SceneLoader();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(userAddress!=null){
            streetTextField.setText(userAddress.getStreet());
            cityTextField.setText(userAddress.getCity());
            postalCodeTextField.setText(Integer.toString(userAddress.getPostalCode()));
            countryTextField.setText(userAddress.getCountry());
        }
    }

    public void handleButtonClick(javafx.event.ActionEvent event) throws IOException{
        if(event.getSource()==cancelBtn){
            cancel();
        }
        if(event.getSource()==saveChangesBtn){
            if(addressValidator.validateAddress(streetTextField.getText(),cityTextField.getText(),postalCodeTextField.getText(),countryTextField.getText())){
                if(userAddress==null){
                    Address address = new Address(streetTextField.getText(),cityTextField.getText(),Integer.parseInt(postalCodeTextField.getText()),countryTextField.getText(),UserService.getInstance().getLoggedInUser());
                    UserService.getInstance().getLoggedInUser().setAddress(address);
                    userHibController.updateUser(UserService.getInstance().getLoggedInUser());
                    addressHibController.createAddress(address);
                } else {
                    userAddress.setStreet(streetTextField.getText());
                    userAddress.setCity(cityTextField.getText());
                    userAddress.setPostalCode(Integer.parseInt(postalCodeTextField.getText()));
                    userAddress.setCountry(countryTextField.getText());
                    userHibController.updateUser(UserService.getInstance().getLoggedInUser());
                    addressHibController.updateAddress(userAddress);
                }
                cancel();
            } else {
                Alert.display("Error","Please fill in all the fields", "Ok");
            }
        }
    }

    private void cancel() throws IOException{
        sceneLoader.loadScene(cancelBtn,"../../../resources/fxml/EditUserInfoWindow.fxml");
    }
}
