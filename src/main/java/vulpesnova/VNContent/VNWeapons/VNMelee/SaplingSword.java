package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

public class SaplingSword extends SwordToolItem {
	
    public SaplingSword() {
        super(200);
        this.rarity = Rarity.COMMON;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(19).setUpgradedValue(1.0F, 90.0F);
        this.attackRange.setBaseValue(80);
        this.knockback.setBaseValue(75);
        this.attackXOffset = 9;
        this.attackYOffset = 10;
        this.setItemCategory("equipment", "weapons", "meleeweapons");
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "saplingswordvntip"));
        return tooltips;
    }

}
