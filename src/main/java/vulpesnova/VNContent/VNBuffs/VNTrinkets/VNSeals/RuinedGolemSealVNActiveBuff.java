package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;

public class RuinedGolemSealVNActiveBuff extends SealBuffBaseVN {
	
    public RuinedGolemSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		setBuffModifiers(buff, 1.0F);
    }
	
	public void setBuffModifiers(ActiveBuff self, float multi) {
		self.setModifier(BuffModifiers.MAX_HEALTH_FLAT, Math.round(50 * multi));
	    self.setModifier(BuffModifiers.ARMOR_FLAT, Math.round(10 * multi));
    }
	
	@Override
	public void onUpdate(ActiveBuff ab) {
		this.setBuffModifiers(ab, getActiveModifier(ab));
	}
}
