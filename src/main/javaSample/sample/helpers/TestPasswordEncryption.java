package javaSample.sample.helpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPasswordEncryption {

    @Test
    public void testEncryptPassword() {
        String inputPassword = "myPassword123";

        // expected hash pagal internetini hash calculator (sha256 algoritmas)
        String expectedPassword = "71d4ec024886c1c8e4707fb02b46fd568df44e77dd5055cadc3451747f0f2716";
        String encryptedPassword = PasswordEncryptHelper.encryptPassword(inputPassword);

        assertEquals(expectedPassword, encryptedPassword);
    }
}
