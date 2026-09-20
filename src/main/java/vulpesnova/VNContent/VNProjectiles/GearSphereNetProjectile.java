package vulpesnova.VNContent.VNProjectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import java.awt.geom.Point2D;
import java.util.List;
import java.awt.Color;
import vulpesnova.VNContent.VNMobs.VNBosses.GEARSphereBossMobVN;

public class GearSphereNetProjectile extends Projectile {
    public GearSphereNetProjectile() {
    }

    public GearSphereNetProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
    }

    public GearSphereNetProjectile(Level level, Mob owner, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this(level, owner, owner.x, owner.y, targetX, targetY, speed, distance, damage, knockback);
    }

    public void init() {
        super.init();
        this.spawnTime = this.getWorldEntity().getTime();
        this.isSolid = false;
        this.canHitMobs = false;
        this.trailOffset = 0.0F;
        this.particleRandomOffset = 1.0F;
        this.particleDirOffset = 0.0F;
    }

    public float tickMovement(float delta) {
        float out = super.tickMovement(delta);
        float travelPerc = GameMath.limit(this.traveledDistance / (float)this.distance, 0.0F, 1.0F);
        float travelPercInv = Math.abs(travelPerc - 1.0F);
        float heightF = GameMath.sin(travelPerc * 180.0F);
        this.height = (float)((int)(heightF * 100.0F + 50.0F * travelPercInv));
        return out;
    }

    public Color getParticleColor() {
        return new Color(182, 60, 53);
    }

    public Trail getTrail() {
        return null;
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 2;
    }

    //add slow to the projectile
    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        super.doHitLogic(mob, object, x, y);
        if (this.isServer()) {
            Mob owner = this.getOwner();
            if (owner != null && !owner.removed()) {
                GearSphereNetEvent event = new GearSphereNetEvent(owner, (int)x, (int)y, GameRandom.globalRandom, this.getDamage(), GEARSphereBossMobVN.SPIT_LINGER_SECONDS);
                this.getLevel().entityManager.events.add(event);
            }

        }
    }

    protected void spawnDeathParticles() {
        Color particleColor = this.getParticleColor();
        if (particleColor != null) {
            for(int i = 0; i < 10; ++i) {
                int angle = GameRandom.globalRandom.nextInt(360);
                Point2D.Float dir = GameMath.getAngleDir((float)angle);
                this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.CRITICAL).movesConstant((float)GameRandom.globalRandom.getIntBetween(20, 50) * dir.x, (float)GameRandom.globalRandom.getIntBetween(20, 50) * dir.y).color(this.getParticleColor()).height(this.getHeight());
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this.getTileX(), this.getTileY());
            float shadowAlpha = Math.abs(GameMath.limit(this.height / 250.0F, 0.0F, 1.0F) - 1.0F);
            int shadowX = camera.getDrawX(this.x) - this.shadowTexture.getWidth() / 2;
            int shadowY = camera.getDrawY(this.y) - this.shadowTexture.getHeight() / 2;
            TextureDrawOptions shadowOptions = this.shadowTexture.initDraw().light(light).rotate(this.angle).alpha(shadowAlpha).pos(shadowX, shadowY);
            topList.add((tm) -> {
                shadowOptions.draw();
            });
        }
    }
}
