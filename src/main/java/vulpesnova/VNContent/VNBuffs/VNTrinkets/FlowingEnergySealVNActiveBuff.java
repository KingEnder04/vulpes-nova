package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class FlowingEnergySealVNActiveBuff extends SealBuffBaseVN {
    public FlowingEnergySealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
        this.displayName = new LocalMessage("buff", "flowingenergysealvnbuff");
    
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    	 buff.setModifier(BuffModifiers.MANA_REGEN, 2.0F);
         buff.setModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 1.0F);
         buff.setModifier(BuffModifiers.HEALTH_REGEN_FLAT, 1.0F);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		ab.setModifier(BuffModifiers.MANA_REGEN, 2.0F* this.getActiveModifier(ab));
		ab.setModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 1.0F* this.getActiveModifier(ab));
		ab.setModifier(BuffModifiers.HEALTH_REGEN_FLAT, 1.0F* this.getActiveModifier(ab));
	}
    
}
