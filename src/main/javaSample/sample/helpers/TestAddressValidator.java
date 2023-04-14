package javaSample.sample.helpers;
import javaSample.sample.helpers.validator.AddressValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAddressValidator {
    @Test
    public void testValidateAddressWithValidInput(){
        //streetname, city, postalcode, country
        String streetName = "Example Street";
        String city = "Example City";
        String postalCode = "01234";
        String country = "Exapmle Country";

        boolean result = AddressValidator.validateAddress(streetName, city,postalCode,country);

        assertTrue(result);
    }

    @Test
    public void testValidateAddressWithInvalidPostalCode(){
        String streetName = "Example Street";
        String city = "Example City";
        String postalCode = "0a1234";
        String country = "Exapmle Country";

        boolean result = AddressValidator.validateAddress(streetName, city,postalCode,country);
        assertFalse(result);
    }

}
