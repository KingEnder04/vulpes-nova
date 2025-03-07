package vulpesnova.VNContent.VNArmors.VNStoneSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class StoneHelmet extends SetHelmetArmorItem {
    public FloatUpgradeValue all_damage = (new FloatUpgradeValue()).setBaseValue(0.04F).setUpgradedValue(1.0F, 0.05F);

    public StoneHelmet() {
        super(8, DamageTypeRegistry.MELEE, 200, Rarity.NORMAL, "stonehelmetvn", "stonechestplatevn", "stonebootsvn", "stonesetvnbonusbuff");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE, this.all_damage.getValue(this.getUpgradeTier(item)))});
    }

}
