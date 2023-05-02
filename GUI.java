import javax.swing.*;
import java.awt.*;

/**
 * GUI class
 **/
public class GUI {

    private JFrame clientWindow = new JFrame();
    private JButton connectButton;
    private JButton disconnectButton;
    private JButton encryptButton;
    private JButton sendButton;
    private JTextArea messageArea;
    private JTextArea textArea;
    private JTextArea encryptedTextArea;
    private JRadioButton DESRadioButton;
    private JRadioButton AESRadioButton;
    private JRadioButton OFBRadioButton;
    private JRadioButton CBCRadioButton;
    private JLabel connectedLabel = new JLabel();

    public JFrame getClientWindow() { return clientWindow; }
    public JButton getConnectButton() { return connectButton; }
    public JButton getDisconnectButton() { return disconnectButton; }
    public JTextArea getMessageArea() { return messageArea; }
    public JTextArea getTextArea() { return textArea; }
    public JTextArea getEncryptedTextArea() { return encryptedTextArea; }               // Getters
    public JRadioButton getDESRadioButton() { return DESRadioButton; }
    public JRadioButton getAESRadioButton() { return AESRadioButton; }
    public JRadioButton getOFBRadioButton() { return OFBRadioButton; }
    public JRadioButton getCBCRadioButton() { return CBCRadioButton; }
    public JButton getEncryptButton() { return encryptButton; }
    public JButton getSendButton() { return sendButton; }
    public JLabel getConnectedLabel() { return connectedLabel; }

    public GUI() { clientWindowBuilder(); }                                             // Constructor

    private void clientWindowBuilder() {
        final JPanel panel1 = new JPanel();
        clientWindow.setContentPane(panel1);
        clientWindow.setResizable(false);
        panel1.setLayout(new GridBagLayout());
        panel1.setBorder(BorderFactory.createTitledBorder("Server"));
        connectButton = new JButton();
        connectButton.setText("\u26A1 Connect");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(connectButton, gbc);
        disconnectButton = new JButton();
        disconnectButton.setText("\u274C Disconnect");
        disconnectButton.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(disconnectButton, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        panel2.setBorder(BorderFactory.createTitledBorder("Method"));
        DESRadioButton = new JRadioButton();
        DESRadioButton.setText("DES");
        panel2.add(DESRadioButton, BorderLayout.EAST);
        DESRadioButton.setEnabled(false);
        AESRadioButton = new JRadioButton();
        AESRadioButton.setText("AES");
        AESRadioButton.setEnabled(false);
        panel2.add(AESRadioButton, BorderLayout.WEST);
        ButtonGroup ADButtonGroup = new ButtonGroup();
        ADButtonGroup.add(AESRadioButton);
        ADButtonGroup.add(DESRadioButton);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel3, gbc);
        panel3.setBorder(BorderFactory.createTitledBorder("Message Area"));
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setColumns(25);
        messageArea.setLineWrap(true);
        messageArea.setRows(30);
        JScrollPane scrollBar = new JScrollPane();
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.getViewport().add(messageArea);
        panel3.add(scrollBar);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel1.add(panel4, gbc);
        panel4.setBorder(BorderFactory.createTitledBorder("Text"));
        textArea = new JTextArea();
        textArea.setColumns(10);
        textArea.setLineWrap(true);
        textArea.setRows(6);
        textArea.setEditable(false);
        JScrollPane scrollBar2 = new JScrollPane();
        scrollBar2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar2.getViewport().add(textArea);
        panel4.add(scrollBar2);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel5, gbc);
        panel5.setBorder(BorderFactory.createTitledBorder("Crypted Text"));
        encryptedTextArea = new JTextArea();
        encryptedTextArea.setLineWrap(true);
        encryptedTextArea.setEditable(false);
        JScrollPane scrollBar3 = new JScrollPane();
        scrollBar3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar3.getViewport().add(encryptedTextArea);
        panel5.add(scrollBar3);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel6, gbc);
        panel6.setBorder(BorderFactory.createTitledBorder("Mode"));
        OFBRadioButton = new JRadioButton();
        OFBRadioButton.setText("OFB");
        OFBRadioButton.setEnabled(false);
        panel6.add(OFBRadioButton, BorderLayout.EAST);
        CBCRadioButton = new JRadioButton();
        CBCRadioButton.setText("CBC");
        CBCRadioButton.setEnabled(false);
        panel6.add(CBCRadioButton, BorderLayout.WEST);
        ButtonGroup COButtonGroup = new ButtonGroup();
        COButtonGroup.add(CBCRadioButton);
        COButtonGroup.add(OFBRadioButton);
        connectedLabel.setText("Not connected");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(connectedLabel, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel7, gbc);
        sendButton = new JButton();
        sendButton.setText("\u2708 Send");
        sendButton.setEnabled(false);
        panel7.add(sendButton, BorderLayout.EAST);
        encryptButton = new JButton();
        encryptButton.setText("\uD83D\uDD12 Encrypt");
        encryptButton.setEnabled(false);
        panel7.add(encryptButton, BorderLayout.CENTER);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer2, gbc);

        clientWindow.setTitle("Crypto Messenger");
        clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientWindow.setSize(999, 999);
        clientWindow.setVisible(true);
        clientWindow.pack();
    }

}