package auctionsniper;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Main {
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    private MainWindow ui;

    private Chat notToBeGCd;

    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";

    public Main() {
        startUserInterface();
    }

    public static void main(String... args) throws XMPPException {
        Main main = new Main();
        XMPPConnection connection = connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
        main.joinAuction(connection, args[ARG_ITEM_ID]);

    }

    private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password);
        return connection;
    }

    private void joinAuction(XMPPConnection connection, String itemId) {
        final Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), 
                            (c, message) -> SwingUtilities.invokeLater(() -> ui.showStatus(MainWindow.STATUS_LOST)));
    }

    private String auctionId(String itemId, XMPPConnection connection) {
        return null;
    }

    private void startUserInterface() {
    }
}