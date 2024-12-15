package Sidenow.Smart_SeniorCenter.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Base64Encoder {
    public static void main(String[] args) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encodedSecret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Encoded secret: " + encodedSecret);
    }
}
