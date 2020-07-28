package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
    private final SniperListener sniperListener;
    private final Auction auction;
    private final String itemId;
    private boolean isWinning = false;


	public AuctionSniper(Auction auction, String itemId, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.itemId = itemId;
        this.auction = auction;
	}

	

    
    @Override
    public void auctionClosed() {
        if (isWinning) {
            sniperListener.sniperWon();
        } else {
            sniperListener.sniperLost();
        }
    }
    
    // public void currentPrice(int price, int increment, boolean isWinning) {
    // } **Using the flag, isWinning, is inconsistent. 
    // We'll replace the flag with a value class, named SniperSnapshot.
    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if (isWinning) {
            sniperListener.sniperWinning();
        } else {
            int bid = price + increment;
            auction.bid(bid);
            sniperListener.sniperBidding(new SniperState(itemId, price, bid));
        }
}

	
}
