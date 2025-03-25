package vulpesnova.VNContent.VNMobs;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import vulpesnova.VNContent.VNProjectiles.SphereSentinelShotVNProjectile;
import vulpesnova.VNContent.VNProjectiles.SpherecererShotVNProjectile;

public class SphereSentinelMobVN extends HostileMob {
    public static GameTexture texture;
    public static GameTexture shadow;
    public static GameTexture sink;

    public static GameDamage damage;
    static {
        damage = new GameDamage(25.0F);
    }

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.4f, "shapeshardsvn", 1, 3)

    );

    public SphereSentinelMobVN() {
        super(200);
        this.setSpeed(0.0F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.0F);
        this.setArmor(10);
        this.attackCooldown = 3000;
        collision = new Rectangle(-32, 0, 64, 64);
        hitBox = new Rectangle(-32, 0, 64, 64);
        selectBox = new Rectangle(-32, -44, 64, 100);
    }

    @Override
	public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<SphereSentinelMobVN>(this, new StationaryPlayerShooterAI<SphereSentinelMobVN>(450) {
            public void shootTarget(SphereSentinelMobVN mob, Mob target) {
            	
                SpherecererShotVNProjectile projectile = new SphereSentinelShotVNProjectile(SphereSentinelMobVN.this.getLevel(),mob.x, mob.y, target.x, target.y, 100.0F, 1500, SphereSentinelMobVN.damage, 50, mob);
                SphereSentinelMobVN.this.attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), false);
                projectile.setTargetPrediction(target);
                projectile.x += Math.signum(SphereSentinelMobVN.this.attackDir.x) * 10.0F;
                projectile.y += SphereSentinelMobVN.this.attackDir.y * 6.0F;
                SphereSentinelMobVN.this.getLevel().entityManager.projectiles.add(projectile);
            }
        });
    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        MobSpawnLocation location = (new MobSpawnLocation(this, targetX, targetY))
        		.checkInLiquid()
        		.checkNotLevelCollides()
        		.checkMaxHostilesAround(1, 200, client);   
        
        if (this.getLevel().isCave) {
            location = location.checkLightThreshold(client);
        } else {
            location = location.checkMaxStaticLightThreshold(10);
        }

        return location.validAndApply();
    }

    @Override
    public void clientTick() {
        super.clientTick();
        if (this.isAttacking) {
            this.getAttackAnimProgress();
        }

    }

    @Override
    public void serverTick() {
        super.serverTick();
        if (this.isAttacking) {
            this.getAttackAnimProgress();
        }

    }

    @Override
    public boolean canBePushed(Mob other) {
        return false;
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        // Spawn flesh debris particles
        for (int i = 0; i < 3; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(), texture,
                    GameRandom.globalRandom.nextInt(5), // Randomize between the debris sprites
                    8,
                    64,
                    x, y, 20f, // Position
                    knockbackX, knockbackY // Basically start speed of the particles
            ), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    @Override
    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 64;
        int drawY = camera.getDrawY(y) - 64;
        float shadowAlpha = 1.0F;
        int shadowSpriteX = 0;
        int shadowSpriteY = 0;
        drawY += getLevel().getTile(getTileX(), getTileY()).getMobSinkingAmount(this);
        boolean mirror = false;
        int spriteY;
        float animProgress;
        if (this.attackDir != null) {
            animProgress = 0.4F;
            if (Math.abs(this.attackDir.x) - Math.abs(this.attackDir.y) <= animProgress) {
                spriteY = this.attackDir.y < 0.0F ? 0 : 2;
                if (this.attackDir.x < 0.0F) {
                    mirror = true;
                }
            } else {
                spriteY = this.attackDir.x < 0.0F ? 3 : 1;
            }
        } else {
            int dir = this.getDir();
            if (dir != 0 && dir != 1) {
                spriteY = 0;
            } else {
                spriteY = 0;
            }
        }

        animProgress = this.getAttackAnimProgress();
        int spriteX;
        if (this.isAttacking) {
            spriteX = 0 + Math.min((int)(animProgress * 0.0F), 0);
        } else {
            spriteX = 0;
        }

        if (inLiquid()) {
            final DrawOptions body = sink.initDraw().sprite(spriteX, spriteY, 128).mirror(mirror, false).light(light).pos(drawX, drawY);
            list.add(new MobDrawable() {
                public void draw(TickManager tickManager) {
                    body.draw();
                }
            });
        } else {
            final DrawOptions body = texture.initDraw().sprite(spriteX, spriteY, 128).mirror(mirror, false).light(light).pos(drawX, drawY);
            list.add(new MobDrawable() {
                public void draw(TickManager tickManager) {
                    body.draw();
                }
            });
        }
        final TextureDrawOptions spheresentinelshadow = shadow.initDraw().sprite(shadowSpriteX, shadowSpriteY, 128).light(light).alpha(shadowAlpha).pos(drawX, drawY);
        tileList.add((tm) -> {
            spheresentinelshadow.draw();
        });
    }

    @Override
    public void showAttack(int x, int y, int seed, boolean showAllDirections) {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.isClient()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(this).pitch(1.2F));
        }

    }

}
