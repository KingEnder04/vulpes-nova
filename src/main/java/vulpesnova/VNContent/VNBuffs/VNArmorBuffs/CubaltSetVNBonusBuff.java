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
import vulpesnova.VulpesNova;

import java.util.LinkedList;

public class CubaltSetVNBonusBuff extends SetBonusBuff {

    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public IntUpgradeValue maxFlatHealth = (new IntUpgradeValue()).setBaseValue(20).setUpgradedValue(1.0F, 30);
    public IntUpgradeValue maxFlatMana = (new IntUpgradeValue()).setBaseValue(100).setUpgradedValue(1.0F, 200);

    public CubaltSetVNBonusBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.2F);
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.1F);
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, this.maxFlatHealth.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, this.maxFlatMana.getValue(this.getUpgradeTier(buff)));
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented) {
            event.target.buffManager.addBuff(new ActiveBuff(VulpesNova.COSMIC_FIRE_VN, event.target, 2.0F, event.attacker), event.target.isServer());
        }

    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cubaltsetvn"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }

}
