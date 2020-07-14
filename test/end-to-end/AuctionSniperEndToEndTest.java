import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws XMPPException, InterruptedException {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    // Additional Cleanup
    @After public void stopAuction() {
        auction.stop();
    }
    @After public void stopApplication() {
        application.stop();
    }
}

// This first test is not really end-to-end. 
// It doesn't include the real auction service 
// because that is not easily available. 

// Must-have skill**
// An important part of the test-driven development skills is
// judging where to set the boundaries of what to test
// and how to eventually cover everything.