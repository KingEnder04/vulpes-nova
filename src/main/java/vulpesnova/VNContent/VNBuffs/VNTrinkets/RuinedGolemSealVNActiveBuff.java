package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class RuinedGolemSealVNActiveBuff extends Buff {
    public RuinedGolemSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 50);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
    }
}
