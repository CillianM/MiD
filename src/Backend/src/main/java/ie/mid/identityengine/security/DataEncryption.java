package ie.mid.identityengine.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DataEncryption {

    private static Logger logger = LoggerFactory.getLogger(DataEncryption.class);

    public static String decryptText(String encodedBase64String, String keyString) {
        try {
            logger.debug("Attempting to decrypt text " + encodedBase64String + "\nwith keystring " + keyString);
            encodedBase64String = encodedBase64String.replace("\n","");
            keyString = keyString.replace("\n","");
            byte[] decodedBase64 = DatatypeConverter.parseBase64Binary(encodedBase64String);
            byte[] keyArray = DatatypeConverter.parseBase64Binary(keyString);
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
            keyString = keyString.replace("\n", "");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(keyString)));
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return byteToBase64(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;
        }
    }

    public static String encryptTextPublic(String text, String publicKeyString) {
        try {
            text = text.replace("\n", "");
            byte[] keyArray = Base64ToByte(publicKeyString.replace("\n", ""));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyArray);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return byteToBase64(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;
        }
    }

    public static String aesEncryption(String text, String aesKey) {
        try {
            byte[] key = Base64ToByte(aesKey);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return byteToBase64(cipher.doFinal(text.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    public static String aesDecryption(String cipherText, String aesKey) throws Exception {
        byte[] cipherArray = Base64ToByte(cipherText);
        byte[] key = Base64ToByte(aesKey);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return byteToBase64(cipher.doFinal(cipherArray));
    }

    public static boolean isInvalidPublicKey(String key) {
        try {
            String testKey = key.replace("\n", "");
            byte[] keyArray = DatatypeConverter.parseBase64Binary(testKey);
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

    public static String byteToBase64(byte[] array) {
        return Base64.encodeBase64String(array);
    }

    private static byte[] Base64ToByte(String base64) {
        return Base64.decodeBase64(base64);
    }
}
