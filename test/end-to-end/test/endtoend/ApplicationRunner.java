package test.endtoend;

import auctionsniper.Main;
import auctionsniper.SniperState;
import auctionsniper.ui.MainWindow;

import static auctionsniper.ui.MainWindow.*;

public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + FakeAuctionServer.XMPP_HOSTNAME + "/Auction";
    private AuctionSniperDriver driver;
    private String itemId;
    

    public void startBiddingIn (final FakeAuctionServer auction) {
        itemId = auction.getItemID();
        Thread thread = new Thread("Test Application") {
            @Override public void run() { // auto-typing isn't worked. 
                try {
                    Main.main(FakeAuctionServer.XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }   
        };
        thread.setDaemon(true);
        thread.start();

        driver = new AuctionSniperDriver(1000);
        driver.hasTitle(MainWindow.APPLICATION_TITLE);
        driver.hasColumnTitles();
        driver.showsSniperStatus(JOINING.itemId, JOINING.lastPrice, 
                                JOINING.lastBid, textFor(SniperState.JOINING);
    }

    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemId, lastPrice, lastBid ,STATUS_BIDDING);
    }

    public void hasShownSniperIsWinning(int winningBid) {
        driver.showsSniperStatus(itemId, winningBid, winningBid, STATUS_WON);
    }
    public void showsSniperHasLostAuction(int lastPrice) {
        driver.showsSniperStatus(itemId, lastPrice, lastPrice ,STATUS_LOST);
    }

    public void showsSniperHasWonAuction(int lastPrice) {
        driver.showsSniperStatus(itemId, lastPrice, lastPrice, STATUS_WON);
    }
    
    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }


}