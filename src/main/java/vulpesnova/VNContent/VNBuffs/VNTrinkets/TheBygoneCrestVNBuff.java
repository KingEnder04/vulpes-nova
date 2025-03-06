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

public class TheBygoneCrestVNBuff extends TrinketBuff {

    public TheBygoneCrestVNBuff() {
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.15F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.15F);
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.10F);
        buff.setModifier(BuffModifiers.MAX_HEALTH, -0.20f);
        buff.setModifier(BuffModifiers.MAX_MANA, -0.50f);
    }
    
	@Override
    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "thebygonecrestvntip1"));
        tooltips.add(Localization.translate("itemtooltip", "thebygonecrestvntip2"));
        return tooltips;
    }
}
