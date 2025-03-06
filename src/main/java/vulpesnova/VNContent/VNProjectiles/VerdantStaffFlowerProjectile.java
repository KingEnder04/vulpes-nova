package vulpesnova.VNContent.VNProjectiles;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

public class VerdantStaffFlowerProjectile extends Projectile {
    private long spawnTime;
    private float startSpeed;

    public VerdantStaffFlowerProjectile() {
    	super();
    	this.init();
    }

    public VerdantStaffFlowerProjectile(Level level, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
    	this();
    	this.setLevel(level);
    	this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
    }
    
    @Override
    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextFloat(this.startSpeed);
    }
    
    @Override
    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.startSpeed = reader.getNextFloat();
    }
    
    @Override
    public void init() {
        super.init();
        this.height = 18.0F;
        this.startSpeed = this.speed;
        this.setWidth(10.0F);
        this.spawnTime = this.getLevel().getWorldEntity().getTime();
    }
    
    @Override
    public void onMoveTick(Point2D.Float startPos, double movedDist) {
        super.onMoveTick(startPos, movedDist);
        float perc = Math.abs(GameMath.limit(this.traveledDistance / (float)this.distance, 0.0F, 1.0F) - 1.0F);
        this.speed = Math.max(10.0F, perc * this.startSpeed);
    }
    
    @Override
    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        super.doHitLogic(mob, object, x, y);
        if (this.isServer()) {
            byte projectiles;
            if (mob == null) {
                projectiles = 0;
            } else {
                projectiles = 12;
            }

            float startX = x - this.dx * 2.0F;
            float startY = y - this.dy * 2.0F;
            float angle = (float)GameRandom.globalRandom.nextInt(360);

            for(int i = 0; i < projectiles; ++i) {
                Point2D.Float dir = GameMath.getAngleDir(angle + (float)i * 360.0F / (float)projectiles);
                VerdantStaffPetalProjectile projectile = new VerdantStaffPetalProjectile(this.getLevel(), this.getOwner(), startX, startY, startX + dir.x * 100.0F, startY + dir.y * 100.0F, this.startSpeed, this.distance * 100, this.getDamage().modFinalMultiplier(0.66F), this.knockback);
                if (this.modifier != null) {
                    this.modifier.initChildProjectile(projectile, 1.0F, projectiles / 2);
                }

                if (mob != null) {
                    projectile.startHitCooldown(mob);
                }

                this.getLevel().entityManager.projectiles.add(projectile);
            }

        }
    }
    
    @Override
    public Color getParticleColor() {
        return new Color(232, 47, 69);
    }
    
    @Override
    public Trail getTrail() {
        return new Trail(this, this.getLevel(), this.getParticleColor(), 12.0F, 200, this.getHeight());
    }
    
    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            TextureDrawOptions shadowOptions = this.shadowTexture.initDraw().light(light).rotate(this.getAngle(), this.shadowTexture.getWidth() / 2, this.shadowTexture.getHeight() / 2).pos(drawX, drawY);
            tileList.add((tm) -> {
                shadowOptions.draw();
            });
        }
    }
    
    @Override
    public float getAngle() {
        return (float)(this.getWorldEntity().getTime() - this.spawnTime) / 2.0F;
    }
}
