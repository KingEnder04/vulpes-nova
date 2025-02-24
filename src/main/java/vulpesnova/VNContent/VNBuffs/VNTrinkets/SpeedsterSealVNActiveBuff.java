package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SpeedsterSealVNActiveBuff extends Buff {
    public SpeedsterSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, 1.0F);
    }
}
