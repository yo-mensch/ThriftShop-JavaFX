package javaSample.sample.helpers.validator;

//import javaSample.sample.window.Alert;
//import javaSample.sample.window.AlertWrapper;

public class ProductValidator implements Validator{

    public static boolean validateProduct(String productName,String productDescription, String productPrice){
        //AlertWrapper alertWrapper = new AlertWrapper(new Alert());
        if(validateFields(productName,productDescription,productPrice)){
            if(validatePrice(productPrice)){
                return true;
            }
            //else showPriceError();
            return false;
        }
        //else showBlankFieldError();
        return false;
    }

    public static boolean validateFields(String productName,String productDescription, String productPrice){
        if(!productName.isEmpty()&&!productDescription.isEmpty()&&!productPrice.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean validatePrice(String productPrice){
        float price = Float.parseFloat(productPrice);
        if(price>0.0f){
            return true;
        }
        return false;
    }

    public static boolean isProductValid(String productName,String productDescription, String productPrice){
        return validateFields(productName,productDescription,productPrice)&&validatePrice(productPrice);
    }

//    public static void showPriceError(){
//        Alert.display("Error","Price must be bigger than 0.0","Ok");
//    }

//    public static void showBlankFieldError(){
//        Alert.display("Error","Please fill in all the fields", "Ok");
//    }
}
