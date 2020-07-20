package auctionsniper;

import java.text.Bidi;

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
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch(priceSource) {
            case FromSniper:
                snapshot = snapshot.winning(price);
                break;
            case FromOtherBidder:
                snapshot = snapshot.bidding(price, bid);
                break;
        }

        notifyChange();
    }

    private void notifyChange() {
        sniperListener.sniperStateChanged(snapshot);
    }

	// public void currentPrice(int price, int increment, boolean b) {
	// }
}