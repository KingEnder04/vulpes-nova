package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

public class GlassShardSword extends SwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public GlassShardSword() {
        super(300);
        this.rarity = Rarity.NORMAL;
        this.attackAnimTime.setBaseValue(100);
        this.attackDamage.setBaseValue(16.0F).setUpgradedValue(1.0F, 90.0F);
        this.attackRange.setBaseValue(80);
        this.knockback.setBaseValue(30);
        this.resilienceGain.setBaseValue(0.5F);
        this.attackXOffset = 9;
        this.attackYOffset = 10;
        this.setItemCategory("equipment", "weapons", "meleeweapons");
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "glassshardswordvntip"));
        return tooltips;
    }

}
