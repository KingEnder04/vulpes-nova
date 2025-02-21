package vulpesnova.VNContent.VNArmors.VNWindSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class WindBootsVN extends BootsArmorItem {
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.1F).setUpgradedValue(1.0F, 0.25F);

    public WindBootsVN() {
        super(2, 200, Rarity.NORMAL, "windbootsvn");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item)))});
    }
}
