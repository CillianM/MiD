package ie.mid.identityengine.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DataEncryption {

    private static Logger logger = LogManager.getLogger(DataEncryption.class);

    public static String decryptText(String encodedBase64String, String keyString) {
        try {
            logger.debug("Attempting to decrypt text " + encodedBase64String + "\nwith keystring " + keyString);
            encodedBase64String = encodedBase64String.replace("\n","");
            keyString = keyString.replace("\n","");
            byte[] decodedBase64 = Base64.decodeBase64(encodedBase64String);
            byte [] keyArray = Base64.decodeBase64(keyString);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyArray);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(decodedBase64),"UTF-8");
        } catch (Exception e){
            logger.error("Error decrypting text " + encodedBase64String,e);
            return null;
        }
    }

    public static String encryptText(String text, String keyString) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(keyString)));
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64String(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isInvalidKey(String key) {
        try {
            String testKey = key.replace("\n", "");
            byte[] keyArray = Base64.decodeBase64(testKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyArray);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
