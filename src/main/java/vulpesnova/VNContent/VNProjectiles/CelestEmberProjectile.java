package vulpesnova.VNContent.VNProjectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class CelestEmberProjectile extends FollowingProjectile {

    // Textures are loaded from resources/projectiles/<projectileStringID>
    // If shadow path is defined when registering the projectile, it is loaded from
    // that path into this projectile shadowTexture field

    // Each projectile must have an empty constructor for the registry to construct them
    public CelestEmberProjectile() {
    }

    // We use this constructor on attack to spawn the projectile with the correct parameters
    public CelestEmberProjectile(Level level, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }
    
    @Override
    public void init() {
        super.init();
        turnSpeed = 1.25f; // This is a homing projectile with a turn speed
        givesLight = false; // The projectile does not give off light
        height = 18; // It's flying 18 pixels above ground
        trailOffset = -18f; // The trail is 18 pixels behind the projectile
        setWidth(16, true); // Extends the hitbox to 16 pixels wide
        piercing = 3; // Pierces 3 mobs before disappearing (total of 4 hits)
        bouncing = 5; // Can bounce 5 times off walls
    }

    @Override
    public Color getParticleColor() {
        // Projectiles sometimes spawn particles. You can return null for no particles.
        return null; //new Color(63, 157, 18);
    }

    @Override
    public Trail getTrail() {
        // Projectiles sometimes spawn trails. You can return null for no trail.
        return new Trail(this, getLevel(), new Color(69, 105, 185), 26, 500, getHeight());
    }
/*
    @Override
    public void updateTarget() {
        // When we have traveled longer than 20 distance, start to find and update the target
        if (traveledDistance > 20) {
            findTarget(
                    m -> m.isHostile, // Filter all non hostile
                    200, 450
            );
        }
    }
*/
    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (removed()) return;
        GameLight light = level.getLightLevel(this);
        int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
        int drawY = camera.getDrawY(y);
        TextureDrawOptions options = texture.initDraw()
                .light(light)
                .rotate(getAngle(), texture.getWidth() / 2, 2) // We rotate the texture around the tip of it
                .pos(drawX, drawY - (int) getHeight());

        list.add(new EntityDrawable(this) {
            @Override
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });

        addShadowDrawables(tileList, drawX, drawY, light, getAngle(), texture.getWidth() / 2, 2);
    }
}
