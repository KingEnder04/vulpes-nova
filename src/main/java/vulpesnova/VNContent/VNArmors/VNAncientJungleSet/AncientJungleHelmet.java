package vulpesnova.VNContent.VNArmors.VNAncientJungleSet;

import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

public class AncientJungleHelmet extends SetHelmetArmorItem {
    public AncientJungleHelmet() {
        super(15, DamageTypeRegistry.MAGIC, 1000, Rarity.LEGENDARY, "ancientjunglehelmetvn", "ancientjunglechestplatevn", "ancientjunglebootsvn", "ancientjunglesetvnbonusbuff");
    }    public FloatUpgradeValue all_damage = (new FloatUpgradeValue()).setBaseValue(0.03F).setUpgradedValue(1.0F, 0.05F);


    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.ALL_DAMAGE, this.all_damage.getValue(this.getUpgradeTier(item)))});
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "ancientjunglesetvntip"));
        return tooltips;
    }

}
