package vulpesnova.VNContent.VNBiomes.VNMinersHaven;
import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateIslandAnimalsEvent;
import necesse.engine.events.worldGeneration.GenerateIslandFloraEvent;
import necesse.engine.events.worldGeneration.GenerateIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GenerateIslandStructuresEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandAnimalsEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandFloraEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandStructuresEvent;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.level.gameObject.GameObject;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.IslandGeneration;
import necesse.level.maps.presets.RandomRuinsPreset;

public class MinersHavenSurfaceLevelVN extends Level {
    public MinersHavenSurfaceLevelVN(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public MinersHavenSurfaceLevelVN(int islandX, int islandY, float islandSize, WorldEntity worldEntity) {
        super(new LevelIdentifier(islandX, islandY, 0), 300, 300, worldEntity);
        this.generateLevel(islandSize);
    }

    public void generateLevel(float islandSize) {
        int size = (int)(islandSize * 100.0F) + 20;
        IslandGeneration ig = new IslandGeneration(this, size);
        int waterTile = TileRegistry.waterID;
        int lavaTile = TileRegistry.lavaID;
        int landTile = TileRegistry.getTileID("rocktile");
        int sandTile = TileRegistry.getTileID("rocktile");
        GameEvents.triggerEvent(new GenerateIslandLayoutEvent(this, islandSize, ig), (e) -> {
            if (ig.random.getChance(0.05F)) {
                ig.generateSimpleIsland(this.width / 2, this.height / 2, waterTile, landTile, sandTile);
            } else {
                ig.generateShapedIsland(waterTile, landTile, sandTile);
            }

            int rivers = ig.random.getIntBetween(1, 5);

            for(int i = 0; i < rivers && (i <= 0 || !ig.random.getChance(0.4F)); ++i) {
                ig.generateRiver(waterTile, landTile, sandTile);
            }

            ig.generateLakes(0.02F, waterTile, landTile, sandTile);
            ig.generateLakes(0.02F, lavaTile, landTile, sandTile);

            ig.clearTinyIslands(waterTile);
            this.liquidManager.calculateHeights();
        });
        GameEvents.triggerEvent(new GeneratedIslandLayoutEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandFloraEvent(this, islandSize, ig), (e) -> {
            //int treeObject = ObjectRegistry.getObjectID("cubetreevn");
            //ig.generateCellMapObjects(0.4F, treeObject, landTile, 0.08F);

            ig.generateObjects(ObjectRegistry.getObjectID("surfacerock"), -1, 0.002F, false);
            ig.generateObjects(ObjectRegistry.getObjectID("surfacerocksmall"), -1, 0.001F, false);

            GenerationTools.generateRandomObjectVeinsOnTile(this, ig.random, 0.03F, 6, 12, landTile, ObjectRegistry.getObjectID("wildiceblossom"), 0.2F, false);
        });
        GameTile mudTile = TileRegistry.getTile(TileRegistry.mudID);
        GameObject thorns = ObjectRegistry.getObject(ObjectRegistry.getObjectID("thorns"));
        GenerationTools.generateRandomSmoothVeinsC(this, ig.random, 0.05F, 5, 3.0F, 7.0F, 3.0F, 5.0F, (cells) -> {
            boolean addThorns = ig.random.getChance(0.3F);
            cells.forEachTile(this, (level, tileX, tileY) -> {
                mudTile.placeTile(level, tileX, tileY, false);
                if (this.getObjectID(tileX, tileY) == 0) {
                    if (addThorns) {
                        if (ig.random.getChance(0.85F) && thorns.canPlace(level, tileX, tileY, 0, false) == null) {
                            thorns.placeObject(level, tileX, tileY, 0, false);
                        }
                    }
                }

            });
        });
        GameTile rockTile = TileRegistry.getTile(TileRegistry.rockID);
        GameObject rocks = ObjectRegistry.getObject(ObjectRegistry.getObjectID("rock"));
        GenerationTools.generateRandomSmoothVeinsC(this, ig.random, 0.1F, 8, 4.0F, 15.0F, 4.0F, 8.0F, (cells) -> {
            boolean addRocks = ig.random.getChance(0.3F);
            cells.forEachTile(this, (level, tileX, tileY) -> {
                rockTile.placeTile(level, tileX, tileY, false);
                if (this.getObjectID(tileX, tileY) == 0) {
                    if (addRocks) {
                        if (ig.random.getChance(0.85F) && rocks.canPlace(level, tileX, tileY, 0, false) == null) {
                            rocks.placeObject(level, tileX, tileY, 0, false);
                        }
                    }
                }

            });
        });

        GameEvents.triggerEvent(new GeneratedIslandFloraEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandStructuresEvent(this, islandSize, ig), (e) -> {
            GenerationTools.spawnRandomPreset(this, (new RandomRuinsPreset(ig.random)).setTiles(new String[]{"stonefloor"}).setWalls(new String[]{"stonewall"}), false, false, ig.random, false, 40, 3);
        });
        GameEvents.triggerEvent(new GeneratedIslandStructuresEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandAnimalsEvent(this, islandSize, ig), (e) -> {
            ig.spawnMobHerds("penguin", ig.random.getIntBetween(20, 40), landTile, 2, 6, islandSize);
        });
        GameEvents.triggerEvent(new GeneratedIslandAnimalsEvent(this, islandSize, ig));
        GenerationTools.checkValid(this);
    }
    
	@Override
    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "surface", "biome", this.biome.getLocalization());
    }
}