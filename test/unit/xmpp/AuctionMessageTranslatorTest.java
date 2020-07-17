package xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionEventListener.PriceSource;
import auctionsniper.xmpp.AuctionMessageTranslator;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
    public static final Chat UNUSED_CHAT = null;
    private static final String SNIPER_ID = "sniper id";
    private final Mockery context = new Mockery();
    private final AuctionEventListener listener = 
                    context.mock(AuctionEventListener.class);
    private final AuctionMessageTranslator translator = 
                    new AuctionMessageTranslator(SNIPER_ID, listener);

    @Test public void
    notifiesAuctionClosedWhenCloseMessageReceived() {
        context.checking(new Expectations() {
            {
                oneOf(listener).auctionClosed();
            }
            // @Override
            // public <T> T oneOf(T mockObject) {
            //     // TODO Auto-generated method stub
            //     return super.oneOf(mockObject);
            // } // {oneOf()} vs oneOf() **
        });        
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");

        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test public void
    notifiesBidDetailsWhenCurrentPriceMessageReceivedFromBidder() {
        context.checking(new Expectations() {
            {
                exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
            }
        });
        Message message = new Message();
        message.setBody(
            "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;"
        );
        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test public void 
    notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
        context.checking(new Expectations() {
            {
                exactly(1).of(listener).currentPrice(234, 5, PriceSource.FromSniper);
            }
        });
        Message message = new Message();
        message.setBody(
            "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: "
            + SNIPER_ID + ";"
        );
        translator.processMessage(UNUSED_CHAT, message);
    }

    
}

