import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.BufferedReader;
import java.util.Base64;

/**
 * ClientConnection class providing client connection with Thread
 **/
public class ClientConnection implements Runnable {

    private BufferedReader clientInput;                                                         // Client input
    private JTextArea messageArea;                                                              // Client window message area

    public ClientConnection(BufferedReader clientInput, JTextArea messageArea) {
        this.clientInput = clientInput;
        this.messageArea = messageArea;
    }

    public void run() {

        String inputString;                                                                     // Input message
        while (true) {
            try {
                inputString = clientInput.readLine();                                           // Message assigned
                if (inputString.substring(0, 2).equals("%&") || inputString.substring(0, 2).equals("&%")) {
                    keyVectorInitializer(inputString);                                          // AES - DES methods
                } else if (inputString.equals("#*") || inputString.equals("*#")) {
                    modeInitializer(inputString);                                               // CBC - OFB modes
                } else {
                    decryptStarter(inputString);                                                // Decryption started
                }
            } catch (Exception ex) {
                if ("Connection reset".equals(ex.getMessage())) {                               // Connection interrupt
                    System.out.println("Server Disconnected!");
                    break;
                }
            }
        }
    }

    /**
     * The resulting cipher text was printed on client screens.
     * Using the cipher text, plain text was obtained.
     * Plain text printed to client screens.
     *
     * @param inputString --> (ENC + username + cipher text) string
     **/
    private void decryptStarter(String inputString) throws Exception {

        String[] words = inputString.split("--");
        messageArea.append(words[2] + "\n");
        String decryptedText = Client.cipherTextDecryption(Base64.getDecoder().decode(words[2]));
        messageArea.append(words[1] + " > " + decryptedText + "\n");
    }

    /**
     * It takes the key and vector from the server side but they are Base64 type.
     * It cast them back into their own kind.
     * Synchronizes the key and vector of all clients connected to the server to the casted key and vector.
     *
     * @param message --> (ENC_MODE + key + initialization vector) string
     **/
    private void keyVectorInitializer(String message) {

        String[] words = message.split("--");
        byte[] decodedString = Base64.getDecoder().decode(words[1]);
        Client.setSecretKey(new SecretKeySpec(decodedString, 0, decodedString.length, words[0]));
        Client.setIV(Base64.getDecoder().decode(words[2]));

        if (words[0].equals("%&")) {
            Client.setMethod("AES");
        } else {
            Client.setMethod("DES");
        }
    }

    /**
     * Determines the mode of the clients according to the mode received by the server.
     *
     * @param message --> CBC or OFB
     **/
    private void modeInitializer(String message) {

        if (message.equals("#*")) {
            Client.setMode("CBC");
        } else {
            Client.setMode("OFB");
        }
    }

}