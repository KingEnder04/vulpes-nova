package vulpesnova.VNContent.VNBiomes;


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
import necesse.level.maps.Level;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.IslandGeneration;
import necesse.level.maps.presets.RandomRuinsPreset;
import vulpesnova.VulpesNova;

import java.util.function.Consumer;

public class FlatlandsSurfaceLevelVN extends Level {
    public FlatlandsSurfaceLevelVN(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public FlatlandsSurfaceLevelVN(int islandX, int islandY, float islandSize, WorldEntity worldEntity) {
        super(new LevelIdentifier(islandX, islandY, 0), 300, 300, worldEntity);
        this.generateLevel(islandSize);
    }

    public void generateLevel(float islandSize) {
        int size = (int)(islandSize * 100.0F) + 20;
        IslandGeneration ig = new IslandGeneration(this, size);
        int waterTile = TileRegistry.waterID;
        int landTile = VulpesNova.cubeMainLandVNID;
        int sandTile = VulpesNova.cubeSandVNID;
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
            ig.clearTinyIslands(waterTile);
            this.liquidManager.calculateHeights();
        });
        GameEvents.triggerEvent(new GeneratedIslandLayoutEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandFloraEvent(this, islandSize, ig), (e) -> {
            int treeObject = ObjectRegistry.getObjectID("cubetreevn");
            ig.generateCellMapObjects(0.4F, treeObject, landTile, 0.08F);
            ig.generateObjects(ObjectRegistry.getObjectID("snowpile0"), landTile, 0.05F);
            ig.generateObjects(ObjectRegistry.getObjectID("snowpile1"), landTile, 0.05F);
            ig.generateObjects(ObjectRegistry.getObjectID("snowpile2"), landTile, 0.05F);
            ig.generateObjects(ObjectRegistry.getObjectID("snowpile3"), landTile, 0.05F);
            ig.generateObjects(ObjectRegistry.getObjectID("cubesurfacerockvn"), -1, 0.001F, false);
            ig.generateObjects(ObjectRegistry.getObjectID("cubesurfacerocksmallvn"), -1, 0.002F, false);
            ig.generateFruitGrowerVeins("blockberrybushvn", 0.04F, 8, 10, 0.1F, (Consumer)null, new int[]{landTile});
            GenerationTools.generateRandomObjectVeinsOnTile(this, ig.random, 0.03F, 6, 12, landTile, ObjectRegistry.getObjectID("wildiceblossom"), 0.2F, false);
        });
        GameEvents.triggerEvent(new GeneratedIslandFloraEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandStructuresEvent(this, islandSize, ig), (e) -> {
            GenerationTools.spawnRandomPreset(this, (new RandomRuinsPreset(ig.random)).setTiles(new String[]{"cubewoodfloorvn", "cubestonetiledfloorvn"}).setWalls(new String[]{"cubewoodvnwall", "cubestonevnwall"}), false, false, ig.random, false, 40, 3);
        });
        GameEvents.triggerEvent(new GeneratedIslandStructuresEvent(this, islandSize, ig));
        GameEvents.triggerEvent(new GenerateIslandAnimalsEvent(this, islandSize, ig), (e) -> {
            ig.spawnMobHerds("sheep", ig.random.getIntBetween(20, 40), landTile, 2, 6, islandSize);
            ig.spawnMobHerds("penguin", ig.random.getIntBetween(20, 40), landTile, 2, 6, islandSize);
            ig.spawnMobHerds("cow", ig.random.getIntBetween(5, 10), landTile, 1, 1, islandSize);
        });
        GameEvents.triggerEvent(new GeneratedIslandAnimalsEvent(this, islandSize, ig));
        GenerationTools.checkValid(this);
    }

    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "surface", "biome", this.biome.getLocalization());
    }
}
