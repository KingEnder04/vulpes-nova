package vulpesnova.VNContent.VNArmors.VNMeatSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class MeatBoots extends BootsArmorItem {
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.1F).setUpgradedValue(1.0F, 0.25F);

    public MeatBoots() {
        super(5, 200, Rarity.NORMAL, "meatbootsvn");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item))), new ModifierValue<Float>(BuffModifiers.SUMMONS_SPEED, 0.2F)});
    }
}