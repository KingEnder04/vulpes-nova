package vulpesnova.VNContent.VNBiomes.VNFlatlands;

import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.*;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.generationModules.CaveGeneration;
import necesse.level.maps.generationModules.CellAutomaton;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.PresetGeneration;
import necesse.level.maps.presets.AncientVultureArenaPreset;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetUtils;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.caveRooms.CaveRuins;
import necesse.level.maps.presets.modularPresets.abandonedMinePreset.AbandonedMinePreset;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.FurnitureSet;
import necesse.level.maps.presets.set.WallSet;
import vulpesnova.VNContent.GEARBossArenaPreset;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FlatlandsDeepCaveLevelVN extends FlatlandsCaveLevelVN {
    public FlatlandsDeepCaveLevelVN(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public FlatlandsDeepCaveLevelVN(int islandX, int islandY, int dimension, WorldEntity worldEntity) {
        super(new LevelIdentifier(islandX, islandY, dimension), 300, 300, worldEntity);
        this.isCave = true;
        this.generateLevel();
    }
    
	@Override
    public void generateLevel() {
        int deepRockTile = TileRegistry.getTileID("cubedeepstonefloorvn");
        CaveGeneration cg = new CaveGeneration(this, "cubedeepstonefloorvn", "cubedeeprockvn");
        GameEvents.triggerEvent(new GenerateCaveLayoutEvent(this, cg), (e) -> {
            cg.generateLevel(0.44F, 4, 3, 6);
        });
        GameEvents.triggerEvent(new GeneratedCaveLayoutEvent(this, cg));
        GameEvents.triggerEvent(new GenerateCaveMiniBiomesEvent(this, cg), (e) -> {
            GenerationTools.generateRandomObjectVeinsOnTile(this, cg.random, 0.2F, 4, 8, deepRockTile, ObjectRegistry.getObjectID("wildcaveglow"), 0.2F, false);
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.07F, 2, 7.0F, 20.0F, 3.0F, 8.0F, TileRegistry.getTileID("lavatile"), 1.0F, true);
            this.liquidManager.calculateShores();
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("cubedeepgroundrockvn"), 0.005F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("cubedeepgroundrocksmallvn"), 0.01F);
        });
        GameEvents.triggerEvent(new GeneratedCaveMiniBiomesEvent(this, cg));
        GameEvents.triggerEvent(new GenerateCaveOresEvent(this, cg), (e) -> {
            cg.generateOreVeins(0.05F, 3, 6, ObjectRegistry.getObjectID("copperoredeeprock"));
            cg.generateOreVeins(0.25F, 3, 6, ObjectRegistry.getObjectID("ironoredeeprock"));
            cg.generateOreVeins(0.15F, 3, 6, ObjectRegistry.getObjectID("goldoredeeprock"));
            cg.generateOreVeins(0.25F, 5, 10, ObjectRegistry.getObjectID("obsidianrock"));
            cg.generateOreVeins(0.2F, 3, 6, ObjectRegistry.getObjectID("tungstenoredeeprock"));
            cg.generateOreVeins(0.05F, 3, 6, ObjectRegistry.getObjectID("lifequartzdeeprock"));
        });
        GameEvents.triggerEvent(new GeneratedCaveOresEvent(this, cg));
        GameObject crystalClusterSmall = ObjectRegistry.getObject("rubyclustersmall");
        GenerationTools.generateRandomSmoothVeinsL(this, cg.random, 0.005F, 4, 4.0F, 7.0F, 4.0F, 6.0F, (lg) -> {
            CellAutomaton ca = lg.doCellularAutomaton(cg.random);
            ca.streamAliveOrdered().forEachOrdered((tile) -> {
                cg.addIllegalCrateTile(tile.x, tile.y);
                this.setTile(tile.x, tile.y, TileRegistry.getTileID("rubygravel"));
                this.setObject(tile.x, tile.y, 0);
            });
            ca.streamAliveOrdered().forEachOrdered((tile) -> {
                if (this.getObjectID(tile.x, tile.y) == 0 && this.getObjectID(tile.x - 1, tile.y) == 0 && this.getObjectID(tile.x + 1, tile.y) == 0 && this.getObjectID(tile.x, tile.y - 1) == 0 && this.getObjectID(tile.x, tile.y + 1) == 0 && cg.random.getChance(0.08F)) {
                    int rotation = cg.random.nextInt(4);
                    Point[] clearPoints = new Point[]{new Point(-1, -1), new Point(1, -1)};
                    if (this.getRelativeAnd(tile.x, tile.y, PresetUtils.getRotatedPoints(0, 0, rotation, clearPoints), (tileX, tileY) -> {
                        return ca.isAlive(tileX, tileY) && this.getObjectID(tileX, tileY) == 0;
                    })) {
                        ObjectRegistry.getObject(ObjectRegistry.getObjectID("rubycluster")).placeObject(this, tile.x, tile.y, rotation, false);
                    }
                }

                if (cg.random.getChance(0.3F) && crystalClusterSmall.canPlace(this, tile.x, tile.y, 0, false) == null) {
                    crystalClusterSmall.placeObject(this, tile.x, tile.y, 0, false);
                }

            });
        });
        PresetGeneration presets = new PresetGeneration(this);
        GameEvents.triggerEvent(new GenerateCaveStructuresEvent(this, cg, presets), (e) -> {
            int abandonedMineCount = cg.random.getIntBetween(2, 3);

          
            Preset arena = new GEARBossArenaPreset();
            presets.findRandomValidPositionAndApply(cg.random, 5, arena, 10, false, false);
            

            for(int i = 0; i < abandonedMineCount; ++i) {
                Rectangle abandonedMineRec = AbandonedMinePreset.generateAbandonedMineOnLevel(this, cg.random, presets.getOccupiedSpace());
                if (abandonedMineRec != null) {
                    presets.addOccupiedSpace(abandonedMineRec);
                }
            }

            AtomicInteger chestRoomRotation = new AtomicInteger();
            int chestRoomAmount = cg.random.getIntBetween(13, 18);

            for(int ix = 0; ix < chestRoomAmount; ++ix) {
                Preset chestRoom = new RandomCaveChestRoom(cg.random, LootTablePresets.deepCaveChest, chestRoomRotation, new ChestRoomSet[]{ChestRoomSet.deepStone, ChestRoomSet.obsidian});
                chestRoom.replaceTile(TileRegistry.deepStoneFloorID, (Integer)cg.random.getOneOf(new Integer[]{TileRegistry.deepStoneFloorID, TileRegistry.deepStoneBrickFloorID}));
                presets.findRandomValidPositionAndApply(cg.random, 5, chestRoom, 10, true, true);
            }

            AtomicInteger caveRuinsRotation = new AtomicInteger();
            int caveRuinsCount = cg.random.getIntBetween(25, 35);

            for(int ixx = 0; ixx < caveRuinsCount; ++ixx) {
                WallSet wallSet = (WallSet)cg.random.getOneOf(new WallSet[]{WallSet.deepStone, WallSet.obsidian});
                FurnitureSet furnitureSet = (FurnitureSet)cg.random.getOneOf(new FurnitureSet[]{FurnitureSet.oak, FurnitureSet.spruce});
                String floorStringID = (String)cg.random.getOneOf(new String[]{"deepstonefloor", "deepstonebrickfloor"});
                Preset room = ((CaveRuins.CaveRuinGetter)cg.random.getOneOf(CaveRuins.caveRuinGetters)).get(cg.random, wallSet, furnitureSet, floorStringID, LootTablePresets.basicDeepCaveRuinsChest, caveRuinsRotation);
                presets.findRandomValidPositionAndApply(cg.random, 5, room, 10, true, true);
            }

            cg.generateRandomCrates(0.03F, new int[]{ObjectRegistry.getObjectID("crate")});
        });
        GameEvents.triggerEvent(new GeneratedCaveStructuresEvent(this, cg, presets));
        GenerationTools.checkValid(this);
    }
    
	@Override
    public LootTable getCrateLootTable() {
        return LootTablePresets.basicDeepCrate;
    }
    
	@Override
    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "deepcave", "biome", this.biome.getLocalization());
    }
}
