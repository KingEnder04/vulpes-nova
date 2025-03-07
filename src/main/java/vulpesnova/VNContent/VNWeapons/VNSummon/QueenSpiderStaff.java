package vulpesnova.VNContent.VNWeapons.VNSummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class QueenSpiderStaff extends SummonToolItem {
    private int mobsSummonedAtOnce = 3;
	public QueenSpiderStaff() {
        super("babyspider", necesse.entity.mobs.itemAttacker.FollowPosition.PYRAMID, 0.33F, 400);
        this.rarity = Rarity.EPIC;
        this.attackDamage.setBaseValue(10.0F).setUpgradedValue(1.0F, 30.0F);
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }
    
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "queenspiderstaffvntip"));
        return tooltips;
    }
    
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
    	
		if (level.isServer()) {
			for(int i=0;i<this.mobsSummonedAtOnce;i++) {
			this.runServerSummon(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
			}
		}
		return item;
	}
    
    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) {
		return 3;
	}
}
