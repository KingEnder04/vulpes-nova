package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;

public class FlowingEnergySealVNActiveBuff extends SealBuffBaseVN {
    public FlowingEnergySealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		setBuffModifiers(buff, 1.0F);
    }
	
    public void setBuffModifiers(ActiveBuff self, float multi) {
    	self.setModifier(BuffModifiers.MANA_REGEN, 2.0F * multi);
    	self.setModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 1.0F * multi);
    	self.setModifier(BuffModifiers.HEALTH_REGEN_FLAT, 1.0F * multi);
    }
	   
	@Override
	public void onUpdate(ActiveBuff ab) {
		this.setBuffModifiers(ab, getActiveModifier(ab));
	}
}
