package ie.mid.util;

import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
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
}
