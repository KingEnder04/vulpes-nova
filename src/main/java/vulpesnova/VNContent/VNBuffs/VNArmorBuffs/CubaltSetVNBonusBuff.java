package vulpesnova.VNContent.VNBuffs.VNArmorBuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobBeforeHitCalculatedEvent;
import necesse.entity.mobs.MobBeforeHitEvent;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTooltips.ListGameTooltips;

import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import vulpesnova.VulpesNova;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class CubaltSetVNBonusBuff extends SetBonusBuff {

	
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public IntUpgradeValue maxFlatHealth = (new IntUpgradeValue()).setBaseValue(20).setUpgradedValue(1.0F, 30);
    public IntUpgradeValue maxFlatMana = (new IntUpgradeValue()).setBaseValue(100).setUpgradedValue(1.0F, 200);

    public static int REFLECTION_COOLDOWN = 2;
    public static float REFLECTION_CHANCE = 0.2F;
    public static long last_reflection_time = 0;
    public CubaltSetVNBonusBuff() {
    	
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.1F);
       // buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.1F);
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, this.maxFlatHealth.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, this.maxFlatMana.getValue(this.getUpgradeTier(buff)));
    }

    
    @Override
    public void onBeforeHit(ActiveBuff buff, MobBeforeHitEvent event) {
    	Mob target = event.target;
    	
    	Point targetPoint = target.getTilePoint();
    	float lastTimeDiff = (event.target.getLevel().getWorldEntity().getTime() - last_reflection_time) / 1000;
    	float randy = GameRandom.globalRandom.getFloatBetween(0, 1);
    	if(randy <= REFLECTION_CHANCE && lastTimeDiff > REFLECTION_COOLDOWN && Projectile.class.isAssignableFrom(event.attacker.getClass())) {

	    		last_reflection_time = event.target.getLevel().getWorldEntity().getTime();
	    		Projectile pickOne = (Projectile)event.attacker;
	    		int newProjID = ProjectileRegistry.getProjectileID(pickOne.getClass());
	    		Projectile newProjectile = ProjectileRegistry.getProjectile(newProjID,
	    				event.target.getLevel(),
	    				event.target.x,
	    				event.target.y,
	    				event.attacker.getAttackOwner().x,
	    				event.attacker.getAttackOwner().y,
	    				pickOne.speed,
	    				(int) (pickOne.distance*1.25),
	    				pickOne.getDamage(),
	    				event.target);
	    		event.target.getLevel().entityManager.projectiles.add(newProjectile);
	    		event.showDamageTip = false;
	    		event.damage.modDamage(0);
	    		System.out.println("reflect");
	    	
	    	
    	}
	}
    
    public void onBeforeAttacked(ActiveBuff buff, MobBeforeHitEvent event) {
    	
	}

    @Override
    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cubaltsetvntip"));
        return tooltips;
    }
    
    @Override
    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }

}
