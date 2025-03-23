package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNMisc.AttackHandlers.TitanBusterGreatswordVNAttackHandler;

public class TitanBusterGreatswordVN extends GreatswordToolItem {
    public TitanBusterGreatswordVN() {
        super(1000, getThreeChargeLevels(600, 800, 1000));
        this.rarity = Rarity.LEGENDARY;
        this.attackDamage.setBaseValue(80.0F).setUpgradedValue(1.0F, 170.0F);
        this.attackRange.setBaseValue(114);
        this.knockback.setBaseValue(150);
    }
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "titanbustergreatswordvnchargetip1"));
        tooltips.add(Localization.translate("itemtooltip", "titanbustergreatswordvnchargetip2"));
        tooltips.add(Localization.translate("itemtooltip", "titanbustergreatswordvnchargetip3"));
        return tooltips;
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
    	attackerMob.startAttackHandler(new TitanBusterGreatswordVNAttackHandler(this, attackerMob, slot, item, this, seed, x, y, this.chargeLevels));
        return item;
    }
}
