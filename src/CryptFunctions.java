import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * CryptFunctions class includes functions related to encryption and decryption
 **/
public class CryptFunctions {

    /**
     * This function was written to generate secret keys.
     * The generated key is converted to Base64 form to be sent to clients via the server.
     *
     * @param keySize --> Secret key size
     * @param method  --> Encryption method
     **/
    public static String secretKeyGenerator(int keySize, String method) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(method);
        keyGenerator.init(keySize);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * This function was written to create initialization vectors.
     * The resulting vector was converted to Base64 form to be sent to clients via the server.
     **/
    public static String initializationVectorGenerator(int vectorSize) {
        byte[] IV = new byte[vectorSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        return Base64.getEncoder().encodeToString(IV);
    }

    /**
     * The plain text is encrypted according to the parameters.
     *
     * @param plaintext --> Decrypted text
     * @param key       --> Secret key received from server
     * @param IV        --> Initialization vector received from server
     * @param method    --> Encryption method
     * @param mode      --> Encryption mode
     */
    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV, String method, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance(method + "/" + mode + "/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), method);
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(plaintext);
    }

    /**
     * The cipher text is decrypted according to the parameters.
     *
     * @param cipherText --> Encrypted text
     * @param key        --> Secret key received from server
     * @param IV         --> Initialization vector received from server
     * @param method     --> Encryption method
     * @param mode       --> Encryption mode
     */
    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV, String method, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance(method + "/" + mode + "/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), method);
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedText = cipher.doFinal(cipherText);
        return new String(decryptedText);
    }

}