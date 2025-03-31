package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import java.util.ArrayList;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;

public abstract class AOETrinketBuff extends TrinketBuff implements BuffAbility {
	
	protected int aoeRange;
	protected boolean teamOnly;
	public AOETrinketBuff(int aoeRange, boolean teamOnly) {
		this.aoeRange = aoeRange;
		this.teamOnly = teamOnly;
	}
	public abstract void runAbilityFor(PlayerMob player, ActiveBuff buff, Packet content);
	
	@Override
    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        return !player.buffManager.hasBuff(BuffRegistry.getBuffID(this.getStringID()));
    }
    
	@Override
    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
       
		ArrayList<PlayerMob> playersInRange = player.getLevel()
				.entityManager.players.getInRegionRangeByTile(player.getTileX(), player.getTileY(), aoeRange);
		
		for(PlayerMob p : playersInRange) {
			if(this.canRunAbility(p, buff, content)) {
				runAbilityFor(p, buff, content);
			}
		}
    }
}
