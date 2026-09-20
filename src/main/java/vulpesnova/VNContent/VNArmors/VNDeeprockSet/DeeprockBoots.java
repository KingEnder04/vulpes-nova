package vulpesnova.VNContent.VNArmors.VNDeeprockSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class DeeprockBoots extends BootsArmorItem {
    public FloatUpgradeValue all_damage = (new FloatUpgradeValue()).setBaseValue(0.03F).setUpgradedValue(1.0F, 0.05F);

    public DeeprockBoots() {
        super(5, 200, Rarity.NORMAL, "deeprockbootsvn", FeetArmorLootTable.feetArmor);
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{
        		new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE,
        				this.all_damage.getValue(this.getUpgradeTier(item)))
        		});
    }
}
