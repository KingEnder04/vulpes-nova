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
public class YourGemCollectionVNBuff extends TrinketBuff {
    public YourGemCollectionVNBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 50);
        buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 1.00F);
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 50);
        buff.setModifier(BuffModifiers.RESILIENCE_REGEN_FLAT, 0.50F);
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, 100);
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN_FLAT, 1.00F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.10F);
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.10F);
        buff.setModifier(BuffModifiers.SPEED, 0.25F);
        buff.setModifier(BuffModifiers.DASH_STACKS, 1);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 1);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
        buff.setModifier(BuffModifiers.FISHING_LINES, 1);

        }
    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented) {
            event.target.buffManager.addBuff(new ActiveBuff(VulpesNova.BLEEDING_BUFF_VN, event.target, 6.0F, event.attacker), event.target.isServer());
        }

    }
    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "yourgemcollectionvntip"));
        return tooltips;
    }

}
