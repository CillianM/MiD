package ie.mid.identityengine.security;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeyUtil {

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String hashString(String s) {
        byte[] stringArray = s.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(stringArray);
            for (int i = 0; i < 200 - 1; i++) {
                hash = messageDigest.digest(hash);
            }
            return byteToBase64(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing: " + e.getMessage(), e);
        }
    }

    public static String generateAesKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // for example
            SecretKey secretKey = keyGen.generateKey();
            return byteToBase64(secretKey.getEncoded());
        } catch (Exception e) {
            return null;
        }
    }

    public static String byteToBase64(byte[] array) {
        return Base64.encodeBase64String(array);
    }

    private static byte[] Base64ToByte(String base64) {
        return Base64.decodeBase64(base64);
    }
}
