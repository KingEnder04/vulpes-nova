package vulpesnova.VNContent.VNBiomes.VNMinersHaven;

import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
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

public class MinersHavenBiomeVN extends Biome {
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;
    public static FishingLootTable forestSurfaceFish;
    public static LootItemInterface randomPortalDrop;
    public static LootItemInterface randomShadowGateDrop;

    public MinersHavenBiomeVN() {
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

    static {
        caveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(100, "stonecaveling");
        deepCaveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(100, "deepstonecaveling");
        forestSurfaceFish = (new FishingLootTable(defaultSurfaceFish)).addWater(120, "furfish");
        randomPortalDrop = new LootItemList(new LootItemInterface[]{new ChanceLootItem(0.01F, "mysteriousportal")});
        randomShadowGateDrop = new LootItemList(new LootItemInterface[]{new ChanceLootItem(0.004F, "shadowgate")});
    }
}
