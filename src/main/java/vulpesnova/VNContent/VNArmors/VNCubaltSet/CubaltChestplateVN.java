package vulpesnova.VNContent.VNArmors.VNCubaltSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;

public class CubaltChestplateVN extends ChestArmorItem {

    public CubaltChestplateVN() {
        super(19, 200, Rarity.UNCOMMON, "cubaltchestplatevn", "cubaltchestvnarms");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE, 0.1f)});
    }
}
