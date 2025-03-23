package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class DemonWarriorSealVNActiveBuff extends SealBuffBaseVN {
	
    public DemonWarriorSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
    public void setBuffModifiers(ActiveBuff self, float multi) {
    	self.setModifier(BuffModifiers.ATTACK_SPEED, 0.5F * multi);
    	self.setModifier(BuffModifiers.ALL_DAMAGE, 0.3F * multi);
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.5F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.3F);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		this.setBuffModifiers(ab, getActiveModifier(ab));
	}
}
