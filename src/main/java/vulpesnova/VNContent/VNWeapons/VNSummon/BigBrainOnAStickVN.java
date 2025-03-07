package vulpesnova.VNContent.VNWeapons.VNSummon;

import java.util.List;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.ToolItemSummonedMob;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class BigBrainOnAStickVN extends SummonToolItem {
    public BigBrainOnAStickVN() {
        super("babyzombie", necesse.entity.mobs.itemAttacker.FollowPosition.PYRAMID, 0.5F, 500);
        this.rarity = Rarity.UNCOMMON;
        this.attackDamage.setBaseValue(11.0F).setUpgradedValue(1.0F, 30.0F);
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
    	
		if (level.isServer()) {
			
			List<ToolItemSummonedMob> slist = List.of(new ToolItemSummonedMob[] {
					(AttackingFollowingMob) MobRegistry.getMob("babyzombie", level),
					(AttackingFollowingMob) MobRegistry.getMob("babyzombiearcher", level)
			} );
	
			slist.forEach((mob)->summonServerMob(attackerMob, mob, x, y, attackHeight, item));		
		}
		return item;
	}
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bigbrainstickvntip"));
        return tooltips;
    }
}