package ie.mid.identityengine.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
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
}
