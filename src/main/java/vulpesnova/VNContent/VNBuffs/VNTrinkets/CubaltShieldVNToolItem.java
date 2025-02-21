package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.ShieldTrinketItem;
import necesse.level.maps.light.GameLight;

public class CubaltShieldVNToolItem extends ShieldTrinketItem {

    public static GameTexture holdTexture;


    public CubaltShieldVNToolItem(Item.Rarity rarity, int enchantCost) {
        super(rarity, 10, 0.5F, 9000, 0.10F, 0, 360.0F, enchantCost);
    }

    public ListGameTooltips getExtraShieldTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getExtraShieldTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cubaltshieldvntip"));
        return tooltips;
    }

}
