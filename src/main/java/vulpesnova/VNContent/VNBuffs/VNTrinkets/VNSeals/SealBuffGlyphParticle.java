package vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

public class SealBuffGlyphParticle extends Particle {
	public static GameTexture buffGlyph;
	public int sprite;
	protected Mob followTarget;
	private float alpha;
	private Color multColor;
	private int range;

	public SealBuffGlyphParticle(Level level, Mob p, int range, Color multColor, long duration) {
		super(level, p.x, p.y, duration);
		this.followTarget = p;
		this.sprite = 0;
		this.alpha = 0.0F;
		this.multColor = multColor;
		this.range = range;
	}

	private float getChargeUpAlpha() {
		return (float) this.getLifeCycleTime() / (float) this.lifeTime;
	}

	public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList,
			OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera,
			PlayerMob perspective) {
		
		if(this.followTarget == null) { 
			this.remove();
			return;
		}
		
		GameLight light = level.getLightLevel(this.getX() / 32, this.getY() / 32);
		
		Rectangle r = this.followTarget.getHitBox();
		int x = this.followTarget.getX();
		int y = this.followTarget.getY();// - (r.height / 2);		
		
		long remainingLifeTime = this.getRemainingLifeTime();
		
		if (remainingLifeTime < 175L) {
			this.alpha = Math.max(0.0F, (float) remainingLifeTime / 175.0F);
		} else if (this.getLifeCycleTime() < 1000L) {
			this.alpha = (float) this.getLifeCycleTime() / 1000.0F;
		} else {
			this.alpha = 1.0F;
		}
		
		float pulseStage = (this.getRemainingLifeTime() / 500) % 2;
		float pulseScale = this.getRemainingLifeTime() % 500;
		if(pulseStage == 0) {
			pulseScale = GameMath.lerp(pulseScale / 500, .95F, 1.05F);
		}
		else {
			pulseScale = GameMath.lerp(pulseScale / 500, 1.05F, 0.95F);
		}
	
		int normalSpriteScale = 192;
		int tilesCovered = normalSpriteScale / 32;
		float rangeSizeModifier = this.range / tilesCovered;
		int newSize = (int)(192 * rangeSizeModifier * pulseScale);
		
		
		int drawX = camera.getDrawX(x);
		int drawY = camera.getDrawY(y);
		
		DrawOptions options = buffGlyph.initDraw()
				.sprite(0, 0, 192)
				.light(light)
				.size(newSize)
				.alpha(this.alpha)
				.colorMult(this.multColor)
				.pos(drawX - newSize / 2, drawY - newSize / 2);
				
		
		tileList.add((tm) -> {
			options.draw();
		});
	}
}