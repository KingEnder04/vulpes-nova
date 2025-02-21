package vulpesnova.VNContent.VNBuffs.VNArmorBuffs;

import necesse.engine.localization.Localization;
import necesse.engine.modifiers.Modifier;
import necesse.engine.registries.BuffRegistry.Debuffs;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;

import necesse.engine.modifiers.ModifierTooltip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.util.LinkedList;

public class MeatSetBonusBuff extends SetBonusBuff {

    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public MeatSetBonusBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setMaxModifier(BuffModifiers.SLOW, 0.0F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 20);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented) {
            event.target.buffManager.addBuff(new ActiveBuff(Debuffs.SPIDER_VENOM, event.target, 5.0F, event.attacker), event.target.isServer());
        }

    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "meatsetvntip"));
        tooltips.add(Localization.translate("itemtooltip", "spiderset"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).excludeLimits(new Modifier[]{BuffModifiers.SLOW}).buildToStatList(list);
    }

}
