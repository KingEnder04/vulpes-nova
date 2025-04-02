package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import java.util.ArrayList;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;

public abstract class AOETrinketBuff extends TrinketBuff implements BuffAbility {
	
	protected int aoeRange = 2;
	protected boolean teamOnly;
	protected Buff appliedBuff;
	protected Buff cooldownBuff;
	
	public AOETrinketBuff(int aoeRange, boolean teamOnly) {
		this.aoeRange = aoeRange;
		this.teamOnly = teamOnly;
	}	
	
	public void setBuffs(Buff activeBuff, Buff cooldownBuff) {
		this.appliedBuff = activeBuff;
		this.cooldownBuff = cooldownBuff;
	}
	
	public abstract void runAbilityFor(PlayerMob player, ActiveBuff buff, Packet content);
	
	@Override
    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {		
        return (cooldownBuff != null ? !player.buffManager.hasBuff(cooldownBuff.getID()) : true);
    }
    
	public void applyBuffs(PlayerMob player, float activeDuration, float cooldownDuration) {
		if(cooldownBuff != null) player.buffManager.addBuff(
				 new ActiveBuff(cooldownBuff, player, cooldownDuration, (Attacker)null)
				 , true);
		if(appliedBuff != null) player.buffManager.addBuff(
	    		 new ActiveBuff(appliedBuff, player, activeDuration, (Attacker)null)
	    		 , true);
		if(appliedBuff != null || cooldownBuff != null) player.buffManager.forceUpdateBuffs();
	}
	
	@Override
    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
       
		ArrayList<PlayerMob> playersInRange = player.getLevel()
				.entityManager.players.getInRegionRangeByTile(player.getTileX(), player.getTileY(), aoeRange);
		if(this.canRunAbility(player, buff, content)) { // only if we can use it for the original player
			for(PlayerMob p : playersInRange) {
				if(this.canRunAbility(p, buff, content) 
						&& (this.teamOnly ? p.isSameTeam(player) : true)) {
					runAbilityFor(p, buff, content);
				}
			}
		}
    }
}
