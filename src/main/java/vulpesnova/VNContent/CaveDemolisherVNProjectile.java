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
    
	@Override
    public void init() {
        super.init();
        this.stopsRotatingOnStationary = true;
    }
    
	@Override
    public int getFuseTime() {
        return 4000;
    }
    
	@Override
    public float getParticleAngle() {
        return 220.0F;
    }
    
	@Override
    public float getParticleDistance() {
        return 14.0F;
    }
    
	@Override
    public ExplosionEvent getExplosionEvent(float x, float y) {
        int toolTier = (int) Math.max(2, this.getOwnerToolTier() + 1);
        return new BombExplosionEvent(x, y, 400, new GameDamage(400.0F, 1000.0F), true, false, toolTier, this.getOwner());
    }
}
