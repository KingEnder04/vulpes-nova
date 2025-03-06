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

public class ArachnoRemoteVN extends SummonToolItem {
	
    public ArachnoRemoteVN() {
        super("babyspiderkinwarrior", necesse.entity.mobs.itemAttacker.FollowPosition.WALK_CLOSE, 1.0f, 500);
        this.rarity = Rarity.RARE;
        this.attackDamage.setBaseValue(20.0F).setUpgradedValue(1.0F, 50.0F);
    }
    
    @Override
    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
    	
    	int mobsSummonedAtOnce = (int)(1.0F / this.summonSpaceTaken);
		if (level.isServer()) {
			for(int i=0;i<mobsSummonedAtOnce;i++) {
				this.runServerSummon(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
			}
		}
		return item;
	}
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "arachnoremotevntip"));
        return tooltips;
    }
}
