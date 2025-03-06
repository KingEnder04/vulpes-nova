package vulpesnova.VNContent.VNBuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.engine.localization.message.LocalMessage;

import java.awt.*;

public class WarAxeBleedingBuff extends Buff {
	
	public static float DURATION_REFRESH = 30F;
	public static float DMG_PER_STACK = 3.0F;
	public static int MAX_STACKS = 5;
	public WarAxeBleedingBuff() {
		super();
		this.displayName =  new LocalMessage("buff", "waraxebleed");
		this.canCancel=false;	
	}

	@Override
	public int getStackSize(ActiveBuff buff) {
		return MAX_STACKS;
	}
	
	@Override
	public void onStacksUpdated(ActiveBuff buff, ActiveBuff other) {		
		buff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT, DMG_PER_STACK  * buff.getStacks());
	}
	
	@Override
	public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        Mob owner = buff.owner;
        if (owner.isVisible() && GameRandom.globalRandom.nextInt(2) == 0) {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(231, 46, 46)).height(16.0F);
        }

	}

	@Override
	public void init(ActiveBuff arg0, BuffEventSubscriber arg1) {
		arg0.setModifier(BuffModifiers.POISON_DAMAGE_FLAT, DMG_PER_STACK);
	}
}