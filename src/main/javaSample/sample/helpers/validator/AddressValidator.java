package javaSample.sample.helpers.validator;

public class AddressValidator implements Validator{

    public AddressValidator() {
    }

    public static boolean validateAddress(String streetName, String city, String postalCode, String country){
        return (hasOnlyNumbers(postalCode)&&!(areFieldsEmpty(streetName,city,postalCode,country)));
    }

    public static boolean hasOnlyNumbers(String str){
        return str.matches("[0-9]+");
    }

    public static boolean areFieldsEmpty(String streetName, String city, String postalCode, String country){
        return (streetName.isEmpty()&&city.isEmpty()&&postalCode.isEmpty()&&country.isEmpty());
    }
}
