package vulpesnova.VNContent.VNArmors.VNGraftedSet;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class GraftedHat extends SetHelmetArmorItem {
    public GraftedHat() {
        super(6, DamageTypeRegistry.SUMMON, 200, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.NORMAL, "graftedhatvn", "graftedrobevn", "graftedbootsvn", "graftedsetvnbonusbuff");
    }
    
	@Override
    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_SUMMONS, 1)});
    }
}
