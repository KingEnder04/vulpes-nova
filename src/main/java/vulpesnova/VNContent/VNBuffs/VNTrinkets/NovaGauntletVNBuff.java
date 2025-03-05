package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import vulpesnova.VulpesNova;

public class NovaGauntletVNBuff extends TrinketBuff {
    public NovaGauntletVNBuff() {
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "novagauntletvntip"));
        return tooltips;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
            buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 30);
            buff.setModifier(BuffModifiers.RESILIENCE_DECAY, -0.90f);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented) {
            event.target.buffManager.addBuff(new ActiveBuff(VulpesNova.COSMIC_FIRE_VN, event.target, 3.0F, event.attacker), event.target.isServer());
        }

    }
}