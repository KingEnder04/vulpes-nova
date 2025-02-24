package vulpesnova.VNContent.VNProjectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GamePoint3D;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CrimsonTempestVNProjectile extends Projectile {

    private long spawnTime;
    private int maxJumps;
    private int jumpsLeft;
    private Point sourcePoint;
    private int jumpDistanceMax;
    private int jumpAngleMax;
    public CrimsonTempestVNProjectile() {
    	
    }

    public CrimsonTempestVNProjectile(Level level, Point src, float x, float y, float targetX, float targetY, float speed, int distance, int maxJumps, int jumps, int jumpDistanceMax, int jumpAngleMax, GameDamage damage, Mob owner) {
    	this.setLevel(level);
		this.setOwner(owner);
    	this.sourcePoint = src;
    	this.maxJumps = maxJumps;
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDamage(damage);
        this.setDistance(distance);
        this.jumpDistanceMax = jumpDistanceMax;        
        this.jumpAngleMax = jumpAngleMax;        
        this.jumpsLeft = jumps;
    }

    public ArrayList<Mob> getPossibleJumps(Mob src, LevelObjectHit obj) {
    	if (this.sourcePoint == null) return null;
        ArrayList<Mob> returnPossibilities = new ArrayList<>();   
        Point projPos = this.getPositionPoint();
        GamePoint3D posDifSrc = new GamePoint3D( projPos.x - this.sourcePoint.x,  projPos.y - this.sourcePoint.y, 0).normalize();
        double maxDistSquared = this.jumpDistanceMax * this.jumpDistanceMax; // Use squared distance for efficiency

        for (Mob m : this.getLevel().entityManager.mobs) {
        	if(src !=null) {
	        	if(m.getUniqueID()==src.getUniqueID())continue;
	        	if(m.getID()==this.getOwnerID())continue;
        	}
            Point mobPos = m.getPositionPoint();
            double distSquared = (mobPos.x - this.x) * (mobPos.x - this.x) + (mobPos.y - this.y) * (mobPos.y - this.y);
            if (distSquared > maxDistSquared) continue; // Skip mobs too far away

            GamePoint3D posDifMob = new GamePoint3D(mobPos.x - projPos.x, mobPos.y - projPos.y, 0).normalize();

            // Compute dot product and clamp it to prevent acos domain errors
            double dotProduct = posDifMob.x * posDifSrc.x + posDifMob.y * posDifSrc.y;
            dotProduct = Math.max(-1, Math.min(1, dotProduct)); // Clamp to [-1,1] to prevent NaN errors

            double angleBetween = Math.toDegrees(Math.acos(dotProduct)); // Get angle in degrees

            if (angleBetween <= this.jumpAngleMax) {
                returnPossibilities.add(m);               
            }
        }
        return returnPossibilities;
    }

    
    public void init() {
        super.init();
        this.piercing = 0;
        this.bouncing = 0;
        this.height = 16.0F;
        this.setWidth(10.0F, true);
        this.isSolid = true;
        this.givesLight = true;
        this.particleRandomOffset = 10.0F;
    }

  /*  public Color getParticleColor() {
        return new Color(243, 97, 141);
    }*/

    public Trail getTrail() {
    	if (this.sourcePoint== null)return null;
    	Point2D.Float vPos = new Point2D.Float( (float)this.sourcePoint.x, (float)this.sourcePoint.y);
    	
    	TrailVector v = new TrailVector( vPos,
    					(float) Math.cos(this.getAngle()),
    					(float) Math.sin(this.getAngle()),
    					2.0f,
    					this.height);
        return new Trail(v, this.getLevel(), new Color(255,0,0), 1000);//new Trail(this, this.getLevel(), null, this.width, 500, this.height);
    }

    @Override
	public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
    	super.doHitLogic(mob, object, x, y);
    	ArrayList<Mob> targets = getPossibleJumps(mob, object);
    	if (mob != null && targets != null && targets.size() > 0) {
    	
	    	if(this.jumpsLeft>0) {
	    		this.jumpsLeft-=1;
	    		Mob target = targets.get(GameRandom.getIntBetween(GameRandom.globalRandom, 0, targets.size()-1));
	    		
	    		Level lev = this.getLevel();

	    		// Define an interpolation factor (e.g., 20% of the way to the target)
	    		float interpolationFactor = 0.1f;

	    		// Calculate new spawn position slightly toward the target
	    		float spawnX = x + (target.x - x) * interpolationFactor;
	    		float spawnY = y + (target.y - y) * interpolationFactor;
	    		Projectile projectile = new CrimsonTempestVNProjectile(lev,
	    				mob == null ? object.getPoint() : mob.getPositionPoint(),
	    		        spawnX,  // Adjusted spawn X
	    		        spawnY,  // Adjusted spawn Y
	    		        (float) target.x,
	    		        (float) target.y, 
	    		        (float) this.speed,
	    		        this.jumpDistanceMax * 3,
	    		        this.maxJumps,
	    		        this.jumpsLeft,
	    		        this.jumpDistanceMax,
	    		        this.jumpAngleMax,
	    		        this.getDamage(),
	    		        this.getOwner());
	    	
	    		projectile.resetUniqueID(new GameRandom((long) this.getUniqueID()));  		
	    		projectile.setAngle(projectile.getAngleToTarget(target.x, target.y));
	    		lev.entityManager.projectiles.add(projectile);    	
	    		

	    	}
    	}
		
	}
    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            float alpha = this.getFadeAlphaTime(300, 200);
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y - this.getHeight()) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(this.getAngle() - 0.0F, this.texture.getWidth() / 2, this.texture.getHeight() / 2).alpha(alpha).pos(drawX, drawY);
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }

}