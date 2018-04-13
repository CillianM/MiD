package admin.security;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DataEncryption {

    private static Logger logger = Logger.getLogger(DataEncryption.class);

    public static String encryptText(String text, String keyString) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64ToByte(keyString)));
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64String(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e){
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
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyArray));
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(decodedBase64),"UTF-8");
        } catch (Exception e){
            logger.error("Error decrypting text " + encodedBase64String,e);
            return null;
        }
    }

    public static String aesDecryption(String cipherText, String aesKey) throws Exception
    {
        byte [] cipherArray = Base64ToByte(cipherText);
        byte[] key = Base64ToByte(aesKey);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(cipherArray),"UTF-8");
    }

    private static byte[] Base64ToByte(String base64){
        return Base64.decodeBase64(base64);
    }
}
