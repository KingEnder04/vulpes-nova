package vulpesnova.VNContent.VNBuffs.VNArmorBuffs;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.MobHealthChangedEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.BloodPlateSetBonusBuff;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.util.LinkedList;

public class ChilledBloodplateHatSetBonusBuff extends BloodPlateSetBonusBuff {
    public IntUpgradeValue maxManaFlat = (new IntUpgradeValue(0,0.1F)).setBaseValue(60).setUpgradedValue(1.0F, 250);
    public FloatUpgradeValue magicCritChance = (new FloatUpgradeValue()).setBaseValue(0.1F).setUpgradedValue(1.0F, 0.2F);



    public ChilledBloodplateHatSetBonusBuff() {
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAGIC_CRIT_CHANCE, this.magicCritChance.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, this.maxManaFlat.getValue(this.getUpgradeTier(buff)));


        super.init(buff, eventSubscriber);
        eventSubscriber.subscribeEvent(MobHealthChangedEvent.class, (event) -> {
            if (event.currentHealth < event.lastHealth && !event.fromUpdatePacket) {
                ActiveBuff activeBuff = new ActiveBuff(BuffRegistry.BLOODPLATE_COWL_ACTIVE, buff.owner, 4.0F, (Attacker)null);
                buff.owner.buffManager.addBuff(activeBuff, false);
            }

        });
    }
    
	@Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
    }
    
	@Override
    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }

}