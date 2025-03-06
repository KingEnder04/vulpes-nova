package vulpesnova.VNContent.VNArmors.VNCubaltSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class CubaltHelmetVN extends SetHelmetArmorItem {

    public FloatUpgradeValue combat_mana_regen = (new FloatUpgradeValue()).setBaseValue(0.75F).setUpgradedValue(1.0F, 1.00F);

    public CubaltHelmetVN() {
        super(16, DamageTypeRegistry.NORMAL, 200, Rarity.UNCOMMON, "cubalthelmetvn", "cubaltchestplatevn", "cubaltbootsvn", "cubaltsetvnbonusbuff");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.COMBAT_MANA_REGEN, this.combat_mana_regen.getValue(this.getUpgradeTier(item)))});
    }

}
