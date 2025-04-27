package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import vulpesnova.VulpesNova;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class RuinedGolemSealVNTrinketBuff extends AOETrinketBuff {
	public static int BUFF_RANGE = 10;
    public RuinedGolemSealVNTrinketBuff() {
    	super(new Color(190,0,190), BUFF_RANGE, true);
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
		this.setBuffs(VulpesNova.RUINED_GOLEM_VN_ACTIVE, VulpesNova.RUINED_GOLEM_VN_COOLDOWN);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 4);
        buff.setModifier(BuffModifiers.SPEED, -0.1f);
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 10);
    }
    
	@Override
    public void runAbilityFor(PlayerMob player, ActiveBuff buff, Packet content) {
        float active = 45.0F;
        float cooldown = 5.0F;
        this.applyBuffs(player, active, cooldown);
        
        int minHeight = 0;
        int maxHeight = 40;
        int particles = 20;

        for(int i = 0; i < particles; ++i) {
            float height = (float)minHeight + (float)(maxHeight - minHeight) * (float)i / (float)particles;
            AtomicReference<Float> currentAngle = new AtomicReference<Float>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            player.getLevel().entityManager.addParticle(player.x + GameMath.sin((Float)currentAngle.get()) * distance, player.y + GameMath.cos((Float)currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(new Color(107, 85, 112)).height(height).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = (Float)currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = player.x + GameMath.sin(angle) * distance;
                pos.y = player.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime((int)(active * 1000.0F)).sizeFades(16, 24);
        }

    }    
	
	@Override
    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "ruinedgolemsealvntip1"));
        tooltips.add(Localization.translate("itemtooltip", "ruinedgolemsealvntip2"));
        return tooltips;
    }

}
