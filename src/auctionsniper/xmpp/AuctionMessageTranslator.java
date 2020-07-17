package auctionsniper.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionSniper;
import auctionsniper.AuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {
	private AuctionEventListener listener; // Interface
	private final String sniperId;
	private AuctionSniper auctionSniper;
	// "final blank" warning present if these three fields are not initialized by
	// constructor.
	// public AuctionMessageTranslator(AuctionEventListener listener) {
	// this.listener = listener; // assigned to Interface
	// }

	public AuctionMessageTranslator(String sniperId, AuctionSniper auctionSniper) {
		this.sniperId = sniperId;
		this.auctionSniper = auctionSniper;
	}

	public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
		this.sniperId = sniperId;
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		AuctionEvent event = AuctionEvent.from(message.getBody());

		String eventType = event.type();
		if ("CLOSE".equals(eventType)) {
			listener.auctionClosed();
		}
		if ("PRICE".equals(eventType)) {
			listener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));
		}
	}

	private static class AuctionEvent {
		private final Map<String, String> fields = new HashMap<>();

		public String type() {
			return get("Event");
		}

		public PriceSource isFrom(String sniperId) {
			return sniperId.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
		}
		private String bidder() { return get("Bidder");}

		public int currentPrice() {
			return getInt("CurrentPrice");
		}
		public int increment() {return getInt("Increment");}

		private int getInt(String fieldName) {
			return Integer.parseInt(get(fieldName));
		}

		private String get(String fieldName) {return fields.get(fieldName);}

		private void addField(String field) {
			String[] pair = field.split(":");
			fields.put(pair[0].trim(), pair[1].trim());
		}

		static AuctionEvent from(String messageBody) {
			AuctionEvent event = new AuctionEvent();
			for (String field : fieldsIn(messageBody)) {
				event.addField(field);
			}
			return event;
		}

		static String[] fieldsIn(String messageBody) {
			return messageBody.split(";");
		}
	}
}


// It is acceptable to assign interface to interface in testing. 
// In other words, by doing so, we can work at higher level of abstraction. 
// In real world, it is not working at all.
// Here, to work correctly, Main class implements AuctionEventListener:)