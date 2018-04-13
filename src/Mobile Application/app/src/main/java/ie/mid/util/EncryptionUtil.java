package ie.mid.util;

import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
    public static String encryptText(String text, String keyString) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(HashUtil.Base64ToByte(keyString)));
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeToString(cipher.doFinal(text.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e){
            return null;
        }
    }

    public static String encryptTextPublic(String text, String publicKeyString) {
        try {
            text = text.replace("\n","");
            byte[] keyArray = HashUtil.Base64ToByte(publicKeyString.replace("\n", ""));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyArray);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeToString(cipher.doFinal(text.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e){
            return null;
        }
    }

    public static String decryptText(String encodedBase64String, String keyString) {
        try {
            byte[] decodedBase64 = Base64.decode(encodedBase64String, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("RSA");
            Key key = new SecretKeySpec(HashUtil.Base64ToByte(keyString), 0, HashUtil.Base64ToByte(keyString).length, "RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(decodedBase64),"UTF-8");
        } catch (Exception e){
            return null;
        }
    }

    public static String generateAesKey(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // for example
            SecretKey secretKey = keyGen.generateKey();
            return HashUtil.byteToBase64(secretKey.getEncoded());
        }
        catch (Exception e){
            return null;
        }
    }

    public static String aesEncryption(String text, String aesKey) {
        try {
            byte[] key = HashUtil.Base64ToByte(aesKey);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return HashUtil.byteToBase64(cipher.doFinal(text.getBytes()));
        } catch (Exception e){
            return null;
        }
    }

    public static String aesDecryption(String cipherText, String aesKey) throws Exception
    {
        byte [] cipherArray = HashUtil.Base64ToByte(cipherText);
        byte[] key = HashUtil.Base64ToByte(aesKey);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return HashUtil.byteToBase64(cipher.doFinal(cipherArray));
    }
}
