package vulpesnova.VNContent.VNArmors.VNWoodSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class WoodenHelmet extends SetHelmetArmorItem {

    public FloatUpgradeValue combat_mana_regen = (new FloatUpgradeValue()).setBaseValue(0.50F).setUpgradedValue(1.0F, 0.25F);

    public WoodenHelmet() {
        super(4, DamageTypeRegistry.MAGIC, 200, Rarity.NORMAL, "woodenhelmetvn", "woodenchestplatevn", "woodenbootsvn", "woodensetvnbonusbuff");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.COMBAT_MANA_REGEN, this.combat_mana_regen.getValue(this.getUpgradeTier(item)))});
    }

}
