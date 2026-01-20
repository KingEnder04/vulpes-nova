package vulpesnova.VNContent.VNBiomes.VNMinersHaven;

import necesse.engine.AbstractMusicList;
import necesse.engine.GameTileRange;
import necesse.engine.MusicList;
import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.GameMusic;
import necesse.engine.sound.SoundSettings;
import necesse.engine.sound.SoundSettingsRegistry;
import necesse.engine.sound.gameSound.GameSound;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TicketSystemList;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.engine.world.biomeGenerator.GeneratorPlaceFactory;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.caveRooms.CaveRuins;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.FurnitureSet;
import necesse.level.maps.presets.set.VillageSet;
import necesse.level.maps.presets.set.WallSet;
import necesse.level.maps.regionSystem.Region;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsDeepCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsSurfaceLevelVN;
import vulpesnova.VulpesNova;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MinersHavenBiomeVN extends Biome {
    public static FishingLootTable cubeSurfaceFish;
    public static MobSpawnTable surfaceMobs;
    public static MobSpawnTable caveMobs;
    public static MobSpawnTable deepSnowCaveMobs;
    public static MobSpawnTable surfaceCritters;
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;
    public static LootItemInterface randomRoyalEggDrop;
    public static LootItemInterface randomIceCrownDrop;
    public MinersHavenBiomeVN() {
    }

    public SoundSettings getWindSound(Level level) {
        return SoundSettingsRegistry.windSnow;
    }

    protected void loadRainTexture() {
        this.rainTexture = rainTextures.addTexture(GameTexture.fromFile("snowfall"));
    }

    public Color getRainColor(Level level, int tileX, int tileY) {
        return new Color(255, 255, 255, 200);
    }

    public void tickRainEffect(GameCamera camera, Level level, int tileX, int tileY, float rainAlpha) {
    }

    public GameSound getRainSound(Level level) {
        return null;
    }

    public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return new MinersHavenSurfaceLevelVN(islandX, islandY, islandSize, worldEntity, this);
    }

    public Level getNewCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new MinersHavenCaveLevelVN(islandX, islandY, dimension, worldEntity, this);
    }

    public Level getNewDeepCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new MinersHavenDeepCaveLevelVN(islandX, islandY, dimension, worldEntity, this);
    }

    public FishingLootTable getFishingLootTable(FishingSpot spot) {
        return !spot.tile.level.isCave ? cubeSurfaceFish : super.getFishingLootTable(spot);
    }

    public MobSpawnTable getMobSpawnTable(Level level) {
        if (!level.isCave) {
            return surfaceMobs;
        } else {
            return level.getIdentifier().equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER) ? deepSnowCaveMobs : caveMobs;
        }
    }

    public MobSpawnTable getCritterSpawnTable(Level level) {
        if (level.isCave) {
            return level.getIdentifier().equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER) ? deepCaveCritters : caveCritters;
        } else {
            return surfaceCritters;
        }
    }

    public LootTable getExtraMobDrops(Mob mob) {
        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
            if (mob.getLevel().getIdentifier().equals(LevelIdentifier.CAVE_IDENTIFIER)) {
                return new LootTable(new LootItemInterface[]{randomRoyalEggDrop, super.getExtraMobDrops(mob)});
            }

            if (mob.getLevel().getIdentifier().equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER)) {
                return new LootTable(new LootItemInterface[]{randomIceCrownDrop, super.getExtraMobDrops(mob)});
            }
        }

        return super.getExtraMobDrops(mob);
    }

    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        if (level.isCave) {
            return level.getIdentifier().equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER) ? new MusicList(new GameMusic[]{MusicRegistry.SubzeroSanctum}) : new MusicList(new GameMusic[]{MusicRegistry.GlaciersEmbrace});
        } else {
            return level.getWorldEntity().isNight() ? new MusicList(new GameMusic[]{MusicRegistry.PolarNight}) : new MusicList(new GameMusic[]{MusicRegistry.AuroraTundra});
        }
    }

    public LootTable getExtraBiomeMobDrops(LevelIdentifier levelIdentifier) {
        if (levelIdentifier == null) {
            return new LootTable();
        } else if (levelIdentifier.equals(LevelIdentifier.CAVE_IDENTIFIER)) {
            return new LootTable(new LootItemInterface[]{randomRoyalEggDrop});
        } else {
            return levelIdentifier.equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER) ? new LootTable(new LootItemInterface[]{randomIceCrownDrop}) : new LootTable();
        }
    }

    public GameTile getUnderLiquidTile(Level level, int tileX, int tileY) {
        return TileRegistry.getTile(TileRegistry.rockID);
    }

    public int getBiomeBlendingPriority() {
        return 100;
    }

    public RandomCaveChestRoom getNewCaveChestRoomPreset(GameRandom random, AtomicInteger lootRotation) {
        RandomCaveChestRoom chestRoom = new RandomCaveChestRoom(random, LootTablePresets.snowCaveChest, lootRotation, new ChestRoomSet[]{ChestRoomSet.snowStone, ChestRoomSet.ice, ChestRoomSet.wood});
        chestRoom.replaceTile(TileRegistry.stoneFloorID, (Integer)random.getOneOf(new Integer[]{TileRegistry.stoneFloorID, TileRegistry.stoneBrickFloorID}));
        chestRoom.replaceTile(TileRegistry.snowStoneFloorID, (Integer)random.getOneOf(new Integer[]{TileRegistry.snowStoneFloorID, TileRegistry.snowStoneBrickFloorID}));
        return chestRoom;
    }

    public RandomCaveChestRoom getNewDeepCaveChestRoomPreset(GameRandom random, AtomicInteger lootRotation) {
        RandomCaveChestRoom chestRoom = new RandomCaveChestRoom(random, LootTablePresets.deepSnowCaveChest, lootRotation, new ChestRoomSet[]{ChestRoomSet.deepStone, ChestRoomSet.deepSnowStone});
        chestRoom.replaceTile(TileRegistry.deepStoneFloorID, (Integer)random.getOneOf(new Integer[]{TileRegistry.deepStoneFloorID, TileRegistry.deepStoneBrickFloorID}));
        chestRoom.replaceTile(TileRegistry.deepSnowStoneFloorID, (Integer)random.getOneOf(new Integer[]{TileRegistry.deepSnowStoneFloorID, TileRegistry.deepSnowStoneBrickFloorID}));
        return chestRoom;
    }

    public CaveRuins getNewCaveRuinsPreset(GameRandom random, AtomicInteger lootRotation) {
        WallSet wallSet = (WallSet)random.getOneOf(new WallSet[]{WallSet.snowStone, WallSet.wood});
        FurnitureSet furnitureSet = (FurnitureSet)random.getOneOf(new FurnitureSet[]{FurnitureSet.pine, FurnitureSet.spruce});
        String floorStringID = (String)random.getOneOf(new String[]{"woodfloor", "woodfloor", "snowstonefloor", "snowstonebrickfloor"});
        return ((CaveRuins.CaveRuinGetter)random.getOneOf(CaveRuins.caveRuinGetters)).get(random, wallSet, furnitureSet, floorStringID, LootTablePresets.snowCaveRuinsChest, lootRotation);
    }

    public CaveRuins getNewDeepCaveRuinsPreset(GameRandom random, AtomicInteger lootRotation) {
        WallSet wallSet = (WallSet)random.getOneOf(new WallSet[]{WallSet.deepStone, WallSet.deepSnowStone});
        FurnitureSet furnitureSet = (FurnitureSet)random.getOneOf(new FurnitureSet[]{FurnitureSet.pine, FurnitureSet.spruce});
        String floorStringID = (String)random.getOneOf(new String[]{"deepsnowstonefloor", "deepsnowstonebrickfloor"});
        return ((CaveRuins.CaveRuinGetter)random.getOneOf(CaveRuins.caveRuinGetters)).get(random, wallSet, furnitureSet, floorStringID, LootTablePresets.snowDeepCaveRuinsChest, lootRotation);
    }

    public int getGenerationCaveLavaTileID() {
        return TileRegistry.iceID;
    }

    public int getGenerationDeepCaveLavaTileID() {
        return TileRegistry.deepIceID;
    }

    public int getGenerationBeachTileID() {
        return TileRegistry.iceID;
    }

    public int getGenerationTerrainTileID() {
        return TileRegistry.rockID;
    }

    public int getGenerationCaveTileID() {
        return TileRegistry.rockID;
    }

    public int getGenerationCaveRockObjectID() {
        return ObjectRegistry.rockID;
    }

    public int getGenerationDeepCaveTileID() {
        return TileRegistry.deepRockID;
    }

    public int getGenerationDeepCaveRockObjectID() {
        return ObjectRegistry.deepRockID;
    }

    public void initializeGeneratorStack(BiomeGeneratorStack stack) {
        super.initializeGeneratorStack(stack);
        stack.addRandomSimplexVeinsBranch("minershavenPineTrees", 2.0F, 0.2F, 1.0F, 0);
        stack.addRandomVeinsBranch("minershavenBlueFlowerPatch", 0.05F, 5, 15, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenIceBlossomPatch", 0.05F, 6, 12, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenBlackberries", 0.065F, 8, 10, 0.1F, 0, false);
        stack.addRandomVeinsBranch("minershavenSheep", 0.02F, 8, 12, 0.1F, 0, false);
        stack.addRandomVeinsBranch("minershavenPenguins", 0.02F, 8, 12, 0.1F, 0, false);
        stack.addRandomVeinsBranch("minershavenFrostShards", 0.48F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenCopper", 0.48F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenIron", 0.4F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenGold", 0.24F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenFallingIcicles", 0.8F, 7, 20, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenDeepCopper", 0.08F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenDeepIron", 0.4F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenDeepGold", 0.24F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenDeepTungsten", 0.08F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenDeepLifeQuartz", 0.08F, 3, 6, 0.4F, 2, false);
        stack.addRandomVeinsBranch("minershavenDeepGlacial", 0.27F, 3, 6, 0.4F, 2, false);
    }

    public void generateRegionSurfaceTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionSurfaceTerrain(region, stack, random);
        int snowTile = TileRegistry.snowID;
        stack.startPlaceOnVein(this, region, random, "minershavenPineTrees").onlyOnTile(snowTile).chance(0.07999999821186066).placeObject("pinetree");

        for(int i = 0; i < 4; ++i) {
            stack.startPlace(this, region, random).chance(0.05000000074505806).onlyOnTile(snowTile).placeObject("snowpile" + i);
        }

        stack.startPlace(this, region, random).onlyOnTile(snowTile).chance(0.004999999888241291).placeObject("blueflowerpatch");
        stack.startPlace(this, region, random).chance(0.001500000013038516).placeObject("snowsurfacerock");
        stack.startPlace(this, region, random).chance(0.0024999999441206455).placeObject("snowsurfacerocksmall");
        stack.startPlaceOnVein(this, region, random, "minershavenBlackberries").onlyOnTile(snowTile).placeObjectFruitGrower("blackberrybush");
        stack.startPlaceOnVein(this, region, random, "minershavenBlueFlowerPatch").onlyOnTile(snowTile).chance(0.4000000059604645).placeObject("blueflowerpatch");
        stack.startPlaceOnVein(this, region, random, "minershavenIceBlossomPatch").onlyOnTile(snowTile).chance(0.20000000298023224).placeObject("wildiceblossom");
        TicketSystemList<String> sheepSpawns = (new TicketSystemList()).addObject(100, "sheep").addObject(25, "ram");
        stack.startPlaceOnVein(this, region, random, "minershavenSheep").onlyOnTile(snowTile).placeMob(sheepSpawns);
        stack.startPlaceOnVein(this, region, random, "minershavenPenguins").onlyOnTile(snowTile).placeMob("penguin");
        stack.startPlace(this, region, random).onlyOnTile(snowTile).chancePerRegion(0.019999999552965164).placeMob("polarbear");
    }

    public void generateRegionCaveTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionCaveTerrain(region, stack, random);
        stack.startPlace(this, region, random).chance(0.004999999888241291).placeObject("snowcaverock");
        stack.startPlace(this, region, random).chance(0.009999999776482582).placeObject("snowcaverocksmall");
        stack.startPlace(this, region, random).chance(0.029999999329447746).placeCrates(new String[]{"snowcrate"});
        stack.startPlaceOnVein(this, region, random, "minershavenFrostShards").onlyOnObject(ObjectRegistry.snowRockID).placeObjectForced("frostshardsnow");
        stack.startPlaceOnVein(this, region, random, "minershavenCopper").onlyOnObject(ObjectRegistry.snowRockID).placeObjectForced("copperoresnow");
        stack.startPlaceOnVein(this, region, random, "minershavenIron").onlyOnObject(ObjectRegistry.snowRockID).placeObjectForced("ironoresnow");
        stack.startPlaceOnVein(this, region, random, "minershavenGold").onlyOnObject(ObjectRegistry.snowRockID).placeObjectForced("goldoresnow");
    }

    public void generateRegionDeepCaveTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionDeepCaveTerrain(region, stack, random);
        stack.startPlace(this, region, random).chance(0.004999999888241291).placeObject("deepsnowcaverock");
        stack.startPlace(this, region, random).chance(0.009999999776482582).placeObject("deepsnowcaverocksmall");
        stack.startPlace(this, region, random).chance(0.029999999329447746).placeCrates(new String[]{"snowcrate"});
        stack.startPlaceOnVein(this, region, random, "minershavenFallingIcicles").onlyOnObject(0).chance(0.4000000059604645).placeObject("fallingicicletrigger");
        stack.startPlaceOnVein(this, region, random, "minershavenDeepCopper").onlyOnObject(ObjectRegistry.deepSnowRockID).placeObjectForced("copperoredeepsnowrock");
        stack.startPlaceOnVein(this, region, random, "minershavenDeepIron").onlyOnObject(ObjectRegistry.deepSnowRockID).placeObjectForced("ironoredeepsnowrock");
        stack.startPlaceOnVein(this, region, random, "minershavenDeepGold").onlyOnObject(ObjectRegistry.deepSnowRockID).placeObjectForced("goldoredeepsnowrock");
        stack.startPlaceOnVein(this, region, random, "minershavenDeepTungsten").onlyOnObject(ObjectRegistry.deepSnowRockID).placeObjectForced("tungstenoredeepsnowrock");
        stack.startPlaceOnVein(this, region, random, "minershavenDeepLifeQuartz").onlyOnObject(ObjectRegistry.deepSnowRockID).placeObjectForced("lifequartzdeepsnowrock");
        stack.startPlaceOnVein(this, region, random, "minershavenDeepGlacial").onlyOnObject(ObjectRegistry.deepSnowRockID).placeObjectForced("glacialoredeepsnowrock");
    }

    public Color getDebugBiomeColor() {
        return new Color(223, 244, 255);
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