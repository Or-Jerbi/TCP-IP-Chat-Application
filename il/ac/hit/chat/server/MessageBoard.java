package il.ac.hit.chat.server;

import il.ac.hit.chat.ConnectionProxy;
import il.ac.hit.chat.StringConsumer;
import il.ac.hit.chat.StringProducer;
import java.io.IOException;
import java.util.*;
import java.util.Iterator;
import javax.lang.model.util.ElementScanner6;

public class MessageBoard implements StringConsumer, StringProducer {

    private Vector<ConnectionProxy> connections = new Vector<ConnectionProxy>();
    private List<String> names = new ArrayList<>();
    private String connectedUsers;

    @Override
    public void consume(String text)  {
        String temp = text;
        if (temp.endsWith("disconnected"))
        {
            int indexDisonnected = temp.lastIndexOf("disconnected");
            if (indexDisonnected != -1) {
                // Extract the name before "disconnected"
                String name = temp.substring(0, indexDisonnected).trim();
                removeName(name);            
            }
            for(ConnectionProxy connection : connections) {      
                connection.consume(text);  
                connection.consume(connectedUsers);          
            }
        }

        else if (temp.endsWith("connected"))
        {
            int connectedIndex = temp.lastIndexOf("connected");
            if (connectedIndex != -1) {
                // Extract the name before "connected"
                String name = temp.substring(0, connectedIndex).trim();
                addName(name);            
            }
            for(ConnectionProxy connection : connections) {      
                connection.consume(text); 
                connection.consume(connectedUsers);              
            }
        }

        else
        {
            Iterator<ConnectionProxy> iterator = connections.iterator();
            if (temp.endsWith("Everyone"))
            {
                String endSequence = "!@#end#@!";
                int endIndex = temp.indexOf(endSequence);
                String result = temp.substring(0, endIndex);

                while (iterator.hasNext()) {
                        ConnectionProxy connection = iterator.next();
                        connection.consume(result);
                        }
            }

            else
            {             
                String endSequence = "!@#end#@!";
                int endIndex = temp.indexOf(endSequence);
                String reciver = temp.substring(endIndex + endSequence.length());


                String tempText = text;
                String separator = ":";
                int separatorIndex = tempText.indexOf(separator);
                String sender = tempText.substring(0, separatorIndex);


                String anotherTempText = text;
                int startIndex = anotherTempText.indexOf(separator);
                String message = anotherTempText.substring(startIndex + separator.length(), endIndex);
                
                for(ConnectionProxy connection : connections) { 
                    if (connection.getNameHost().equals(reciver)) {
                        connection.consume(sender + " : " + message);     
                    }
                    if (connection.getNameHost().equals(sender)){
                        connection.consume(sender + " : " + message); 
                    }                        
                }                                                  
            }
        }              
    }

    @Override
    public void addConsumer(StringConsumer consumer) {
        connections.add((ConnectionProxy)consumer); 
    }

    public void addName(String name) {
        names.add(name);
        this.connectedUsers = String.join("!@#end#@!", names);
        this.connectedUsers+= "!@#end#@!";
    }

    @Override
    public void removeConsumer(StringConsumer consumer) {
        connections.remove((ConnectionProxy)consumer);
    }

    public void removeName(String name) {
        names.remove(name);
        this.connectedUsers = String.join("!@#end#@!", names);
        this.connectedUsers+= "!@#end#@!";
    }
}