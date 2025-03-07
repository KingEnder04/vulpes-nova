package vulpesnova.VNContent.VNArmors.VNMeatSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;

public class MeatRobe extends ChestArmorItem {
    public MeatRobe() {
        super(8, 200, Rarity.NORMAL, "meatrobevn", "meatrobearmsvn");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0.5F)});
    }

}
