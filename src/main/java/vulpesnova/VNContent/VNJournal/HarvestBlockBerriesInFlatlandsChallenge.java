package vulpesnova.VNContent.VNJournal;

import necesse.engine.journal.PickupItemsJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.pickup.ItemPickupEntity;
import necesse.level.maps.Level;

public class HarvestBlockBerriesInFlatlandsChallenge extends PickupItemsJournalChallenge {

	public HarvestBlockBerriesInFlatlandsChallenge() {
		super(25, true, "blockberryvn");
	}
	public void onItemPickedUp(ServerClient serverClient, ItemPickupEntity entity, int amount,
			boolean addedToNonPlayerInventory) {
				Level level = entity.getLevel();
		if(VNJournalChallengeUtils.isFlatlandsBiome(level.getBiome(entity.getTileX(), entity.getTileY()))) super.onItemPickedUp(serverClient, entity, amount, addedToNonPlayerInventory);
	}
}
