package auctionsniper;

import java.util.EventListener;

public interface AuctionEventListener extends EventListener{
    enum PriceSource {
        FromSniper, FromOtherBidder;
    } // enum in Interface ?? 

    void auctionClosed();
    void currentPrice(int price, int increment, PriceSource fromotherbidder);

}
