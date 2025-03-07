package vulpesnova.VNContent.VNArmors.VNChilledBloodplate;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class ChilledBloodplateHelmet extends SetHelmetArmorItem {
    public FloatUpgradeValue healthRegen = (new FloatUpgradeValue()).setBaseValue(0.15F).setUpgradedValue(1.0F, 0.5F);

    public ChilledBloodplateHelmet() {
        super(8, DamageTypeRegistry.MELEE, 600, Rarity.UNCOMMON, "chilledbloodplatehelmetvn", "chilledbloodplatechestplatevn", "chilledbloodplatebootsvn", "chilledbloodplatehelmetsetvnbonusbuff");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, this.healthRegen.getValue(this.getUpgradeTier(item))), new ModifierValue<Float>(BuffModifiers.MELEE_DAMAGE, 0.1F)});
    }

}