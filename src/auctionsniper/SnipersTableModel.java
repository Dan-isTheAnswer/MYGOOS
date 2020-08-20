package auctionsniper;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements SniperListener {

    private static String[] STATUS_TEXT = {"Joining", "Bidding", "Winning", "Lost", "Won"};

    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private SniperSnapshot snapshot = STARTING_UP;

    public enum Column {
        ITEM_IDENTIFIER ("Item") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.itemId;
            } 
        } , LAST_PRICE ("Last Price") {

            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.lastPrice;
            } 
        } , LAST_BID ("Last Bid") {

            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.lastBid;
            } 
        } , SNIPER_STATE ("State") {

            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return textFor(snapshot.state);
            } 
        };

        public final String name;

        Column(String name) {
            this.name = name;
        }

        public static Column at(int offset) {
            return values()[offset];
        }

        abstract public Object valueIn(SniperSnapshot snapshot);
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }


    @Override
    public int getRowCount() { // getter from interface TableModel
        return 1;
    }

    @Override
    public int getColumnCount() { // getter
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { //getter
        return Column.at(columnIndex).valueIn(snapshot);
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()]; 
    }

    @Override
    public void sniperStateChanged(SniperSnapshot newSniperSnapshot) {
        snapshot = newSniperSnapshot;
        fireTableRowsUpdated(0, 0);

    }
}
