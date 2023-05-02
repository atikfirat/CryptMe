import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

/**
 * Client class
 **/
public class Client extends Thread {

    public static BufferedWriter logFile;                                                       // Log file
    private String userName;                                                                    // Client user name
    private PrintWriter clientOutput;                                                           // Client output stream
    private GUI clientGUI;                                                                      // Client GUI

    private static SecretKey secretKey;                                                         // Client secret key
    private static byte[] IV;                                                                   // Client initialization vector
    private static String Method;                                                               // Crypt method
    private static String Mode;                                                                 // Crypt mode

    public static void setSecretKey(SecretKey secretKey) { Client.secretKey = secretKey; }
    public static void setIV(byte[] IV) { Client.IV = IV; }                                     // Setters
    public static void setMode(String mode) { Client.Mode = mode; }
    public static void setMethod(String method) { Client.Method = method; }

    public Client(String serverAddress) throws Exception {
        Socket clientSocket = new Socket(serverAddress, Server.getPORT());
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        clientGUI = new GUI();                                                                  // Client GUI created
        ActionListeners();                                                                      // Action listeners created
        new Thread(new ClientConnection(clientInput, clientGUI.getMessageArea())).start();      // Thread started
    }

    /**
     * Client main
     **/
    public static void main(String[] args) throws Exception {
        new Client("localhost");
    }

    /**
     * 1. Connect Button --> Gets a name by opening the input dialog.
     * 2. Send Button --> Transfers the encrypted text to the server for distribution.
     * 3. Disconnect Button --> It is used to remove the customer from the server.
     * 4. Encrypt Button --> Enables encryption of text entered as input. Prints on the encrypted text area.
     * 5. AES Button , 6. DES Button , 7. CBC Button , 8. OFB Button
     **/
    private void ActionListeners() {

        clientGUI.getConnectButton().addActionListener(e -> {
            userName = JOptionPane.showInputDialog(null, "Enter user name:", "Input",
                    JOptionPane.PLAIN_MESSAGE);
            if (userName != null && userName.trim().length() > 0) {
                clientGUI.getConnectedLabel().setText("Connected : " + userName);
                clientGUI.getTextArea().setEditable(true);
                clientOutput.println(userName);
                clientGUI.getConnectButton().setEnabled(false);                          // 1. Connect button
                clientGUI.getDisconnectButton().setEnabled(true);
                clientGUI.getEncryptButton().setEnabled(true);
                clientGUI.getAESRadioButton().setEnabled(true);
                clientGUI.getDESRadioButton().setEnabled(true);
                clientGUI.getCBCRadioButton().setEnabled(true);
                clientGUI.getOFBRadioButton().setEnabled(true);
            }
        });

        clientGUI.getSendButton().addActionListener(e -> {
            try {
                clientOutput.println("ENC--" + userName + "--" + clientGUI.getEncryptedTextArea().getText());
                logFile = new BufferedWriter(new FileWriter("log.txt", true));
                logFile.write(userName + " > " + clientGUI.getEncryptedTextArea().getText() + "\n");
                logFile.close();
                clientGUI.getEncryptedTextArea().setText("");
                clientGUI.getTextArea().setEditable(true);                                   // 2. Send button
                clientGUI.getEncryptButton().setEnabled(true);
                clientGUI.getSendButton().setEnabled(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        clientGUI.getDisconnectButton().addActionListener(e -> {
            clientOutput.println("Disconnect");                                                 // 3. Disconnect button
            System.exit(0);
        });

        clientGUI.getEncryptButton().addActionListener(e -> {
            if (!clientGUI.getAESRadioButton().isSelected() && !clientGUI.getDESRadioButton().isSelected()) {
                JOptionPane.showMessageDialog(this.clientGUI.getClientWindow(), "SELECT METHOD", "Error !", JOptionPane.ERROR_MESSAGE);
            } else if (!clientGUI.getCBCRadioButton().isSelected() && !clientGUI.getOFBRadioButton().isSelected()) {
                JOptionPane.showMessageDialog(this.clientGUI.getClientWindow(), "SELECT MODE", "Error !", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    plainTextEncryption();
                    clientGUI.getSendButton().setEnabled(true);
                    clientGUI.getEncryptButton().setEnabled(false);                          // 4. Encrypted button
                    clientGUI.getTextArea().setEditable(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        clientGUI.getAESRadioButton().addActionListener(e -> clientOutput.println("%&"));    // 5. AES button
        clientGUI.getDESRadioButton().addActionListener(e -> clientOutput.println("&%"));    // 6. DES button
        clientGUI.getCBCRadioButton().addActionListener(e -> clientOutput.println("#*"));    // 7. CBC button
        clientGUI.getOFBRadioButton().addActionListener(e -> clientOutput.println("*#"));    // 8. OFB button
    }

    /**
     * It takes the plain text from the textArea field.
     * It encrypts the text according to the selected mode and methods.
     * Prints the encrypted text area in the form of Base64.
     **/
    private void plainTextEncryption() throws Exception {

        String plainText = clientGUI.getTextArea().getText();
        clientGUI.getTextArea().setText("");

        byte[] encryptedText;
        if (Method.equals("AES") && Mode.equals("CBC")) {
            encryptedText = CryptFunctions.encrypt(plainText.getBytes(), secretKey, IV, "AES", "CBC");
        } else if (Method.equals("AES") && Mode.equals("OFB")) {
            encryptedText = CryptFunctions.encrypt(plainText.getBytes(), secretKey, IV, "AES", "OFB");
        } else if (Method.equals("DES") && Mode.equals("CBC")) {
            encryptedText = CryptFunctions.encrypt(plainText.getBytes(), secretKey, IV, "DES", "CBC");
        } else if (Method.equals("DES") && Mode.equals("OFB")) {
            encryptedText = CryptFunctions.encrypt(plainText.getBytes(), secretKey, IV, "DES", "OFB");
        } else {
            encryptedText = new byte[1];
        }
        clientGUI.getEncryptedTextArea().setText(Base64.getEncoder().encodeToString(encryptedText));
    }

    /**
     * Receives encrypted text from another client.
     * Decodes cipher text according to selected mode and methods.
     *
     * @param cipherText --> Encrypted text from other client
     **/
    public static String cipherTextDecryption(byte[] cipherText) throws Exception {

        String decryptedText;
        if (Method.equals("AES") && Mode.equals("CBC")) {
            decryptedText = CryptFunctions.decrypt(cipherText, secretKey, IV, "AES", "CBC");
        } else if (Method.equals("AES") && Mode.equals("OFB")) {
            decryptedText = CryptFunctions.decrypt(cipherText, secretKey, IV, "AES", "OFB");
        } else if (Method.equals("DES") && Mode.equals("CBC")) {
            decryptedText = CryptFunctions.decrypt(cipherText, secretKey, IV, "DES", "CBC");
        } else if (Method.equals("DES") && Mode.equals("OFB")) {
            decryptedText = CryptFunctions.decrypt(cipherText, secretKey, IV, "DES", "OFB");
        } else {
            decryptedText = "";
        }
        return decryptedText;
    }

}