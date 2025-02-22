package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class RuinedGolemSealVNActiveBuff extends SealBuffBaseVN {
    public RuinedGolemSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
        this.displayName = new LocalMessage("buff","ruinedgolemsealvnbuff");
        		
    }

  
    
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    	 buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 50);
         buff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		ab.setModifier(BuffModifiers.MAX_HEALTH_FLAT, (int)(50 * this.getActiveModifier(ab)));
		ab.setModifier(BuffModifiers.ARMOR_FLAT, 10 * (int)(this.getActiveModifier(ab)));
	}
}
