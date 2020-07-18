package auctionsniper;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.ui.MainWindow;
import auctionsniper.xmpp.AuctionMessageTranslator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main  {
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
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
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

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        disconnectWhenUICloses(connection);
        
        
        final Chat chat = connection.getChatManager().createChat(
            auctionId(itemId, connection), null); 
        this.notToBeGCd = chat;
            
        // Auction auction = new Auction(){
        //     public void bid(int amount) {
        //         try {
        //             chat.sendMessage(String.format(BID_COMMAND_FORMAT, amount));
        //         } catch (XMPPException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // };
        Auction auction = new XMPPAuction(chat);
        chat.addMessageListener(
            new AuctionMessageTranslator(
                connection.getUser(),
                new AuctionSniper(auction, new SniperStateDisplayer()))
        );
        auction.join();
        // chat.sendMessage(JOIN_COMMAND_FORMAT);
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



    public static class XMPPAuction implements Auction {
        private final Chat chat;

        public XMPPAuction(Chat chat) {
            this.chat = chat;
        }

        public void bid(int amount) {
            sendMessage(String.format(BID_COMMAND_FORMAT, amount));
        }

        public void join() {
            sendMessage(JOIN_COMMAND_FORMAT);
        }

        private void sendMessage(final String message) {
            try {
                chat.sendMessage(message);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        }


    public class SniperStateDisplayer implements SniperListener {
        public void sniperBidding() {
            showStatus(MainWindow.STATUS_BIDDING);
        }

        public void sniperLost() {
            showStatus(MainWindow.STATUS_LOST);
        }

        public void sniperWinning() {
            showStatus(MainWindow.STATUS_WINNING); 
        }

        public void sniperWon() {
            showStatus(MainWindow.STATUS_WON);
        }

        private void showStatus(String status) {
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    ui.showStatus(status);
                }
            });
        }

    }
}