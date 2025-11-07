package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.presets.TrinketsLootTable;

public class AOESimpleTrinketItem extends SimpleTrinketItem {

	public AOESimpleTrinketItem(Rarity rarity, String buffStringID, int enchantCost) {
		super(rarity, buffStringID, enchantCost, TrinketsLootTable.trinkets);
	}

}
