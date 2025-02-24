package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class ProtectorSealVNActiveBuff extends Buff {
    public ProtectorSealVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.10F);
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.10F);
        buff.setModifier(BuffModifiers.CRIT_DAMAGE, 0.10F);
    }
}
