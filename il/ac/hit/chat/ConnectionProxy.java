package il.ac.hit.chat;

import java.net.Socket;
import java.io.*;

public class ConnectionProxy extends Thread implements StringConsumer,StringProducer {

    private StringConsumer consumer = null;
    private Socket socket = null;
    private InputStream is = null;
    private OutputStream os = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String name;


     public ConnectionProxy(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        dis = new DataInputStream(is);
        dos = new DataOutputStream(os);
        setNameHost(dis.readUTF());
    }

    public ConnectionProxy(Socket socket, String name) throws IOException {
        this.name=name;
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        dis = new DataInputStream(is);
        dos = new DataOutputStream(os);
        dos.writeUTF(getNameHost());
    }

    public void setNameHost(String name)
    {
        this.name = name;
    }

    public String getNameHost()
    {
        return this.name;
    }

    @Override
    public void consume(String str)
    {
        try {
            dos.writeUTF(str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addConsumer(StringConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void removeConsumer(StringConsumer consumer) {
        this.consumer = null;
    }

    public void run() {
        while(true) {
            try {
                consumer.consume(dis.readUTF());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}