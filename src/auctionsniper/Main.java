package auctionsniper;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.ui.MainWindow;
import auctionsniper.xmpp.AuctionMessageTranslator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main implements SniperListener {
    @SuppressWarnings("unusesd") private Chat notToBeGCd;
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%S/" + AUCTION_RESOURCE;

    public static final String MAIN_WINDOW_NAME = "Auction Sniper"; // Create Constant
    // Warning: JOIN_COMMAND_FORMAT & BID_COMMAND_FORMAT are required to be initialized to something else. 
	public static final String JOIN_COMMAND_FORMAT = "Join Command Format";
	public static final String BID_COMMAND_FORMAT = "Bid Command Format";
    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception { // vs. main(String[] args)
        Main main = new Main();
        main.joinAuction(connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]), 
                args[ARG_ITEM_ID]);
    }

    private static XMPPConnection 
    connection(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);

        return connection;
    }

    // Warning: throws XMPPException
    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        disconnectWhenUICloses(connection);
        Chat chat = connection.getChatManager().createChat(
            auctionId(itemId, connection), 
            new AuctionMessageTranslator(new AuctionSniper(this)));
        this.notToBeGCd = chat;

        chat.sendMessage(JOIN_COMMAND_FORMAT);
        // notToBeGCd = chat;
    }

    private void disconnectWhenUICloses(final XMPPConnection connection) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                connection.disconnect();
            }
        });

    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, 
                            connection.getServiceName());
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable(){
            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }


    @Override
    public void sniperLost() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.showStatus(MainWindow.STATUS_LOST);
            }
        });
    }
}