package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class ArchbishopCowlVNActiveBuff extends Buff {
    public ArchbishopCowlVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.2F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.5F);
        buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 3F);
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 50);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, -25);
    }
}
