package endtoend;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import auctionsniper.Main;

import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
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

    public void showsSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
        // new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
        // Does this table have a cell with the label text I intended? (Check out)
        new JTableDriver(this).hasRow(
            matching(withLabelText(itemId), withLabelText(String.valueOf(lastPrice)), 
                    withLabelText(String.valueOf(lastBid)), withLabelText(statusText))
        );
    }
}
