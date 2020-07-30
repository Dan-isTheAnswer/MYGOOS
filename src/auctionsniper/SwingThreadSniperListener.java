package auctionsniper;

import javax.swing.SwingUtilities;

public class SwingThreadSniperListener implements SniperListener {
//   private final SniperListener delegate;
    private final SnipersTableModel snipers;
  
  public SwingThreadSniperListener(SnipersTableModel snipers) {
    // this.delegate = delegate;
    this.snipers = snipers;
  }
  public void sniperStateChanged(final SniperSnapshot snapshot) { 
    SwingUtilities.invokeLater(new Runnable() { 
      public void run() { 
        // delegate.sniperStateChanged(snapshot); 
        snipers.sniperStateChanged(snapshot);
      } 
    }); 
  }
}
