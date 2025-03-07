package vulpesnova.VNContent.VNProjectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.ParticleOption;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class AcornProjectile extends Projectile {
	
    public AcornProjectile() {
    }
    
	public AcornProjectile(float x, float y, float targetX, float targetY, float velocity, int range, GameDamage damage, int knockback, Mob owner) {
		this.x = x;
		this.y = y;
		this.setTarget(targetX, targetY);
		this.speed = velocity;
		this.setDamage(damage);
		this.setOwner(owner);
		this.setDistance(range);
		this.knockback = knockback;
	}
	
	@Override
    public void init() {
        super.init();
        this.height = 18.0F;
        this.bouncing = 2;
        this.heightBasedOnDistance = true;
        this.setWidth(8.0F);
    }
	
    @Override
    public void modifySpinningParticle(ParticleOption particle) {
        particle.givesLight(75.0F, 0.5F).lifeTime(1000);
    }
    
    @Override
    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(67, 128, 72), 2.0F, 100, this.getHeight());
    }
    
    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), 0);
        }
    }
    
    @Override
    public void playHitSound(float x, float y) {
        SoundManager.playSound(GameResources.blunthit, SoundEffect.effect(x, y));
    }
}
