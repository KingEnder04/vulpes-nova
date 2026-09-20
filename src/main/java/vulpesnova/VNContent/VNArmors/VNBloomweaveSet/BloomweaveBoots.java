package vulpesnova.VNContent.VNArmors.VNBloomweaveSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.FeetArmorLootTable;

public class BloomweaveBoots extends BootsArmorItem {
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.1F).setUpgradedValue(1.0F, 0.25F);

    public BloomweaveBoots() {
        super(2, 200, Rarity.NORMAL, "bloomweavebootsvn", FeetArmorLootTable.feetArmor);
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item)))});
    }
}
