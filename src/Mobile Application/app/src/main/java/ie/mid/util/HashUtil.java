package ie.mid.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import android.util.Base64;
import java.util.Random;

/**
 * Created by Cillian on 28/01/2018.
 */

public class HashUtil {

    private static Random random = new SecureRandom();

    public static byte[] hashPassword(String password, byte[] salt) {
        byte[] saltedPassword = concatenateArrays(password.getBytes(), salt);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(saltedPassword);
            for (int i = 0; i < 200 - 1; i++) {
                hash = messageDigest.digest(hash);
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing: " + e.getMessage(), e);
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

    public static String byteToBase64(byte[] array){
        return Base64.encodeToString(array,Base64.DEFAULT);
    }

    public static byte[] Base64ToByte(String base64){
        return Base64.decode(base64, Base64.DEFAULT);
    }

    public static String byteToHex(byte[] array) {
        StringBuilder builder = new StringBuilder();
        for (byte b : array) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte[] hexToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static byte[] concatenateArrays(byte[] a, byte[] b) {
        byte[] concatenatedArray = new byte[a.length + b.length];
        System.arraycopy(a, 0, concatenatedArray, 0, a.length);
        System.arraycopy(b, 0, concatenatedArray, a.length, b.length);
        return concatenatedArray;
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
