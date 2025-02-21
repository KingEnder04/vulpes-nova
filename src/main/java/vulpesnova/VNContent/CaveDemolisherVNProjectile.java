package vulpesnova.VNContent;

import necesse.entity.levelEvent.explosionEvent.BombExplosionEvent;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.projectile.BombProjectile;

public class CaveDemolisherVNProjectile extends BombProjectile {

    public CaveDemolisherVNProjectile() {

    }
    public CaveDemolisherVNProjectile(float x, float y, float targetX, float targetY, int speed, int distance, GameDamage damage, Mob owner) {
        super(x, y, targetX, targetY, speed, distance, damage, owner);
    }

    public void init() {
        super.init();
        this.stopsRotatingOnStationary = true;
    }

    public int getFuseTime() {
        return 4000;
    }

    public float getParticleAngle() {
        return 220.0F;
    }

    public float getParticleDistance() {
        return 14.0F;
    }

    public ExplosionEvent getExplosionEvent(float x, float y) {
        int toolTier = Math.max(2, this.getOwnerToolTier() + 1);
        return new BombExplosionEvent(x, y, 400, new GameDamage(400.0F, 1000.0F), true, toolTier, this.getOwner());
    }
}
