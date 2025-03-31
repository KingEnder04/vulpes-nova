package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;

public class ProtectorSealVNActiveBuff extends SealBuffBaseVN {
	
    public ProtectorSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		setBuffModifiers(buff, 1.0F);
    }
	
	public void setBuffModifiers(ActiveBuff self, float multi) {
		self.setModifier(BuffModifiers.ALL_DAMAGE, 0.10F * multi);
		self.setModifier(BuffModifiers.CRIT_CHANCE, 0.10F * multi);
		self.setModifier(BuffModifiers.CRIT_DAMAGE, 0.10F * multi);
    }
	
	@Override
	public void onUpdate(ActiveBuff ab) {
		this.setBuffModifiers(ab, getActiveModifier(ab));
	}
}
