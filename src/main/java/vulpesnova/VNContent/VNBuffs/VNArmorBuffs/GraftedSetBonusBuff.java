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

import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.util.LinkedList;

public class GraftedSetBonusBuff extends SetBonusBuff {

    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public GraftedSetBonusBuff() {
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.05f);
        buff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.1f);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(this.getUpgradeTier(buff)));
    }
    
	@Override
    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented) {
            event.target.buffManager.addBuff(new ActiveBuff(Debuffs.SPIDER_VENOM, event.target, 5.0F, event.attacker), event.target.isServer());
        }

    }
    
	@Override
    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "graftedsetvntip"));
        return tooltips;
    }

    @Override
    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }

}
