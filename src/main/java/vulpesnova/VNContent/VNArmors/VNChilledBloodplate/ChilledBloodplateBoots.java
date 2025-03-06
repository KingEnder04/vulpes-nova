package vulpesnova.VNContent.VNArmors.VNChilledBloodplate;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
public class ChilledBloodplateBoots extends BootsArmorItem {
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1.0F, 0.30F);
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.20F).setUpgradedValue(1.0F, 0.6F);

    public ChilledBloodplateBoots() {
        super(6, 700, Rarity.UNCOMMON, "chilledbloodplatebootsvn");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item))), new ModifierValue<Float>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, this.healthRegen.getValue(this.getUpgradeTier(item)))});
    }
}