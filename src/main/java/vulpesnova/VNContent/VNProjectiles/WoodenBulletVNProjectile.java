package vulpesnova.VNContent.VNProjectiles;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameSprite;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;

public class WoodenBulletVNProjectile extends BulletProjectile {

    public WoodenBulletVNProjectile() {
    }

    public WoodenBulletVNProjectile(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
        this();
        this.setLevel(owner.getLevel());
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
    }

    public Trail getTrail() {
        Trail trail = new Trail(this, this.getLevel(), new Color(39, 154, 93), 22.0F, 100, this.height);
        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
        return trail;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        if (this.getLevel().isServerLevel()) {
            if (mob != null) {
                ActiveBuff ab = new ActiveBuff("spidervenom", mob, 10.0F, this.getOwner());
                mob.addBuff(ab, true);
            }

        }
    }
    protected Color getWallHitColor() {
        return new Color(134, 86, 55);
    }

    public void refreshParticleLight() {
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 120.0F, this.lightSaturation);
    }
}
