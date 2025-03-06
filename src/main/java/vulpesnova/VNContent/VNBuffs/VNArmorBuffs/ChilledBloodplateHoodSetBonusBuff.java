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
import java.util.LinkedList;

public class ChilledBloodplateHoodSetBonusBuff extends BloodPlateSetBonusBuff {
    public FloatUpgradeValue projectileVelocity = (new FloatUpgradeValue()).setBaseValue(0.5F).setUpgradedValue(1.0F, 1.0F);
    public FloatUpgradeValue critChance = (new FloatUpgradeValue()).setBaseValue(0.1F).setUpgradedValue(1.0F, 0.2F);



    public ChilledBloodplateHoodSetBonusBuff() {
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.CRIT_CHANCE, this.critChance.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.PROJECTILE_VELOCITY, this.projectileVelocity.getValue(this.getUpgradeTier(buff)));


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