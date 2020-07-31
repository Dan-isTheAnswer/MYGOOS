package unit;

import static org.junit.Assert.*;
import org.junit.Test;

import auctionsniper.Column;
import auctionsniper.SniperSnapshot;
import auctionsniper.SnipersTableModel;

public class ColumnTest {

	private SniperSnapshot snapshot = SniperSnapshotBuilder.aSnapshot().build();

	@Test
	public void valueInTheItemIdentifierColumnIsForTheSnapshotItemIdentifier() {
		assertEquals(snapshot.itemId, Column.ITEM_IDENTIFIER.valueIn(snapshot));
	}

	@Test
	public void valueInTheLastPriceColumnIsForTheLastPriceOfASnapshot() {
		assertEquals(snapshot.lastPrice, Column.LAST_PRICE.valueIn(snapshot));
	}

	@Test
	public void valueInTheLastBidColumnIsForTheLastBidOfASnapshot() {
		assertEquals(snapshot.lastBid, Column.LAST_BID.valueIn(snapshot));
	}

	@Test
	public void valueInTheStateColumnIsForTheTextualRepresentationOfASnapshotState() {
		assertEquals(SnipersTableModel.textFor(snapshot.state),
				Column.SNIPER_STATE.valueIn(snapshot));
	}
}
