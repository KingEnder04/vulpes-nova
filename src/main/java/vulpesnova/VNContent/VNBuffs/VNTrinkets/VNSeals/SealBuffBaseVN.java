package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.localization.message.GameMessageBuilder;


public abstract class SealBuffBaseVN extends Buff{
	
	public static float LOSS_PER_ACTIVE_SEAL = 0.2F;
	protected static Set<ActiveBuff> activeSeals = ConcurrentHashMap.newKeySet();

	protected float getActiveModifier(ActiveBuff var1) {
		return Math.max(0, 1 - (SealBuffBaseVN.getActiveSealCount(var1.owner) *LOSS_PER_ACTIVE_SEAL));
	}
	
	public static int getActiveSealCount(Mob holder) {
		int found = 0;
		for ( ActiveBuff ab : activeSeals) {
			if (ab.owner.getUniqueID() == holder.getUniqueID()) {
				found += 1;
			}
		}
		return found;
	}
	
	public static boolean activeSealsAdd(ActiveBuff add) {
	
		boolean contains = false;
		for ( ActiveBuff ab : activeSeals) {
			if ((ab.owner.getUniqueID() == add.owner.getUniqueID())&&(ab.buff.getID() == add.buff.getID())) {
				contains=true;
			}
		}
		if(!contains) activeSeals.add(add);
		return contains;		
	}
	
	public static boolean activeSealsRemove(ActiveBuff add) {
		for ( ActiveBuff ab : activeSeals) {
			if ((ab.owner.getUniqueID() == add.owner.getUniqueID())&&(ab.buff.getID() == add.buff.getID())) {
				activeSeals.remove(ab);
				return true;
			}
		}
		return false;
	}
    
	@Override
	public void firstAdd(ActiveBuff buff) {
		activeSealsAdd(buff);
	}
    
	@Override
	public void onRemoved(ActiveBuff buff) {
		activeSealsRemove(buff);
	}
	
	public void updateSealBuffsFor(Mob holder) {
		for ( ActiveBuff ab : activeSeals) {
			if (ab.owner.getUniqueID() == holder.getUniqueID()) {
				ab.buff.onUpdate(ab);
			}
		}
	}
    
	@Override
	public abstract void onUpdate(ActiveBuff ab);
    
	@Override
	public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
		ListGameTooltips tt = new ListGameTooltips(this.getDisplayName());
		int xnt = getActiveSealCount(ab.owner);
		if (xnt > 1) {
			GameMessageBuilder b = new GameMessageBuilder();
			b.append(new LocalMessage("buff", "diminished"));
			b.append(new StaticMessage(String.format(" %d%%", (int)(1 - (this.getActiveModifier(ab)*100)))));
			tt.add(b);
		}
		return tt;
	}
	
}