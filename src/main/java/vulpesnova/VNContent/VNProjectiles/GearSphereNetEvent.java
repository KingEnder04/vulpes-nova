package vulpesnova.VNContent.VNProjectiles;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.GroundEffectEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobHitCooldowns;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.level.maps.LevelObjectHit;
import java.awt.Rectangle;
        import java.awt.Shape;

public class GearSphereNetEvent extends GroundEffectEvent {
    public GameDamage damage;
    public float lingerTimeSeconds;
    protected int tickCounter;
    protected MobHitCooldowns hitCooldowns;
    public static Rectangle hitBox = new Rectangle(-25, -20, 50, 40);

    public GearSphereNetEvent() {
    }

    public GearSphereNetEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage, float lingerTimeSeconds) {
        super(owner, x, y, uniqueIDRandom);
        this.damage = damage;
        this.lingerTimeSeconds = lingerTimeSeconds;
    }

    public boolean isNetworkImportant() {
        return true;
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        this.damage.writePacket(writer);
        writer.putNextFloat(this.lingerTimeSeconds);
        writer.putNextInt(this.tickCounter);
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.damage = GameDamage.fromReader(reader);
        this.lingerTimeSeconds = reader.getNextFloat();
        this.tickCounter = reader.getNextInt();
    }

    public void init() {
        super.init();
        this.hitCooldowns = new MobHitCooldowns();
        if (this.isClient()) {
            long msLeft = (long)(this.lingerTimeSeconds * 1000.0F) - (long)this.tickCounter * 50L;
            this.level.entityManager.addParticle(new GearSphereNetParticle(this.level, (float)this.x, (float)this.y, msLeft), true, Particle.GType.CRITICAL);
            SoundManager.playSound(GameResources.blunthit, SoundEffect.effect((float)this.x, (float)this.y).volume(0.4F));
            SoundManager.playSound(GameResources.slimeSplash3, SoundEffect.effect((float)this.x, (float)this.y).falloffDistance(600).volume(0.6F));
        }

    }

    public Shape getHitBox() {
        return new Rectangle(this.x + hitBox.x, this.y + hitBox.y, hitBox.width, hitBox.height);
    }

    public void clientHit(Mob target) {
        this.startTargetHitCooldown(target);
        this.hitCooldowns.startCooldown(target);
    }

    public void serverHit(Mob target, boolean clientSubmitted) {
        if (clientSubmitted || this.hitCooldowns.canHit(target)) {
            target.isServerHit(this.damage, 0.0F, 0.0F, 0.0F, this.owner);
            this.hitCooldowns.startCooldown(target);
        }

    }

    public void hitObject(LevelObjectHit hit) {
        hit.getLevelObject().attackThrough(this.damage, this.owner);
    }

    public boolean canHit(Mob mob) {
        return super.canHit(mob) && this.hitCooldowns.canHit(mob);
    }

    public void clientTick() {
        ++this.tickCounter;
        if ((float)this.tickCounter > 20.0F * this.lingerTimeSeconds) {
            this.over();
        } else {
            super.clientTick();
        }

    }

    public void serverTick() {
        ++this.tickCounter;
        if ((float)this.tickCounter > 20.0F * this.lingerTimeSeconds) {
            this.over();
        } else {
            super.serverTick();
        }

    }
}
