package vulpesnova.VNContent.VNProjectiles;

import java.awt.Color;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundEmitter;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundPlayer;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import vulpesnova.VulpesNova;
import necesse.engine.util.GameUtils;

public class CodBlasterProjectile extends BulletProjectile implements SoundEmitter  {
	
	public CodBlasterProjectile() {
		
	}
	
	public CodBlasterProjectile(float x, float y, float targetX, float targetY, float velocity, int range, GameDamage damage, int knockback, Mob owner) {
		this.x = x;
		this.y = y;
		this.setTarget(targetX, targetY);
		this.speed = velocity;
		this.setDamage(damage);
		this.setOwner(owner);
		this.setDistance(range);
		this.knockback = knockback;
		
	}

	public void init() {
		super.init();
		this.givesLight = false;
		this.height = 16.0F;
		this.piercing = 0;
		this.bouncing = 1000;
		
	}
	private SoundPlayer soundeff;
	public void postInit() {
		SoundManager.playSound(VulpesNova.COD_FLOPPIN, SoundEffect.effect(this).volume(0.6F).falloffDistance(600));	
	}

	public Color getParticleColor() {
		return new Color(180, 180, 220);
	}

	public Trail getTrail() {
		//return super.getTrail();
		//return new Trail(this, this.getLevel(), new Color(0, 25, 127), 6.0F, 250, 18.0F);
		return null;
	}
	
	
	public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList,
			OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera,
			PlayerMob perspective) {
		
		
		if (!this.removed()) {
		
			GameLight light = level.getLightLevel(this);
			
			// Define the animation properties
			int totalFrames = 6;   // Total frames in the sprite sheet (assuming 4 frames)
			int frameTime = 250;    // 250ms per frame
			
			// Use GameUtils.getAnim to calculate the current animation frame
			int anim = GameUtils.getAnim(this.getWorldEntity().getTime(), totalFrames, frameTime);
			
			// Get draw positions
			int drawX = camera.getDrawX(this.x) - 16; // Adjusted X position based on sprite width
			int drawY = camera.getDrawY(this.y);
			
			// Create TextureDrawOptions for the animation frame
			final TextureDrawOptions options = this.texture.initDraw()
				.sprite(anim, 0, 32, 32)  // Use the built-in sprite animation functionality
				.light(light)
				.rotate(this.getAngle()+45.0F, 16, 16)
				.pos(drawX, drawY - (int) this.getHeight());
			
			// Add main drawable to the list
			list.add(new EntityDrawable(this) {
				public void draw(TickManager tickManager) {
					options.draw();
				}
			});
			
			// Optional: Add shadow (if applicable)
			TextureDrawOptions shadowOptions = this.shadowTexture.initDraw()
				.sprite(anim, 0, 32, 32)  // Assuming shadow uses the same sprite sheet
				.light(light)
				.rotate(this.getAngle()+45.0F, 16, 16)
				.pos(drawX, drawY);
			
			// Add shadow drawable to the tile list
			tileList.add((tm) -> {
				shadowOptions.draw();
			});
		}
	}	




}