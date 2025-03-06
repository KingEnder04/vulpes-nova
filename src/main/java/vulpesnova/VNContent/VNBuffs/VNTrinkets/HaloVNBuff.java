package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class HaloVNBuff extends TrinketBuff {
    public HaloVNBuff() {
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 50);
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, 50);
        buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.25f);
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN_FLAT, 0.5f);
    }
    
	@Override
    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "halovntip"));
        tooltips.add(Localization.translate("itemtooltip", "halovntip2"));
        return tooltips;
    }
}
