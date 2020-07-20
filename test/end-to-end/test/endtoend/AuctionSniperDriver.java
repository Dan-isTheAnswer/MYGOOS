package test.endtoend;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import auctionsniper.Main;

import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;

public class AuctionSniperDriver extends JFrameDriver{
    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(), 
            JFrameDriver.topLevelFrame(
                named(Main.MAIN_WINDOW_NAME), showingOnScreen()), 
            new AWTEventQueueProber(timeoutMillis, 100));
    }


    public void showsSniperStatus(String itemId, int lastPrice, int lastBid,
                                String statusText) {
        // new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
        JTableDriver table = new JTableDriver(this);
        table.hasRow(
            matching(withLabelText(itemId), withLabelText(String.valueOf(lastPrice)),
                        withLabelText(String.valueOf(lastBid)), withLabelText(statusText))
        );
    }

    public void hasColumnTitles() {
        JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
        headers.hasHeaders(matching(withLabelText("Item"), withLabelText("Last Price"), 
                            withLabelText("Last Bid"), withLabelText("State")));
    }
}