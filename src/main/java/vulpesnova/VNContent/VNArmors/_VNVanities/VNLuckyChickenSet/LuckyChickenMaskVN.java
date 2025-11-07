package vulpesnova.VNContent.VNArmors._VNVanities.VNLuckyChickenSet;

import necesse.entity.mobs.gameDamageType.DamageType;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.lootTable.presets.CosmeticArmorLootTable;
import necesse.inventory.lootTable.presets.CosmeticSetArmorLootTable;

public class LuckyChickenMaskVN extends SetHelmetArmorItem {
    public LuckyChickenMaskVN() {
        super(0, (DamageType)null, 0, CosmeticArmorLootTable.cosmeticArmor, CosmeticSetArmorLootTable.cosmeticSetArmor, Rarity.COMMON, "luckychickencostumemaskvn", "luckychickencostumeshirtvn", "luckychickencostumebootsvn", (String)null);
        this.facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        this.hairDrawOptions = HairDrawMode.OVER_HAIR;
    }
}
