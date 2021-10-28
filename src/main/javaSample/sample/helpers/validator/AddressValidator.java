package javaSample.sample.helpers.validator;

public class AddressValidator implements Validator{

    public AddressValidator() {
    }

    public static boolean validateAddress(String streetName, String city, String postalCode, String country){
        return !(streetName.isEmpty()&&city.isEmpty()&&postalCode.isEmpty()&&country.isEmpty());
    }
}
