package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import auctionsniper.Column;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
    private final static SniperState STARTING_UP = new SniperState("", 0, 0);
    private static String[] STATUS_TEXT = { MainWindow.STATUS_JOINING, 
                                        MainWindow.STATUS_BIDDING};
    private SniperState sniperState = STARTING_UP;
    private String newStatusText = "new!";
    private SniperSnapshot snapshot;

    public int getColumnCount() { 
        return Column.values().length;
    }

    public int getRowCount() { return 1; }

    public Object getValueAt(int rowIndex, int columnIndex) { 
        return Column.at(columnIndex).valueIn(snapshot);
    }

    
    public void setStatusText(String statusText) {
        statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
	}
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
	}
	
    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }

}