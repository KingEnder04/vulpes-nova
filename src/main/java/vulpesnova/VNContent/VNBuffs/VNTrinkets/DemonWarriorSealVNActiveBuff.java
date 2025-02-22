package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class DemonWarriorSealVNActiveBuff extends SealBuffBaseVN {
    public DemonWarriorSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
        this.displayName = new LocalMessage("buff", "demonwarriorsealvnbuff");
     
   	    	
    }

    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    	 buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.5F);
         buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.3F);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		ab.setModifier(BuffModifiers.ATTACK_SPEED, 0.5F * this.getActiveModifier(ab));
		ab.setModifier(BuffModifiers.ALL_DAMAGE, 0.3F * this.getActiveModifier(ab));
	}
    
}
