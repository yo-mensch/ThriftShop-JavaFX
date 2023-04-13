package tests;

import javaSample.sample.helpers.validator.ProductValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestProductValidator {

    //valid produktas
    @Test
    public void testValidateProductWithValidInput() {
        String productName = "Test Product";
        String productDescription = "This is a test product";
        String productPrice = "10.00";

        boolean result = ProductValidator.isProductValid(productName, productDescription, productPrice);

        assertTrue(result);
    }

    //produktas su tusciu pavadinimu
    @Test
    public void testValidateProductWithEmptyInput() {
        String productName = "";
        String productDescription = "This is a test product";
        String productPrice = "10.00";

        boolean result = ProductValidator.validateFields(productName, productDescription, productPrice);

        assertFalse(result);
    }

    //produktas su nuline kaina
    @Test
    public void testValidateProductWithInvalidPrice() {
        String productName = "Test Product";
        String productDescription = "This is a test product";
        String productPrice = "0.00";

        boolean result = ProductValidator.validatePrice(productPrice);

        assertFalse(result);
    }
}
