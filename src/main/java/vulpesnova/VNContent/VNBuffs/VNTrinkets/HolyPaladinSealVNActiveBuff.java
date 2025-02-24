package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class HolyPaladinSealVNActiveBuff extends Buff {
    public HolyPaladinSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 2F);
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 100);
    }
}
