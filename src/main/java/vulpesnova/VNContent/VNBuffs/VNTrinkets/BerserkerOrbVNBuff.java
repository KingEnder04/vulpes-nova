package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class BerserkerOrbVNBuff extends TrinketBuff {
    public BerserkerOrbVNBuff() {
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "berserkerorbvntip"));
        tooltips.add(Localization.translate("itemtooltip", "berserkerorbvntip2"));
        return tooltips;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.updateBuff(buff);
        buff.setModifier(BuffModifiers.ARMOR_PEN_FLAT, 5);
    }

    public void serverTick(ActiveBuff buff) {
        this.updateBuff(buff);
    }

    public void clientTick(ActiveBuff buff) {
        this.updateBuff(buff);
    }

    private void updateBuff(ActiveBuff buff) {
        float current = (Float)buff.getModifier(BuffModifiers.ALL_DAMAGE);
        float next = getAttackBonusPerc((float)buff.owner.getHealth() / (float)buff.owner.getMaxHealth(), 0.1F) * 0.8F;
        next = GameMath.toDecimals(next, 2);
        if (current != next) {
            buff.setModifier(BuffModifiers.ALL_DAMAGE, next);
            buff.forceManagerUpdate();
        }

        float current2 = (Float)buff.getModifier(BuffModifiers.HEALTH_REGEN);
        float next2 = getAttackBonusPerc((float)buff.owner.getHealth() / (float)buff.owner.getMaxHealth(), 0.2F) * 0.4F;
        next2 = GameMath.toDecimals(next2, 2);
        if (current2 != next2) {
            buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN, next2);
            buff.forceManagerUpdate();
        }
    }

    public static float getAttackBonusPerc(float healthPercent, float offset) {
        if (offset != 0.0F) {
            healthPercent = (offset - healthPercent) / (offset - 1.0F);
        }

        healthPercent = GameMath.limit(healthPercent, 0.0F, 1.0F);
        return Math.abs(healthPercent - 1.0F);
    }
}

