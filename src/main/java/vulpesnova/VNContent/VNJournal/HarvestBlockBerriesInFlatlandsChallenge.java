package vulpesnova.VNContent.VNJournal;

import necesse.engine.journal.PickupItemsJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.pickup.ItemPickupEntity;

public class HarvestBlockBerriesInFlatlandsChallenge extends PickupItemsJournalChallenge {

	public HarvestBlockBerriesInFlatlandsChallenge() {
		super(25, true, "blockberryvn");
	}
	public void onItemPickedUp(ServerClient serverClient, ItemPickupEntity entity, int amount,
			boolean addedToNonPlayerInventory) {
		if(VNJournalChallengeUtils.isFlatlandsBiome(serverClient.getLevel().biome)) super.onItemPickedUp(serverClient, entity, amount, addedToNonPlayerInventory);
	}
}
