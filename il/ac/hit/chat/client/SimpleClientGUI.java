package il.ac.hit.chat.client;

import il.ac.hit.chat.ConnectionProxy;
import il.ac.hit.chat.StringConsumer;
import il.ac.hit.chat.StringProducer;
import il.ac.hit.chat.server.MessageBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.List;
import java.awt.Color;

public class SimpleClientGUI implements StringConsumer, StringProducer {
    private JFrame frame;
    private JPanel panelTop, panelBottom, panelCenter;
    private JTextArea textArea;
    private JTextField nickNameField, server, port, writingArea;
    private JButton connect, disconnect, send;
    private JScrollPane scroll;
    private JLabel toLabel, message;
    private JComboBox<String> participants;
    private boolean isConnected;
    private String nickName;
    private ConnectionProxy con;
    private Socket socket;
    Vector<StringConsumer> consumers;

    public SimpleClientGUI() {
        isConnected = false;
        nickNameField = new JTextField(10);
        server = new JTextField(10);
        port = new JTextField(10);
        writingArea = new JTextField(50);
        textArea = new JTextArea(25, 75);
        connect = new JButton("Connect");
        disconnect = new JButton("Disconnect");
        send = new JButton("Send");
        frame = new JFrame("Chat");
        scroll = new JScrollPane(textArea);
        participants = new JComboBox<>();
        toLabel = new JLabel();
        message = new JLabel();

        panelTop = new JPanel();
        panelBottom = new JPanel();
        panelCenter = new JPanel();
        panelCenter.setBackground(new Color(234, 237, 244));
        panelTop.setBackground(new Color(101, 138, 206));
        panelBottom.setBackground(new Color(101, 138, 206));
        textArea.setEnabled(false);

        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        panelTop.add(nickNameField);
        panelTop.add(server);
        panelTop.add(port);
        panelTop.add(connect);
        panelTop.add(disconnect);

        panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBottom.add(message);
        panelBottom.add(writingArea);
        panelBottom.add(toLabel);
        panelBottom.add(participants);
        //panelBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelBottom.add(send);

        panelCenter.add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelBottom, BorderLayout.SOUTH);
        frame.add(panelCenter, BorderLayout.CENTER);

        frame.setSize(900, 550);
        frame.setVisible(true);
        frame.requestFocusInWindow(); // Remove focus from nickname field
        participants.addItem("Everyone");
        toLabel.setText(" To: ");
        toLabel.setForeground(Color.white);
        message.setText("   Message: ");
        message.setForeground(Color.white);

        // Apply custom styles
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        writingArea.setPreferredSize(new Dimension(300, 30));
        participants.setPreferredSize(new Dimension(100, 30));
    }
    public void setConnected(boolean isConnected)
    {
        this.isConnected = isConnected;
        this.connect.setEnabled(!isConnected);
        this.send.setEnabled(isConnected);
        this.disconnect.setEnabled(isConnected);      
    }

    public void setDisconnected(StringConsumer sc)
    {
        if(this.isConnected)
        {
            this.isConnected=false;
            this.disconnect.setEnabled(false);
            this.connect.setEnabled(true);
            this.send.setEnabled(false);     
        }
    }

    @Override
    public void addConsumer(StringConsumer sc)
    {
        consumers.addElement(sc);
    }

    @Override
    public void removeConsumer(StringConsumer sc)
    {
        consumers.remove(sc);
    }

    @Override
    public void consume(String str)
    {
        if (!(str.contains("!@#end#@!")))
        {
            textArea.append(str+"\n");
        }

        else
        {
         String[] names = str.split("!@#end#@!");

        this.participants.removeAllItems();
        this.participants.addItem("Everyone");   
        for (String name : names) {
            if (!(name.equals(this.nickName)))
            {
                this.participants.addItem(name);
            }
        }
        }
        
    }

    public void changeNicknameText() {
        this.nickNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nickNameField.getText().equals("Nickname")) {
                    nickNameField.setText(""); // Clear the default text when focused
                    nickNameField.setForeground(Color.BLACK); // Change the text color
                }
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                if (nickNameField.getText().isEmpty()) {
                    nickNameField.setText("Nickname"); // Restore the default text if empty
                    nickNameField.setForeground(Color.GRAY); // Change the text color back to gray
                }
            }
        });
    }

    public void changeServerText() {
        this.server.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (server.getText().equals("127.0.0.1")) {
                    server.setText(""); // Clear the default text when focused
                    server.setForeground(Color.BLACK); // Change the text color
                }
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                if (server.getText().isEmpty()) {
                    server.setText("127.0.0.1"); // Restore the default text if empty
                    server.setForeground(Color.GRAY); // Change the text color back to gray
                }
            }
        });
    }

    
    public void changePortText() {
        this.port.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (port.getText().equals("1300")) {
                    port.setText(""); // Clear the default text when focused
                    port.setForeground(Color.BLACK); // Change the text color
                }
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                if (port.getText().isEmpty()) {
                    port.setText("1300"); // Restore the default text if empty
                    port.setForeground(Color.GRAY); // Change the text color back to gray
                }
            }
        });
    }



   

    public static void main(String[] args)
    {
        SimpleClientGUI gui = new SimpleClientGUI();
        gui.nickNameField.setText("Nickname");
        gui.server.setText("127.0.0.1");
        gui.port.setText("1300");

        gui.changeNicknameText();
        gui.changeServerText();
        gui.changePortText();

        gui.disconnect.setEnabled(false);
        gui.send.setEnabled(false);
        gui.connect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {  
                    if (!gui.server.getText().trim().equals("127.0.0.1") || Integer.parseInt(gui.port.getText()) != 1300)
                    {
                        throw new IOException("Incorrect server or port");   
                    }          
                    gui.socket = new Socket(gui.server.getText(),Integer.parseInt(gui.port.getText()));
                    gui.nickName = new String(gui.nickNameField.getText());
                    gui.con = new ConnectionProxy(gui.socket, gui.nickName);
                    gui.con.consume(gui.nickName + " connected");
                    gui.con.addConsumer(gui);
                    gui.con.start();

                    gui.setConnected(true);
                }
                catch (IOException er)
                {
                    er.printStackTrace();
                    gui.consume(er.getMessage());
                    gui.setConnected(false);
                    return;
                }
            }
            
        }
        );

        gui.disconnect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                    gui.setDisconnected(gui);
                    gui.con.removeConsumer(gui);
                    gui.con.consume(gui.nickName + " disconnected");

            }
        }
        );

        gui.send.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.con.consume(gui.nickName + ": " + gui.writingArea.getText() + "!@#end#@!" + gui.participants.getSelectedItem());
                gui.writingArea.setText("");
            }
        });
    }
}