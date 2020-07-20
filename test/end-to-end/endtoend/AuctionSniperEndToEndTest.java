package endtoend;

import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test public void
    sniperJoinsAuctionUntilAuctionCloses() {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test public void
    sniperMakesAHigherBidButLoses() {

    }

    @Test public void
    sniperWinsAnAuctionByBiddingHigher() {

    }


    @After public void stopAuction() {
        auction.stop();
    }

    @After public void stopApplication() {
        application.stop();
    }


}