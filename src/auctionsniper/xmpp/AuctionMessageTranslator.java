package auctionsniper.xmpp;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionSniper;

public class AuctionMessageTranslator implements MessageListener {
	private AuctionEventListener listener; // Interface 
	private String sniperId;
	private AuctionSniper auctionSniper;
	// "final blank" warning present if these three fields are not initialized by constructor.  
	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener; // assigned to Interface
	}

	public AuctionMessageTranslator(String sniperId, AuctionSniper auctionSniper) {
		this.sniperId = sniperId;
		this.auctionSniper = auctionSniper;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> event = unpackEventFrom(message);

		String type = event.get("Event");
		if ("CLOSE".equals(type)) {
			listener.auctionClosed();
		} else if ("PRICE".equals(type)) {
			listener.currentPrice(Integer.parseInt(event.get("CurrentPrice")), 
								Integer.parseInt(event.get("Increment")));
		}
	}

	private HashMap<String, String> unpackEventFrom(Message message) {
		HashMap<String, String> event = new HashMap<String, String>();
		for (String element : message.getBody().split(";")) {
			String[] pair = element.split(":");
			event.put(pair[0].trim(), pair[1].trim());
		}
		return event;
	}
}


// It is acceptable to assign interface to interface in testing. 
// In other words, by doing so, we can work at higher level of abstraction. 
// In real world, it is not working at all.
// Here, to work correctly, Main class implements AuctionEventListener:)