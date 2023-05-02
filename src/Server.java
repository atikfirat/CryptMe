import java.io.FileWriter;
import java.util.*;
import java.net.*;

/**
 * Server Class
 **/
public class Server {

    private static ArrayList<String> userList = new ArrayList<>();                          // User name list
    private static ArrayList<ServerConnection> clientList = new ArrayList<>();              // Client list
    private static final int PORT = 9556;                                                   // Port number
    private static String AES_key, DES_key, AES_vector, DES_vector;                         // Keys and vectors

    public static ArrayList<String> getUserList() { return userList; }
    public static ArrayList<ServerConnection> getClientList() { return clientList; }
    public static int getPORT() { return PORT; }
    public static String getAES_key() { return AES_key; }                                   // Getters
    public static String getDES_key() { return DES_key; }
    public static String getAES_vector() { return AES_vector; }
    public static String getDES_vector() { return DES_vector; }

    /**
     * Server main
     **/
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(PORT, 9);                      // Server socket
        FileWriter logFile = new FileWriter("log.txt");
        System.out.println("Server Started.\n");
        keyVectorWriter(logFile);
        logFile.close();

        while (true) {
            try {
                Socket client = serverSocket.accept();                                          // Client accepted
                new Thread(new ServerConnection(client)).start();                               // Thread started
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * This function creates keys and initialization vectors.
     * Prints keys and vectors to server screen and writes to log.txt.
     *
     * @param logFile --> File object to write to
     **/
    private static void keyVectorWriter(FileWriter logFile) throws Exception {

        AES_key = CryptFunctions.secretKeyGenerator(128, "AES");
        DES_key = CryptFunctions.secretKeyGenerator(56, "DES");
        AES_vector = CryptFunctions.initializationVectorGenerator(16);
        DES_vector = CryptFunctions.initializationVectorGenerator(8);

        System.out.println("AES KEY(128 bit): " + AES_key);
        System.out.println("DES KEY(56 bit): " + DES_key);
        System.out.println("AES VECTOR(16 byte): " + AES_vector);
        System.out.println("DES VECTOR(8 byte): " + DES_vector);
        System.out.println("---------------------------------------");

        logFile.write("AES KEY(128 bit): " + AES_key + "\n");
        logFile.write("DES KEY(56 bit): " + DES_key + "\n");
        logFile.write("AES VECTOR(16 byte): " + AES_vector + "\n");
        logFile.write("DES VECTOR(8 byte): " + DES_vector + "\n");
        logFile.write("---------------------------------------\n");
    }

}