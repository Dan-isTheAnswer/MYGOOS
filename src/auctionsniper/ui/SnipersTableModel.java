package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
    private String statusText = MainWindow.STATUS_JOINING;
    private String newStatusText = "";

    public int getColumnCount() { return 1;}
    public int getRowCount() { return 1; }
    public Object getValueAt(int rowIndex, int columnIndex) { return statusText; }
	public void setStatusText(String statusText) {
        statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
	}

}