package auctionsniper;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class Main {

    public static final String MAIN_WINDOW_NAME = "Action Sniper";
    public static final String SNIPER_STATUS_NAME = "sniper status";

    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                ui = new MainWindow();
            }

        });
    }
}