package auctionsniper;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
    private final static SniperState STARTING_UP = new SniperState("", 0, 0);
    private String statusText = MainWindow.STATUS_JOINING;
    private SniperState sniperState = STARTING_UP;


    private final static String[] STATUS_TEXT = {
        "Joining", "Bidding", "Winning", "Lost", "Won"
    };
    
    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
    }
    
    public int getColumnCount() { 
        return Column.values().length;
    }
    public int getRowCount() { return 1; }

    
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Column.at(columnIndex).valueIn(snapshot);
    }

    public void setStatusText(String newStatusText) {
        statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    @Override public String getColumnName(int column) {
        return Column.at(column).name;
    }
    
    // public void sniperStatusChanged(SniperState newSniperState, 
    //                                     String newStatusText)
    // {
        //         sniperState = newSniperState;
        //         statusText = newStatusText;
        //         fireTableRowsUpdated(0, 0);
        // }
}
