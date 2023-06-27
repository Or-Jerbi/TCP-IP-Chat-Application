package il.ac.hit.chat.server;
import il.ac.hit.chat.StringConsumer;
import il.ac.hit.chat.StringProducer;
import java.io.IOException;

public class ClientDescriptor implements StringProducer, StringConsumer {

    private StringConsumer consumer;
    
    @Override
    public void consume(String text) throws IOException {
        this.consumer.consume(text);
    }

    @Override
    public void addConsumer(StringConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void removeConsumer(StringConsumer consumer) {
        this.consumer = null;
    }
}