package auctionsniper;

import javax.swing.SwingUtilities;

import auctionsniper.ui.MainWindow;

public class Main {
    public static final String MAIN_WINDOW_NAME = "Auction Sniper"; // Create Constant
    //public static String SNIPER_STATUS_NAME; // Create field
    public static final String SNIPER_STATUS_NAME = "??"; // status??
	private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception { // vs. main(String[] args) 
        Main main = new Main();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable(){
            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }
}