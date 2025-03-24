package vulpesnova.VNContent.VNMobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MobSpawnLocation;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.hostile.FlyingHostileMob;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class GustMobVN extends HostileMob {

    // Loaded in vulpesnova.VulpesNova.initResources()
    public static GameTexture texture;
    public static int SPAWNS_AFTER_X_DAYS = 3;


    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.4f, "cloudsvn", 3, 5)
    );

    // MUST HAVE an empty constructor
    public GustMobVN() {
        super(70);
        this.setSpeed(40.0F);
        this.setFriction(0.5F);
        this.setKnockbackModifier(0.2F);

        this.moveAccuracy = 10;
        this.collision = new Rectangle(-12, -12, 24, 24);
        this.hitBox = new Rectangle(-16, -16, 32, 32);
        this.selectBox = new Rectangle(-18, -40, 36, 54);
    }

    @Override
    public void init() {
        super.init();
        ai = new BehaviourTreeAI<>(this, new CollisionPlayerChaserWandererAI<>(null, 456, new GameDamage(14), 5, 40000));
    }
 	
    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        MobSpawnLocation location = (new MobSpawnLocation(this, targetX, targetY)).checkMobSpawnLocation().checkMaxHostilesAround(2, 15, client);   
        if(server.world.worldEntity.getDay() < (SPAWNS_AFTER_X_DAYS+1)) {
        	return false;        
        }
        if (this.getLevel().isCave) {
            location = location.checkLightThreshold(client);
        } else {
            location = location.checkMaxStaticLightThreshold(10);
        }

        return location.validAndApply();
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
                    GameRandom.globalRandom.nextInt(4), // Randomize between the debris sprites
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
        int bobbing = (int)(GameUtils.getBobbing(level.getWorldEntity().getTime(), 1000) * 5.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 48 + bobbing;

        // A helper method to get the sprite of the current animation/direction of this mob
        int dir = this.getDir();
        Point sprite = getAnimSprite(x, y, dir);

        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(getTileX(), getTileY()).getMobSinkingAmount(this);

        int anim = Math.abs(GameUtils.getAnim(level.getWorldEntity().getTime(), 4, 1000) - 3);
        DrawOptions body = texture.initDraw().sprite(0, anim, 64).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light).pos(drawX, drawY);
        int minLight = 100;
        DrawOptions eyes = texture.initDraw().sprite(1, anim, 64).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light.minLevelCopy((float)minLight)).pos(drawX, drawY);
        this.addShadowDrawables(topList, x, y, light, camera);
        topList.add((tm) -> {
            body.draw();
            eyes.draw();
        });

        list.add(new MobDrawable() {
            @Override
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
        list.add(new MobDrawable() {
            @Override
            public void draw(TickManager tickManager) {
                eyes.draw();
            }
        });

        addShadowDrawables(tileList, x, y, light, camera);
    }

    @Override
    public int getRockSpeed() {
        // Change the speed at which this mobs animation plays
        return 20;
    }
    
    @Override
    public boolean isLavaImmune() {
        return true;
    }

}
