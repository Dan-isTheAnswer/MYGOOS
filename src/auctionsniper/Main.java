package auctionsniper;

import static auctionsniper.MainWindow.STATUS_BIDDING;
import static auctionsniper.MainWindow.STATUS_LOST;
import static auctionsniper.MainWindow.STATUS_WINNING;

import java.lang.reflect.InvocationTargetException;

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

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String SNIPER_STATUS_NAME = "sniper status";
    public static final String BID_COMMAND_FORMAT = "";
    public static final String JOIN_COMMAND_FORMAT = "";

    public Main() throws InvocationTargetException, InterruptedException {
        startUserInterface();
    }

    private void startUserInterface() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
    }

    public static void main(String... args) throws XMPPException, InvocationTargetException, InterruptedException {
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

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), null);
        notToBeGCd = chat;
        
        Auction auction = new XMPPAuction(chat);
        chat.addMessageListener(new AuctionMessageTranslator(new AuctionSniper(auction, new SniperStateDisplayer())));
        auction.join();
    }


    private String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }


    private class SniperStateDisplayer implements SniperListener {

        @Override
        public void sniperLost() {
            showStatus(STATUS_LOST);
        }

        @Override
        public void sniperBidding() {
            showStatus(STATUS_BIDDING);
        }

        public void sniperWinning() {
            showStatus(STATUS_WINNING);
        }

        private void showStatus(final String status) {
            SwingUtilities.invokeLater(() -> ui.showStatus(status));
        }
    }

}