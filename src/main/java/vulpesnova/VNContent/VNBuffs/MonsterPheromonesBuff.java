package vulpesnova.VNContent.VNBuffs;

import java.awt.Color;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class MonsterPheromonesBuff extends Buff {
    public MonsterPheromonesBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MOB_SPAWN_CAP, 0.1F);
        buff.setModifier(BuffModifiers.MOB_SPAWN_RATE, 0.1F);
        buff.setMinModifier(BuffModifiers.MOB_SPAWN_LIGHT_THRESHOLD, 140);
        buff.setModifier(BuffModifiers.TARGET_RANGE, 0.5F);
    }
    
	@Override
    public void clientTick(ActiveBuff buff) {
        Mob owner = buff.owner;
        if (owner.getLevel().tickManager().getTotalTicks() % 2L == 0L) {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(133, 59, 225)).height(16.0F);
        }

    }
    
	@Override
    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
    	ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "monsterpheromonesvntip"));
		return tooltips;
	}
    
	@Override
    public void updateLocalDisplayName() {
        this.displayName = new LocalMessage("item", this.getStringID());
    }
}
