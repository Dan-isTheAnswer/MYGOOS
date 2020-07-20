package test.endtoend;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final FakeAuctionServer auction2 = new FakeAuctionServer("item-65432");
    
    private final ApplicationRunner application = new ApplicationRunner();

    @Test public void 
    sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction(auction, 0, 0);
    }

    @Test public void 
    sniperMakesAHigherBidButLoses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        auction.reportPrice(1000, 98, "other bidder");
        application.hasShownSniperIsBidding(auction, 1000, 1098);

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.hasShownSniperHasLostAuction(auction, 1000, 1098);
    }

    @Test public void
    sniperWinsAnAuctionByBiddingHigher() {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        auction.reportPrice(1000, 98, "other bidder");
        application.hasShownSniperIsBidding(auction, 1000, 1098);

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
        application.hasShownSniperIsWinning(auction, 1098);

        auction.announceClosed();
        application.hasShownSniperHasWonAuction(auction, 1098);
    } 

    @Test public void 
    sniperBidsForMultipleItems() {
        // auction.startSellingItem();
        // auction2.startSellingItem();

        // application.startBiddingIn(auction, auction2);
        // auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        // auction2.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);

        // auction.reportPrice(1000, 98, "other bidder");
        // auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        // auction2.reportPrice(500, 21, "other bidder");
        // auction2.hasReceivedBid(521, ApplicationRunner.SNIPER_XMPP_ID);

        // auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
        // auction2.reportPrice(521, 22, ApplicationRunner.SNIPER_XMPP_ID);

        // application.hasShownSniperIsWinning(auction, 1098);
        // application.hasShownSniperIsWinning(auction2, 521);

        // auction.announceClosed();
        // auction2.announceClosed();

        // application.hasShownSniperHasWonAuction(auction, 1098);
        // application.hasShownSniperHasWonAuction(auction2, 521);
    }

    @Test public void
    sniperLosesAnAuctionWhenThePriceIsTooHigh() {
        // auction.startSellingItem();

        // application.startBiddingWithStopPrice(auction, 1100);
        // auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
        // auction.reportPrice(1000, 98, "other bidder");
        // application.hasShownSniperIsBidding(auction, 1000, 1098);

        // auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        // auction.reportPrice(1197, 10, "third party");
        // application.hasShownSniperIsLosing(auction, 1197, 1098);

        // auction.reportPrice(1207, 10, "fourth party");
        // application.hasShownSniperIsLosing(auction, 1207, 1098);
        // auction.announceClosed();
        // application.hasShownSniperHasLostAuction(auction, 1207, 1098);
    }

    @Test public void
    sniperReportsInvalidAuctionMessageAndStopsRespondingToEvents() {

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

// written at scratch branch