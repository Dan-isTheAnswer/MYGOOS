package endtoend;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import auctionsniper.Main;

import static org.hamcrest.Matchers.equalTo;
// import static auctionsniper.Main.MAIN_WINDOW_NAME;
// import static auctionsniper.Main.SNIPER_STATUS_NAME;

public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(),
            JFrameDriver.topLevelFrame(
                named(Main.MAIN_WINDOW_NAME),
                showingOnScreen()),
            new AWTEventQueueProber(timeoutMillis, 100)   
        );
    } // JFrameDriver constructor ??

    public void showsSniperStatus(String statusText) {
        new JLabelDriver(
            this, named(Main.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
    }
}
