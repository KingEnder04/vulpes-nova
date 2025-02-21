package vulpesnova.VNContent.VNArmors.VNMeatSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;

public class MeatHat extends SetHelmetArmorItem {
    public MeatHat() {
        super(6, DamageTypeRegistry.SUMMON, 200, Rarity.NORMAL, "meathatvn", "meatrobevn", "meatbootsvn", "meatsetvnbonusbuff");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.MAX_SUMMONS, 1)});
    }
}
