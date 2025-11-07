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
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;

public class NecromancersRequiemVN extends SummonToolItem {
	
    public NecromancersRequiemVN() {
        super("babyzombie", necesse.entity.mobs.itemAttacker.FollowPosition.PYRAMID, 0.25f, 2000, SummonWeaponsLootTable.summonWeapons);
        this.rarity = Rarity.UNIQUE;
        this.attackDamage.setBaseValue(26.0F).setUpgradedValue(1.0F, 40.0F);
    }
    
    @Override
    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "necromancersrequiemvntip"));
        return tooltips;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        AttackingFollowingMob mob1 = (AttackingFollowingMob)MobRegistry.getMob("babyzombie", level);
        this.summonServerMob(attackerMob, mob1, x, y, attackHeight, item);
        AttackingFollowingMob mob2 = (AttackingFollowingMob)MobRegistry.getMob("babyzombiearcher", level);
        this.summonServerMob(attackerMob, mob2, x, y, attackHeight, item);
        AttackingFollowingMob mob3 = (AttackingFollowingMob)MobRegistry.getMob("babyskeleton", level);
        this.summonServerMob(attackerMob, mob3, x, y, attackHeight, item);
        AttackingFollowingMob mob4 = (AttackingFollowingMob)MobRegistry.getMob("babyskeletonmage", level);
        this.summonServerMob(attackerMob, mob4, x, y, attackHeight, item);
    }

    /*@Override
    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob) {
		return 4;
	}*/
}
