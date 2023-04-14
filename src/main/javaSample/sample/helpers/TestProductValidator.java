package javaSample.sample.helpers;
import javaSample.sample.helpers.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnit4.class)
public class TestProductValidator {

    public ProductValidator productValidator = new ProductValidator();

    //valid produktas
    @Test
    public void testValidateProductWithValidInput() {
        String productName = "Test Product";
        String productDescription = "This is a test product";
        String productPrice = "10.00";

        boolean result = productValidator.isProductValid(productName, productDescription, productPrice);

        assertTrue(result);
    }

    //produktas su tusciu pavadinimu
    @Test
    public void testValidateProductWithEmptyInput() {
        String productName = "";
        String productDescription = "This is a test product";
        String productPrice = "10.00";

        boolean result = productValidator.validateFields(productName, productDescription, productPrice);

        assertFalse(result);
    }

    //produktas su nuline kaina
    @Test
    public void testValidateProductWithInvalidPrice() {
        String productName = "Test Product";
        String productDescription = "This is a test product";
        String productPrice = "0.00";

        boolean result = productValidator.validatePrice(productPrice);

        assertFalse(result);
    }
}
