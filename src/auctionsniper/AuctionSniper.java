package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
    private SniperSnapshot snapshot;

    private boolean isWinning = false;
    private final SniperListener sniperListener;
    private final Auction auction;
    private String itemId;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.auction = auction;
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(itemId);
    }

    public void auctionClosed() {
        if (isWinning) {
            sniperListener.sniperWon();
        } else {
            sniperListener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if (isWinning) {
            sniperListener.sniperWinning();
        } else {
            int bid = price + increment;
            auction.bid(bid);
            sniperListener.sniperStateChanged(
                            new SniperSnapshot(itemId, price, bid, SniperState.BIDDING);
        }
    }

	public void currentPrice(int price, int increment, boolean b) {
	}
}