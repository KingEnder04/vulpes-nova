package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class ArchbishopCowlVNActiveBuff extends SealBuffBaseVN {
    public ArchbishopCowlVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
    public void setBuffModifiers(ActiveBuff self, float multi) {
    	self.setModifier(BuffModifiers.CRIT_CHANCE, 0.2F * multi);
    	self.setModifier(BuffModifiers.ALL_DAMAGE, 0.5F * multi);
    	self.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 3F * multi);
    	self.setModifier(BuffModifiers.MAX_HEALTH_FLAT, Math.round(50 * multi));
    	self.setModifier(BuffModifiers.ARMOR_FLAT, Math.round(-25 * multi));
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		setBuffModifiers(buff, 1);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		this.setBuffModifiers(ab, getActiveModifier(ab));		
	}
}
