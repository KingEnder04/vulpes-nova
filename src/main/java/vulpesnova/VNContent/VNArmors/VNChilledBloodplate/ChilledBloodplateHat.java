package vulpesnova.VNContent.VNArmors.VNChilledBloodplate;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class ChilledBloodplateHat extends SetHelmetArmorItem {
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1.0F, 0.5F);
    public FloatUpgradeValue manaRegen = (new FloatUpgradeValue()).setBaseValue(0.5F).setUpgradedValue(1.0F, 1.5F);

    public ChilledBloodplateHat() {
        super(6, DamageTypeRegistry.MAGIC, 600, Rarity.UNCOMMON, "chilledbloodplatehatvn", "chilledbloodplatechestplatevn", "chilledbloodplatebootsvn", "chilledbloodplatehatsetvnbonusbuff");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, this.healthRegen.getValue(this.getUpgradeTier(item))), new ModifierValue<Float>(BuffModifiers.MAGIC_DAMAGE, 0.1F), new ModifierValue<Float>(BuffModifiers.COMBAT_MANA_REGEN, this.manaRegen.getValue(this.getUpgradeTier(item)))});
    }
}