package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class ArchbishopCowlVNActiveBuff extends SealBuffBaseVN {
    public ArchbishopCowlVNActiveBuff() {
        this.isVisible = true;
        this.isImportant = true;
        this.displayName = new LocalMessage("buff", "archbishopcowlvnbuff");
    }

    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.2F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.5F);
        buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 3F);
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, 50);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, -25);
    }

	@Override
	public void onUpdate(ActiveBuff ab) {
		ab.setModifier(BuffModifiers.CRIT_CHANCE, 0.2F* this.getActiveModifier(ab));
		ab.setModifier(BuffModifiers.ALL_DAMAGE, 0.5F* this.getActiveModifier(ab));
		ab.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 3F* this.getActiveModifier(ab));
		ab.setModifier(BuffModifiers.MAX_HEALTH_FLAT, (int)(50* this.getActiveModifier(ab)));
		ab.setModifier(BuffModifiers.ARMOR_FLAT, (int)(-25 * this.getActiveModifier(ab)));
	}
}
