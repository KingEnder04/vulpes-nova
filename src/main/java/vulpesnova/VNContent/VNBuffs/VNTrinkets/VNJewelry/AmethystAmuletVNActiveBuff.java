package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNJewelry;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class AmethystAmuletVNActiveBuff extends Buff {
    public AmethystAmuletVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 9999);
    }
}
