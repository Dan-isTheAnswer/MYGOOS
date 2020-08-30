package auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private SniperListener sniperListener;
    private Auction auction;

	public AuctionSniper(Auction auction, SniperListener listener) {
        this.auction = auction;
        this.sniperListener = listener;
	}

    @Override
	public void auctionClosed() {
        sniperListener.sniperLost();
	}

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        auction.bid(price + increment);
        sniperListener.sniperBidding();
    }

}