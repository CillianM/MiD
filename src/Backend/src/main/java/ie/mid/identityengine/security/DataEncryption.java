package ie.mid.identityengine.security;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DataEncryption {

    private static Logger logger = Logger.getLogger(DataEncryption.class);

    public static String encryptText(String text, String keyString) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64String(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e){
            logger.error("Error encrypting text " + text,e);
            return null;
        }
    }

    public static String decryptText(String encodedBase64String, String keyString) {
        try {
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
