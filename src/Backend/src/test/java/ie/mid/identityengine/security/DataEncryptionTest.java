package ie.mid.identityengine.security;

import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.*;

public class DataEncryptionTest {

    private String publicKey;
    private String privateKey;
    private String encryptedText;
    private final String decryptedText = "HELLO";

    @Before
    public void setUp() throws Exception {
        KeyPair keyPair = KeyUtil.generateKeyPair();
        publicKey = KeyUtil.byteToBase64(keyPair.getPublic().getEncoded()).replace("\n", "");
        privateKey = KeyUtil.byteToBase64(keyPair.getPrivate().getEncoded()).replace("\n", "");
        encryptedText = DataEncryption.encryptText(decryptedText, privateKey);
    }

    @Test
    public void decryptText() {
        assertEquals(decryptedText, DataEncryption.decryptText(encryptedText, publicKey));
    }

    @Test
    public void encryptText() {
        assertEquals(encryptedText, DataEncryption.encryptText(decryptedText, privateKey));
    }

    @Test
    public void isInvalidKey() {
        assertTrue(DataEncryption.isInvalidPublicKey(decryptedText));
        assertFalse(DataEncryption.isInvalidPublicKey(publicKey));
    }
}