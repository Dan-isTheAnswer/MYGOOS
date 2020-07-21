package auctionsniper;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import static auctionsniper.Main.MAIN_WINDOW_NAME;

import java.awt.Color;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_WINNING = "Winning";
    public static final String STATUS_WON = "Won";
    public static final String STATUS_LOST = "Lost";
    
    public static final String SNIPER_STATUS_NAME = "sniper status";
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);
    
    
    public MainWindow() {
        super("Auction Sniper");
        add(sniperStatus);
        setName(MAIN_WINDOW_NAME);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JLabel createLabel(String initialText) {
        JLabel result = new JLabel (initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    } // How to change Label when its status is changed?
}
