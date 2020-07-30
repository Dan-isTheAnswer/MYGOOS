package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
    private final SniperListener sniperListener;
    private final Auction auction;
    private final String itemId;
    private boolean isWinning = false;
    private SniperSnapshot snapshot;

	public AuctionSniper(Auction auction, String itemId, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(itemId);
        this.auction = auction;
	}

	

    
    @Override
    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }
    
    // public void currentPrice(int price, int increment, boolean isWinning) {
    // } **Using the flag, isWinning, is inconsistent. 
    // We'll replace the flag with a value class, named SniperSnapshot.
    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch(priceSource) {
        case FromSniper:
            snapshot = snapshot.winning(price);
            break;
        case FromOtherBidder:
            int bid = price + increment;
            auction.bid(bid);
            snapshot = snapshot.bidding(price, bid);
            break;
        }
        notifyChange();
    }

    private void notifyChange() {
        sniperListener.sniperStateChanged(snapshot);
    }
	
}
