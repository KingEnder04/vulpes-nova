package vulpesnova.VNContent.VNMobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class DeadmahMobVN extends HostileMob {

    // Loaded in vulpesnova.VulpesNova.initResources()
    public static GameTexture texture;
    public static LootTable lootTable = new LootTable(new LootItemInterface[]
            {new ChanceLootItem(1.00F, "halovn")}

    );
    public DeadmahMobVN() {
        super(100);
        this.setSpeed(80.0F);
        this.setFriction(8.0F);
        this.setSwimSpeed(1.0F);
        this.moveAccuracy = 10;
        this.collision = new Rectangle(-8, -6, 16, 12);
        this.hitBox = new Rectangle(-32, -48, 64, 64);
        this.selectBox = new Rectangle(-32, -48, 64, 64);
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<DeadmahMobVN>(this, new EmptyAINode<DeadmahMobVN>());
    }

    @Override
    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        // Spawn flesh debris particles
        for (int i = 0; i < 3; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(), texture,
                    GameRandom.globalRandom.nextInt(4), // Randomize between the debris sprites
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
        int drawX = camera.getDrawX(x) - 63;
        int drawY = camera.getDrawY(y) - 90;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final DrawOptions options = texture.initDraw().sprite(sprite.x, sprite.y, 128).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }
    
    @Override
    public Point getAnimSprite(int x, int y, int dir) {
        return new Point(GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 800), dir);
    }

    @Override
    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        int dir = this.getDir();
        return shadowTexture.initDraw().sprite(dir, 0, res).light(light).pos(drawX, drawY);
    }

    @Override
    public int getBobbing(int x, int y) {
        return 0;
    }
}
