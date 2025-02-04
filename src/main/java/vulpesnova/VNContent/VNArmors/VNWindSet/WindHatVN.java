package vulpesnova.VNContent.VNArmors.VNWindSet;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;

public class WindHatVN extends SetHelmetArmorItem {
    public WindHatVN() {
        super(4, DamageTypeRegistry.SUMMON, 200, Rarity.NORMAL, "windhatvn", "windchestplatevn", "windbootsvn", "windsetvnbonusbuff");
    }

}
