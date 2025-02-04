package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;

public class TitanBusterGreatswordVN extends GreatswordToolItem {
    public TitanBusterGreatswordVN() {
        super(1000, getThreeChargeLevels(600, 800, 1000));
        this.rarity = Rarity.LEGENDARY;
        this.attackDamage.setBaseValue(80.0F).setUpgradedValue(1.0F, 170.0F);
        this.attackRange.setBaseValue(114);
        this.knockback.setBaseValue(150);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "titanbustergreatswordchargetip1"));
        tooltips.add(Localization.translate("itemtooltip", "titanbustergreatswordchargetip2"));
        tooltips.add(Localization.translate("itemtooltip", "titanbustergreatswordchargetip3"));
        return tooltips;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        player.startAttackHandler(new TitanBusterGreatswordVNAttackHandler(this, player, slot, item, this, seed, x, y, this.chargeLevels));
        return item;
    }
}
