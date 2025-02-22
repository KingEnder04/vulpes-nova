package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import java.util.Set;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SpeedsterSealVNActiveBuff extends SealBuffBaseVN {
	
	public SpeedsterSealVNActiveBuff() {
		this.isVisible = true;
	    this.isImportant = true;
	    this.displayName = new LocalMessage("buff","speedstersealvnbuff");
	 
	}
	
    public SpeedsterSealVNActiveBuff(ActiveBuff b) {
       this();
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, 1.0F);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		ab.setModifier(BuffModifiers.SPEED, 1.0F * this.getActiveModifier(ab));
	}
    
}
