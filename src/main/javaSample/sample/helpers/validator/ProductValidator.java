package javaSample.sample.helpers.validator;

import javaSample.sample.window.Alert;

public class ProductValidator implements Validator{

    public static boolean validateProduct(String productName,String productDescription, String productPrice){
        if(!productName.isEmpty()&&!productDescription.isEmpty()&&!productPrice.isEmpty()){
            float price = Float.parseFloat(productPrice);
            if(price>0.0f){
                return true;
            }
            else Alert.display("Error","Price must be bigger than 0.0","Ok");
        }
        else Alert.display("Error","Please fill in all the fields", "Ok");
        return false;
    }
}
