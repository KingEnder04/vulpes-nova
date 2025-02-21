package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import vulpesnova.VulpesNova;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class DemonWarriorSealVNTrinketBuff extends TrinketBuff implements BuffAbility {
    public DemonWarriorSealVNTrinketBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 2);
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.05F);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        float active = 10.0F;
        float cooldown = 45.0F;
        player.buffManager.addBuff(new ActiveBuff(VulpesNova.DEMON_WARRIOR_VN_COOLDOWN, player, cooldown, (Attacker)null), false);
        player.buffManager.addBuff(new ActiveBuff(VulpesNova.DEMON_WARRIOR_VN_ACTIVE, player, active, (Attacker)null), false);
        player.buffManager.forceUpdateBuffs();
        int minHeight = 0;
        int maxHeight = 40;
        int particles = 20;

        for(int i = 0; i < particles; ++i) {
            float height = (float)minHeight + (float)(maxHeight - minHeight) * (float)i / (float)particles;
            AtomicReference<Float> currentAngle = new AtomicReference(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            player.getLevel().entityManager.addParticle(player.x + GameMath.sin((Float)currentAngle.get()) * distance, player.y + GameMath.cos((Float)currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(new Color(222, 96, 138)).height(height).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = (Float)currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = player.x + GameMath.sin(angle) * distance;
                pos.y = player.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime((int)(active * 1000.0F)).sizeFades(16, 24);
        }

    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        return !buff.owner.buffManager.hasBuff(VulpesNova.DEMON_WARRIOR_VN_COOLDOWN);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "demonwarriorsealvntip1"));
        tooltips.add(Localization.translate("itemtooltip", "demonwarriorsealvntip2"));
        return tooltips;
    }
}
