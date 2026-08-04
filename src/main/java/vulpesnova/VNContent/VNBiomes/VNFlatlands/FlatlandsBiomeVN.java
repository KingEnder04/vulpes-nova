package vulpesnova.VNContent.VNBiomes.VNFlatlands;

import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.GameMusic;
import necesse.engine.sound.gameSound.GameSound;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.regionSystem.Region;
import vulpesnova.VulpesNova;

import java.awt.Color;

public class FlatlandsBiomeVN extends Biome {

    public static FishingLootTable cubeSurfaceFish;
    public static MobSpawnTable surfaceMobs;
    public static MobSpawnTable caveMobs;
    public static MobSpawnTable deepSnowCaveMobs;
    public static MobSpawnTable surfaceCritters;
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;
    public static LootItemInterface randomRoyalEggDrop;
    public static LootItemInterface randomIceCrownDrop;

    public FlatlandsBiomeVN() {
        this.setGenerationWeight(0.3f);

    }


    public float getWindModifier(Level level, int tileX, int tileY) {
        return level.isCave ? 0.1F : super.getWindModifier(level, tileX, tileY);
    }

    public Color getWindColor(Level level) {
        return level.getIslandDimension() == -1 ? new Color(177, 24, 255) : super.getWindColor(level);
    }

    protected void loadRainTexture() {
        this.rainTexture = rainTextures.addTexture(GameTexture.fromFile("particles/mystery_drop"));
    }
    public Color getRainColor(Level level, int tileX, int tileY) {
        return new Color(177, 24, 255, 200);
    }

    @Override
    public void tickRainEffect(GameCamera camera, Level level, int tileX, int tileY, float rainAlpha) {
    }

    @Override
    public GameSound getRainSound(Level level) {
        return null;
    }

    @Override
    public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return new FlatlandsSurfaceLevelVN(islandX, islandY, islandSize, worldEntity);
    }

    @Override
    public Level getNewCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new FlatlandsCaveLevelVN(islandX, islandY, dimension, worldEntity);
    }

    @Override
    public Level getNewDeepCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new FlatlandsDeepCaveLevelVN(islandX, islandY, dimension, worldEntity);
    }

    @Override
    public FishingLootTable getFishingLootTable(FishingSpot spot) {
        return !spot.tile.level.isCave ? cubeSurfaceFish : super.getFishingLootTable(spot);
    }

    @Override
    public MobSpawnTable getMobSpawnTable(Level level) {

        if (!level.isCave) {
            return surfaceMobs;

        } else {

            return level.getIslandDimension() == -2 ? deepSnowCaveMobs : caveMobs;
        }

    }

    @Override
    public MobSpawnTable getCritterSpawnTable(Level level) {

        if (level.isCave) {
            return level.getIslandDimension() == -2 ? deepCaveCritters : caveCritters;
        } else {
            return surfaceCritters;
        }

    }

    @Override
    public LootTable getExtraMobDrops(Mob mob) {

        LootTable result = new LootTable();

        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
            result.items.add(new ChanceLootItem(.005F, "portablegearcontactbeaconvn"));
        }

        if(mob.isBoss()) {
            result.items.add(new ChanceLootItem(.1F, "awakeninatorvn"));
        }

        if(mob.getID() == MobRegistry.getMobID("titancubemobvn")) {
            result.items.add(new ChanceLootItem(.3F, "novashardvn"));
        }

        return result;
    }

    @Override
    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        if (level.isCave) {
            return level.getIslandDimension() == -2
                    ? new MusicList(new GameMusic[]{MusicRegistry.SecretsOfTheForest})
                    : new MusicList(new GameMusic[]{MusicRegistry.DepthsOfTheForest});
        } else {
            return level.getWorldEntity().isNight()
                    ? new MusicList(new GameMusic[]{MusicRegistry.getMusic("cubicwoods")})
                    : new MusicList(new GameMusic[]{MusicRegistry.getMusic("cubicwoods")});
        }
    }

    // ---- Continuous-world (post-1.0) surface generation hooks ----
    // These are what actually get called on new, continuous-world saves.
    // getNewSurfaceLevel() above is kept only for old island-model saves;
    // it is not consulted on new worlds.

    @Override
    public int getGenerationTerrainTileID() {
        return VulpesNova.cubeMainLandVNID;
    }

    @Override
    public int getGenerationBeachTileID() {
        return VulpesNova.cubeSandVNID;
    }

    @Override
    public int getGenerationCaveTileID() {
        return TileRegistry.getTileID("cubestonefloorvn");
    }

    @Override
    public int getGenerationCaveRockObjectID() {
        return ObjectRegistry.getObjectID("cuberockvn");
    }

    @Override
    public int getGenerationDeepCaveTileID() {
        return TileRegistry.getTileID("cubedeepstonefloorvn");
    }

    @Override
    public int getGenerationDeepCaveRockObjectID() {
        return ObjectRegistry.getObjectID("cubedeeprockvn");
    }

    @Override
    public void initializeGeneratorStack(BiomeGeneratorStack stack) {
        super.initializeGeneratorStack(stack);
        stack.addRandomSimplexVeinsBranch("flatlandsBlockberry", 2.0f, 0.2f, 1.0f, 0);
        stack.addRandomVeinsBranch("flatlandsIceBlossom", 0.03f, 6, 12, 0.2f, 0, false);
    }

    @Override
    public void generateRegionSurfaceTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionSurfaceTerrain(region, stack, random);
        int landTile = VulpesNova.cubeMainLandVNID;

        stack.startPlace(this, region, random).onlyOnTile(landTile).chance(0.08f).placeObject("cubetreevn");
        stack.startPlace(this, region, random).onlyOnTile(landTile).chance(0.05f).placeObject("snowpile0");
        stack.startPlace(this, region, random).onlyOnTile(landTile).chance(0.05f).placeObject("snowpile1");
        stack.startPlace(this, region, random).onlyOnTile(landTile).chance(0.05f).placeObject("snowpile2");
        stack.startPlace(this, region, random).onlyOnTile(landTile).chance(0.05f).placeObject("snowpile3");
        stack.startPlace(this, region, random).chance(0.001f).placeObject("cubegroundrockvn");
        stack.startPlace(this, region, random).chance(0.002f).placeObject("cubegroundrocksmallvn");

        stack.startPlaceOnVein(this, region, random, "flatlandsBlockberry")
                .onlyOnTile(landTile).chance(0.1f).placeObjectFruitGrower("blockberrybushvn");
        stack.startPlaceOnVein(this, region, random, "flatlandsIceBlossom")
                .onlyOnTile(landTile).chance(0.2f).placeObject("wildiceblossom");

        stack.startPlace(this, region, random).onlyOnTile(landTile).chancePerRegion(0.02f).placeMob("penguin");
    }

    static {
        cubeSurfaceFish = (new FishingLootTable(defaultSurfaceFish))
                .addWater(120, "icefish");

        surfaceMobs = (new MobSpawnTable())
                .add(15, "cubemobvn")
                //.add(3,"pyramidmobvn")
                .add(3,"titancubemobvn")
                .add(7,"spheresorcerermobvn")
                .add(50,"spheresentinelmobvn")
                .add(8,"planewalkermobvn");

        caveMobs = (new MobSpawnTable())
                //.add(100,"spheresorcerermobvn")
                //.add(100,"planewalkermobvn")
                .add(100,"nightmarecubemobvn")
                .add(10,"deadmahmobvn");

        deepSnowCaveMobs = (new MobSpawnTable())
                .add(120, "spheresorcerermobvn")
                .add(70, "planewalkermobvn")
                .add(25, "nightmarecubemobvn")
                .add(50, "cryoflake")
                .add(15, "deadmahmobvn");

        surfaceCritters = (new MobSpawnTable())
                .add(30, "snowhare")
                .add(60, "bluebird")
                .add(20, "bird");

        caveCritters = (new MobSpawnTable())
                .include(Biome.defaultCaveCritters)
                .add(300, "cubaltcavelingvn");

        deepCaveCritters = (new MobSpawnTable())
                .include(Biome.defaultCaveCritters)
                .add(100, "cubaltcavelingvn");


    }
}