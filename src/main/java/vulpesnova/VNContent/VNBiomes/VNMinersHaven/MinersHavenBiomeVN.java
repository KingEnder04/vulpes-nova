package vulpesnova.VNContent.VNBiomes.VNMinersHaven;

import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.entity.mobs.Mob;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.regionSystem.Region;

public class MinersHavenBiomeVN extends Biome {
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;
    public static FishingLootTable forestSurfaceFish;
    public static LootItemInterface randomPortalDrop;
    public static LootItemInterface randomShadowGateDrop;

    public MinersHavenBiomeVN() {
        this.setGenerationWeight(0.15f);
    }
    
	@Override
    public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return new MinersHavenSurfaceLevelVN(islandX, islandY, islandSize, worldEntity);
    }
    
	@Override
    public Level getNewCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new MinersHavenCaveLevelVN(islandX, islandY, dimension, worldEntity);
    }
    
	@Override
    public Level getNewDeepCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new MinersHavenDeepCaveLevelVN(islandX, islandY, dimension, worldEntity);
    }
    
	@Override
    public MobSpawnTable getCritterSpawnTable(Level level) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 ? deepCaveCritters : caveCritters;
        } else {
            return super.getCritterSpawnTable(level);
        }
    }
    
	@Override
    public FishingLootTable getFishingLootTable(FishingSpot spot) {
        return !spot.tile.level.isCave ? forestSurfaceFish : super.getFishingLootTable(spot);
    }
    
	@Override
    public LootTable getExtraMobDrops(Mob mob) {
        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
            if (mob.getLevel().getIslandDimension() == -1) {
                return new LootTable(new LootItemInterface[]{randomPortalDrop, super.getExtraMobDrops(mob)});
            }

            if (mob.getLevel().getIslandDimension() == -2) {
                return new LootTable(new LootItemInterface[]{randomShadowGateDrop, super.getExtraMobDrops(mob)});
            }
        }

        return super.getExtraMobDrops(mob);
    }

    public LootTable getExtraBiomeMobDrops(LevelIdentifier levelIdentifier) {
        if (levelIdentifier == null) {
            return new LootTable();
        } else if (levelIdentifier.equals(LevelIdentifier.CAVE_IDENTIFIER)) {
            return new LootTable(new LootItemInterface[]{randomPortalDrop});
        } else {
            return levelIdentifier.equals(LevelIdentifier.DEEP_CAVE_IDENTIFIER) ? new LootTable(new LootItemInterface[]{randomShadowGateDrop}) : new LootTable();
        }
    }

    // ---- Continuous-world (post-1.0) surface generation hooks ----
    // These are what actually get called on new, continuous-world saves.
    // getNewSurfaceLevel() above is kept only for old island-model saves;
    // it is not consulted on new worlds.

    @Override
    public int getGenerationTerrainTileID() {
        return TileRegistry.getTileID("rocktile");
    }

    @Override
    public int getGenerationBeachTileID() {
        return TileRegistry.getTileID("rocktile");
    }

    @Override
    public int getGenerationCaveTileID() {
        return TileRegistry.getTileID("rocktile");
    }

    @Override
    public int getGenerationCaveRockObjectID() {
        return ObjectRegistry.getObjectID("rock");
    }

    @Override
    public int getGenerationDeepCaveTileID() {
        return TileRegistry.getTileID("deeprocktile");
    }

    @Override
    public int getGenerationDeepCaveRockObjectID() {
        return ObjectRegistry.getObjectID("deeprock");
    }

    @Override
    public void initializeGeneratorStack(BiomeGeneratorStack stack) {
        super.initializeGeneratorStack(stack);
        stack.addRandomSimplexVeinsBranch("minersHavenMud", 2.0f, 0.35f, 1.0f, 0);
        stack.addRandomSimplexVeinsBranch("minersHavenRockPatch", 2.0f, 0.35f, 1.0f, 0);
        stack.addRandomVeinsBranch("minersHavenIceBlossom", 0.03f, 6, 12, 0.2f, 0, false);
    }

    @Override
    public void generateRegionSurfaceTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionSurfaceTerrain(region, stack, random);
        int landTile = TileRegistry.getTileID("rocktile");
        int mudTile = TileRegistry.mudID;
        int rockTile = TileRegistry.rockID;

        stack.startPlace(this, region, random).chance(0.002f).placeObject("surfacerock");
        stack.startPlace(this, region, random).chance(0.001f).placeObject("surfacerocksmall");

        stack.startPlaceOnVein(this, region, random, "minersHavenIceBlossom")
            .onlyOnTile(landTile).chance(0.2f).placeObject("wildiceblossom");

        stack.startPlaceOnVein(this, region, random, "minersHavenMud")
            .onlyOnTile(landTile).chance(1.0).placeTile(mudTile);
        stack.startPlaceOnVein(this, region, random, "minersHavenMud")
            .onlyOnTile(mudTile).chance(0.15f).placeObject("thorns");

        stack.startPlaceOnVein(this, region, random, "minersHavenRockPatch")
            .onlyOnTile(landTile).chance(1.0).placeTile(rockTile);
        stack.startPlaceOnVein(this, region, random, "minersHavenRockPatch")
            .onlyOnTile(rockTile).chance(0.85f).placeObject("rock");

        stack.startPlace(this, region, random).onlyOnTile(landTile).chancePerRegion(0.02f).placeMob("penguin");
    }

    static {
        caveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(100, "stonecaveling");
        deepCaveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(100, "deepstonecaveling");
        forestSurfaceFish = (new FishingLootTable(defaultSurfaceFish)).addWater(120, "furfish");
        randomPortalDrop = new LootItemList(new LootItemInterface[]{new ChanceLootItem(0.01F, "mysteriousportal")});
        randomShadowGateDrop = new LootItemList(new LootItemInterface[]{new ChanceLootItem(0.004F, "shadowgate")});
    }
}