package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SpeedsterSealVNActiveBuff extends SealBuffBaseVN {
    public SpeedsterSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		setBuffModifiers(buff, 1.0F);
    }
	
	public void setBuffModifiers(ActiveBuff self, float multi) {
		self.setModifier(BuffModifiers.SPEED, 1.0F * multi);
    }
	
	@Override
	public void onUpdate(ActiveBuff ab) {
		this.setBuffModifiers(ab, getActiveModifier(ab));
	}
}
