package auctionsniper.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEventListener;

public class AuctionMessageTranslator implements MessageListener {
	private AuctionEventListener listener; // Interface 
	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener; // assigned to Interface
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		listener.auctionClosed();
	}
}


// It is acceptable to assign interface to interface in testing. 
// In other words, by doing so, we can work at higher level of abstraction. 
// In real world, it is not working at all.
// Here, to work correctly, Main class implements AuctionEventListener:)