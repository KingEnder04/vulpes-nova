package vulpesnova.VNContent.VNArmors.VNWindSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class WindChestplateVN extends ChestArmorItem {
    public FloatUpgradeValue attack_speed = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1.0F, 0.05F);

    public WindChestplateVN() {
        super(6, 200, Rarity.NORMAL, "windchestplatevn", "windchestplatevnarms");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.ATTACK_SPEED, this.attack_speed.getValue(this.getUpgradeTier(item)))});
    }

}
