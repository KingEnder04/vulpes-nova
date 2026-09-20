package vulpesnova.VNContent.VNArmors.VNSkyplateSet;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;

public class SkyplateHelmetVN extends SetHelmetArmorItem {
    public SkyplateHelmetVN() {
        super(4, DamageTypeRegistry.SUMMON, 200, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, Rarity.NORMAL, "skyplatehelmetvn", "skyplatechestplatevn", "skyplatebootsvn", "skyplatesetvnbonusbuff");
    }

}
