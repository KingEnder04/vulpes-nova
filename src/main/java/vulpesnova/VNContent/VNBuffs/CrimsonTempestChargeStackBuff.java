package vulpesnova.VNContent.VNBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class CrimsonTempestChargeStackBuff extends Buff {
	public CrimsonTempestChargeStackBuff() {
		this.isImportant = true;
		this.canCancel = false;
	}
    
	@Override
	public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
	}
    
	@Override
	public int getStackSize(ActiveBuff buff) {
		return 100;
	}
    
	@Override
	public boolean overridesStackDuration() {
		return true;
	}
      
	@Override
	public boolean showsFirstStackDurationText() {
		return super.showsFirstStackDurationText();
	}
}