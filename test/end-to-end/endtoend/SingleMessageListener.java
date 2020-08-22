package endtoend;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class SingleMessageListener implements MessageListener {

    private final ArrayBlockingQueue<Message> messages 
                    = new ArrayBlockingQueue<>(1);
    
    @Override
    public void processMessage(Chat chat, Message message) {
        messages.add(message); 
    } 

    public void receivesAMessage(Matcher<String> messageMatcher) throws InterruptedException {
        final Message message =  messages.poll(5, TimeUnit.SECONDS);
        assertThat("Message", message, is(notNullValue()));
        assertThat(message.getBody(), messageMatcher);
    }
}

// https://junit.org/junit4/javadoc/latest/org/junit/Assert.html#assertThat(java.lang.String,%20T,%20org.hamcrest.Matcher)
