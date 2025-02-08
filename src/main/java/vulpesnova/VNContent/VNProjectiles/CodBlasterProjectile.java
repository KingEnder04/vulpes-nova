package vulpesnova.VNContent.VNProjectiles;

import java.awt.Color;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

public class CodBlasterProjectile extends BulletProjectile {
	public CodBlasterProjectile() {
	}

	public CodBlasterProjectile(float x, float y, float targetX, float targetY, GameDamage damage, Mob owner) {
		this.x = x;
		this.y = y;
		this.setTarget(targetX, targetY);
		this.speed = 10.0F;
		this.setDamage(damage);
		this.setOwner(owner);
		this.setDistance(500);
	}

	public void init() {
		super.init();
		this.givesLight = false;
		this.height = 18.0F;
		this.piercing = 0;
		this.bouncing = 1000;
	}

	public Color getParticleColor() {
		return new Color(0, 25, 12);
	}

	public Trail getTrail() {
		return new Trail(this, this.getLevel(), new Color(0, 25, 127), 6.0F, 250, 18.0F);
	}

	public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList,
			OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera,
			PlayerMob perspective) {
		if (!this.removed()) {
			GameLight light = level.getLightLevel(this);
			int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
			int drawY = camera.getDrawY(this.y);
			final TextureDrawOptions options = this.texture.initDraw().light(light)
					.rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int) this.getHeight());
			list.add(new EntityDrawable(this) {
				public void draw(TickManager tickManager) {
					options.draw();
				}
			});
			this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), 0);
		}
	}

}