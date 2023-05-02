import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ServerConnection class providing server connection with Thread
 **/
public class ServerConnection implements Runnable {

    private String userName;                                                    // Client user name
    private BufferedReader clientInput;                                         // Client input stream
    private PrintWriter clientOutput;                                           // Client output stream

    public ServerConnection(Socket clientSocket) throws Exception {
        clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        userName = clientInput.readLine();                                      // User name assigned
        Server.getUserList().add(userName);                                     // User added to userList
        Server.getClientList().add(this);                                       // Client added to clientList
    }

    public void run() {
        String message;                                                         // Input message
        try {
            label:
            while (true) {
                message = clientInput.readLine();                               // Message readed
                switch (message) {
                    case "Disconnect":                                          // Client disconnected
                        Server.getUserList().remove(userName);
                        Server.getClientList().remove(this);
                        break label;
                    case "%&":                                                  // AES method
                        messageSender("%&--" + Server.getAES_key() + "--" + Server.getAES_vector());
                        break;
                    case "&%":                                                  // DES method
                        messageSender("&%--" + Server.getDES_key() + "--" + Server.getDES_vector());
                        break;
                    default:
                        if (message.length() > 2) {
                            String[] word = message.split("--");
                            System.out.println(word[1] + " > " + word[2]);
                        }
                        messageSender(message);
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Server distributes the message from the client, to client's output streams.
     *
     * @param message --> Input message
     **/
    public void messageSender(String message) {

        for (ServerConnection handler : Server.getClientList()) {
            handler.clientOutput.println(message);
        }
    }

}