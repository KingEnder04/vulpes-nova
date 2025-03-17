package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNJewelry;

import necesse.engine.localization.Localization;
import necesse.engine.network.packet.PacketLifelineEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.MobBeforeDamageOverTimeTakenEvent;
import necesse.entity.mobs.MobBeforeHitCalculatedEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;
import vulpesnova.VulpesNova;

public class AmethystAmuletVNTrinketBuff extends TrinketBuff {
    public AmethystAmuletVNTrinketBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 5);
        eventSubscriber.subscribeEvent(MobBeforeDamageOverTimeTakenEvent.class, (event) -> {
            if (this.runLifeLineLogic(buff, event.getExpectedHealth())) {
                event.prevent();
            }
        });
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "amethystamuletvntip"));
        return tooltips;
    }

    public void onBeforeHitCalculated(ActiveBuff buff, MobBeforeHitCalculatedEvent event) {
        super.onBeforeHitCalculated(buff, event);
        if (this.runLifeLineLogic(buff, event.getExpectedHealth())) {
            event.prevent();
        }

    }


    protected boolean runLifeLineLogic(ActiveBuff buff, float expectedHealth) {
        Level level = buff.owner.getLevel();
        if (level.isServer() && !buff.owner.buffManager.hasBuff(VulpesNova.AMETHYST_AMULET_VN_COOLDOWN.getID()) && expectedHealth <= (buff.owner.getMaxHealth() / 4)) {
            buff.owner.buffManager.addBuff(new ActiveBuff(VulpesNova.AMETHYST_AMULET_VN_ACTIVE, buff.owner, 2.0F, (Attacker)null), true);
            buff.owner.buffManager.addBuff(new ActiveBuff(VulpesNova.AMETHYST_AMULET_VN_COOLDOWN, buff.owner, 60.0F, (Attacker)null), true);
            level.getServer().network.sendToClientsWithEntity(new PacketLifelineEvent(buff.owner.getUniqueID()), buff.owner);
            return true;
        } else {
            return false;
        }
    }
}
