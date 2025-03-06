package vulpesnova.VNContent.VNArmors.VNStoneSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class StoneBoots extends BootsArmorItem {
    public FloatUpgradeValue all_damage = (new FloatUpgradeValue()).setBaseValue(0.03F).setUpgradedValue(1.0F, 0.05F);

    public StoneBoots() {
        super(5, 200, Rarity.NORMAL, "stonebootsvn");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{
        		new ModifierValue<Float>(BuffModifiers.SWIM_SPEED, -0.10F),
        		new ModifierValue<Float>(BuffModifiers.SPEED, -0.05f),
        		new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE,
        				this.all_damage.getValue(this.getUpgradeTier(item)))
        		});
    }
}
