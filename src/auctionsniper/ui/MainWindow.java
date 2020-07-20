package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

import static auctionsniper.Main.MAIN_WINDOW_NAME;

public class MainWindow extends JFrame {
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding"; 
	public static final String STATUS_WINNING = "Winning";
	public static final String STATUS_WON = "Won";

    public static final String SNIPER_STATUS_NAME = "sniper status";
    private static final String SNIPER_TABLE_NAME = "sniper table";
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);
    private final SnipersTableModel snipers = new SnipersTableModel();

    public MainWindow(SnipersTableModel snipers) {
        // TODO with SnipersTableModel snipers
        super("Auction Sniper Main");
        setName(MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable snipersTable) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable() {
        final JTable snipersTable = new JTable(snipers);
        snipersTable.setName(SNIPER_TABLE_NAME);
        return snipersTable;
    }

    private static JLabel createLabel(String initialText) {
        JLabel result = new JLabel (initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }

	public void showStatus(String status) {
        sniperStatus.setText(status);
    }
    
    public void showStatusText(String statusText) {
        snipers.setStatusText(statusText);
    }

	public void sniperStateChanged(SniperSnapshot snapshot) {
        snipers.sniperStateChanged(snapshot);
	}
}