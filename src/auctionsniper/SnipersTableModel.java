package auctionsniper;

import static auctionsniper.MainWindow.STATUS_JOINING;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
    private String statusText = STATUS_JOINING;
    
    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int i, int il) {
        return statusText;
    }

	public void setStatusText(String newStatusText ) {
        this.statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
	}

}
