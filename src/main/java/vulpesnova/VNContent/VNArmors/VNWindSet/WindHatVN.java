package vulpesnova.VNContent.VNArmors.VNWindSet;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class WindHatVN extends SetHelmetArmorItem {
    public WindHatVN() {
        super(4, DamageTypeRegistry.SUMMON, 200, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.NORMAL, "windhatvn", "windchestplatevn", "windbootsvn", "windsetvnbonusbuff");
    }

}
