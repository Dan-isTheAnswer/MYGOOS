package endtoend;

import static endtoend.FakeAuctionServer.XMPP_HOSTNAME;

import auctionsniper.Main;
import auctionsniper.MainWindow;

public class ApplicationRunner {
	public static final String SNIPER_XMPP_ID = "sniper@localhost/Auction";

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";

    private AuctionSniperDriver driver; 
    public void startBiddingIn(FakeAuctionServer auction) { 
        Thread thread = new Thread("Test Application") { 
            @Override public void run() { 
                try { 
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId()); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(MainWindow.STATUS_JOINING);
    }

    public void showSniperHasLostAuction() {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }

	public void hasShownSniperIsBidding() {
        driver.showsSniperStatus(MainWindow.STATUS_BIDDING);
	}
}
