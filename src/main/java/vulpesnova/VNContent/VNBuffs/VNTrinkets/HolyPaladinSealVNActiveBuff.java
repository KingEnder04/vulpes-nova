package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class HolyPaladinSealVNActiveBuff extends SealBuffBaseVN {
	

    public HolyPaladinSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
        this.displayName = new LocalMessage("buff", "holypaladinsealvnbuff");
 

    }
    
	@Override	
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		  buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 2F);
	      buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 100);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		ab.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 2F* this.getActiveModifier(ab));
	    ab.setModifier(BuffModifiers.MAX_HEALTH_FLAT, (int)(100 * this.getActiveModifier(ab)));
	}
}
