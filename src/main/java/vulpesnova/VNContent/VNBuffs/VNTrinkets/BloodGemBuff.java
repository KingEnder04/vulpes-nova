package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import vulpesnova.VulpesNova;
public class BloodGemBuff extends TrinketBuff {
    public BloodGemBuff() {
    }
    
	@Override
    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "bloodgemvntip"));
        return tooltips;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }
    
	@Override
    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented) {
            event.target.buffManager.addBuff(new ActiveBuff(VulpesNova.BLEEDING_BUFF_VN, event.target, 6.0F, event.attacker), event.target.isServer());
        }

    }
}