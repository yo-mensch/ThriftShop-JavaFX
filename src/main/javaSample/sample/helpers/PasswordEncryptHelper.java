package javaSample.sample.helpers;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class PasswordEncryptHelper {

    public static String encryptPassword(String inputPassword){
        String sha256hex = Hashing.sha256()
                .hashString(inputPassword, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
