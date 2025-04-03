package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import necesse.engine.network.Packet;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.gameTexture.GameTexture;

public abstract class AOETrinketBuff extends TrinketBuff implements BuffAbility {
	
	protected float manaPercentageConsumption = 0.3F;
	protected int aoeRange = 2;
	protected boolean teamOnly;
	protected Buff appliedBuff;
	protected Buff cooldownBuff;
	protected Color glyphColor;
	
	public AOETrinketBuff(Color glyphColor, int aoeRange, boolean teamOnly) {
		this.aoeRange = aoeRange;
		this.teamOnly = teamOnly;		
		this.glyphColor = glyphColor;
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
	
	public boolean hasManaForAbility(PlayerMob player) {
		return (player.getMaxMana() / player.getMana()) >= manaPercentageConsumption;
	}
	
	public float manaCost(PlayerMob player) {
		return player.getMaxMana() * manaPercentageConsumption;
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
	
	protected long buffGlyphDrawDuration = 3000;
	protected int buffGlyphDrawFadeTime = 200;
		
	public void drawBuffGlyph(Mob p) {
		p.getLevel().entityManager.addParticle(
				new SealBuffGlyphParticle(p.getLevel(), p, this.aoeRange, this.glyphColor, this.buffGlyphDrawDuration),
				GType.IMPORTANT_COSMETIC);		
	}
	
	@Override
    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
       
		ArrayList<PlayerMob> playersInRange = player.getLevel()
				.entityManager.players.getInRegionRangeByTile(player.getTileX(), player.getTileY(), aoeRange);
		
		if(this.canRunAbility(player, buff, content) && hasManaForAbility(player)) { // only if we can use it for the original player and the player has mana
			this.drawBuffGlyph(player);
			player.useMana(manaCost(player), player.isServer() ? player.getServerClient() : null); // Use the mana
			for(PlayerMob p : playersInRange) {
				if(this.canRunAbility(p, buff, content)) {
					runAbilityFor(p, buff, content);
				}
			}
		}
    }
}
