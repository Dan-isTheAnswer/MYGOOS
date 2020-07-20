package auctionsniper;

import auctionsniper.util.Defect;

public enum SniperState {
  JOINING,
  WINNING,
  BIDDING,
  LOST,
  WON;

  public SniperState whenAuctionClosed() {
    throw new Defect("Auction is already closed");
  }
}
