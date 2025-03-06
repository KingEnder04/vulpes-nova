package vulpesnova.VNContent.VNMobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.SwampBoltProjectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class SphereSorcererMobVN extends HostileMob {

    public static GameTexture texture;
    public static GameDamage damage;
    static {
        damage = new GameDamage(30.0F);
    }

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.4f, "shapeshardsvn", 1, 3)

    );

    public SphereSorcererMobVN() {
        super(200);
        this.setSpeed(20);
        this.setFriction(3.0F);
        this.setKnockbackModifier(3.0F);
        this.setArmor(10);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -41, 28, 48);
    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        MobSpawnLocation location = (new MobSpawnLocation(this, targetX, targetY)).checkMobSpawnLocation();
        if (this.getLevel().isCave) {
            location = location.checkLightThreshold(client);
        } else {
            location = location.checkMaxStaticLightThreshold(10);
        }

        return location.validAndApply();
    }

    @Override
    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerChaserWandererAI<Mob>((Supplier)null, 512, 320, 40000, false, false) {
            public boolean attackTarget(Mob mob, Mob target) {
                return this.shootSimpleProjectile(mob, target, "spherecererproj", damage, 90, 640);
            }
        });
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
    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        // Spawn flesh debris particles
        for (int i = 0; i < 3; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(), texture,
                    GameRandom.globalRandom.nextInt(5), // Randomize between the debris sprites
                    8,
                    32,
                    x, y, 20f, // Position
                    knockbackX, knockbackY // Basically start speed of the particles
            ), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    @Override
    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        // Tile positions are basically level positions divided by 32. getTileX() does this for us etc.
        GameLight light = level.getLightLevel(getTileX(), getTileY());
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 43;

        // A helper method to get the sprite of the current animation/direction of this mob
        int dir = this.getDir();
        Point sprite = getAnimSprite(x, y, dir);

        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(getTileX(), getTileY()).getMobSinkingAmount(this);

        DrawOptions drawOptions = texture.initDraw()
                .sprite(sprite.x, sprite.y, 64)
                .light(light)
                .pos(drawX, drawY);

        list.add(new MobDrawable() {
            @Override
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });

        addShadowDrawables(tileList, x, y, light, camera);
    }

}
