package javaSample.sample.helpers.validator;

import javaSample.sample.helpers.PasswordEncryptHelper;
import javaSample.sample.hibController.UserHibController;
import javaSample.sample.service.UserService;
import javaSample.sample.window.Alert;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserValidator implements Validator{
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    static UserHibController userHibController = new UserHibController(entityManagerFactory);

    public static boolean validateUserRegistration(String username, String email, String password, String confirmedPassword){
        if(validatePassword(password, confirmedPassword)){
            if(!isUsernameTaken(username)){
                if(!isEmailTaken(email)) return true;
                else Alert.display("Error", "Email is taken", "Ok");
            } else Alert.display("Error", "Username is taken", "Ok");
        } else Alert.display("Error", "Passwords do not match, or are shorter than 8 characters.", "Ok");
        return false;
    }

    public static boolean validateUserLogin(String username, String password){
        if(userHibController.getUserByUsername(username)!=null){
            if(userHibController.getUserByUsername(username).getPassword().equals(PasswordEncryptHelper.encryptPassword(password))){
                UserService.getInstance().setLoggedInUser(userHibController.getUserByUsername(username));
                return true;
            }
        }
        return false;
    }

    public static boolean validatePassword(String password, String confirmedPassword){
        if(password.equals(confirmedPassword)  && password.length() >8){
            return true;
        }
        return false;
    }

    public static boolean validateOldPassword(String hashedPassword){
        return hashedPassword.equals(UserService.getInstance().getPasswordHash());
    }

    private static boolean isUsernameTaken(String username){
        if(userHibController.getUserByUsername(username)==null){
            return false;
        }
        return true;
    }

    private static boolean isEmailTaken(String email){
        if(userHibController.getUserByEmail(email)==null){
            return false;
        }
        return true;
    }
}
