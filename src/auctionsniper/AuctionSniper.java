package auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private SniperListener sniperListener;
    private Auction auction;

    private boolean isWinning = false;

	public AuctionSniper(Auction auction, SniperListener listener) {
        this.auction = auction;
        this.sniperListener = listener;
	}

    @Override
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
            auction.bid(price + increment);
            sniperListener.sniperBidding();
        }
    }

}