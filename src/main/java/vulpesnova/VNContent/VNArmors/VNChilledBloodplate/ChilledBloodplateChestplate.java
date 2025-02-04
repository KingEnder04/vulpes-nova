package vulpesnova.VNContent.VNArmors.VNChilledBloodplate;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class ChilledBloodplateChestplate extends ChestArmorItem {
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.3F).setUpgradedValue(1.0F, 1.0F);

    public ChilledBloodplateChestplate() {
        super(11, 700, Rarity.UNCOMMON, "chilledbloodplatechestplatevn", "chilledbloodplatearmsvn");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, this.healthRegen.getValue(this.getUpgradeTier(item))), new ModifierValue(BuffModifiers.ATTACK_SPEED, 0.1F)});
    }
}
