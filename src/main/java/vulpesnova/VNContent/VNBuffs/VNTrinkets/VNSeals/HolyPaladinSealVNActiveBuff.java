package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class HolyPaladinSealVNActiveBuff extends Buff {
	
    public HolyPaladinSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		setBuffModifiers(buff, 1.0F);
    }
	
	public void setBuffModifiers(ActiveBuff self, float multi) {
		self.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 2F * multi);
	    self.setModifier(BuffModifiers.MAX_HEALTH_FLAT, Math.round(100 * multi));
    }
	
}
