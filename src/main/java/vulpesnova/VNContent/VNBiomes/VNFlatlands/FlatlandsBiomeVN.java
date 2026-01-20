package vulpesnova.VNContent.VNBiomes.VNFlatlands;

import necesse.engine.AbstractMusicList;
import necesse.engine.GameTileRange;
import necesse.engine.MusicList;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.*;
import necesse.engine.sound.GameMusic;
import necesse.engine.sound.SoundSettings;
import necesse.engine.sound.SoundSettingsRegistry;
import necesse.engine.sound.gameSound.GameSound;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TicketSystemList;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.engine.world.biomeGenerator.GeneratorPlaceFactory;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.FrozenMobImmuneBuff;
import necesse.entity.objectEntity.FruitGrowerObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.*;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.snow.SnowCaveLevel;
import necesse.level.maps.biomes.snow.SnowDeepCaveLevel;
import necesse.level.maps.biomes.snow.SnowSurfaceLevel;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.caveRooms.CaveRuins;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.FurnitureSet;
import necesse.level.maps.presets.set.VillageSet;
import necesse.level.maps.presets.set.WallSet;
import necesse.level.maps.regionSystem.Region;
import vulpesnova.VNContent.VulpesNovaChestLootTable;
import vulpesnova.VNContent.VulpesNovaLootTablePresets;
import vulpesnova.VulpesNova;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FlatlandsBiomeVN extends Biome {
    public static MobSpawnTable caveMobs;
    public static MobSpawnTable deepCaveMobs;
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;

    public FlatlandsBiomeVN() {
    }

    public float getWindModifier(Level level, int tileX, int tileY) {
        return level.isCave ? 0.1F : super.getWindModifier(level, tileX, tileY);
    }

    public Color getWindColor(Level level) {
        return new Color(204, 153, 153);
    }

    public MobSpawnTable getCritterSpawnTable(Level level) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 ? deepCaveCritters : caveCritters;
        } else {
            return super.getCritterSpawnTable(level);
        }
    }

    public MobSpawnTable getMobSpawnTable(Level level) {
        if (!level.isCave) {
            return defaultSurfaceMobs;
        } else {
            return level.getIslandDimension() == -2 ? deepCaveMobs : caveMobs;
        }
    }

    public FishingLootTable getFishingLootTable(FishingSpot spot) {
        return !spot.tile.level.isCave ? ForestBiome.forestSurfaceFish : super.getFishingLootTable(spot);
    }

    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 ? new MusicList(MusicRegistry.ForgottenDepths) : new MusicList(MusicRegistry.RunecarvedWalls);
        } else {
            return level.getWorldEntity().isNight() ? new MusicList(MusicRegistry.FieldsOfSerenity) : new MusicList(MusicRegistry.SwampCavern);
        }
    }

    @Override
    public RandomCaveChestRoom getNewCaveChestRoomPreset(GameRandom random, AtomicInteger lootRotation) {
        //this doesn't use the treasure loot table so pls fix
        return new RandomCaveChestRoom(random, VulpesNovaChestLootTable.flatlandsCaveChest, lootRotation, ChestRoomSet.wood, ChestRoomSet.stone);
    }

    @Override
    public RandomCaveChestRoom getNewDeepCaveChestRoomPreset(GameRandom random, AtomicInteger lootRotation) {
        RandomCaveChestRoom chestRoom = new RandomCaveChestRoom(random, VulpesNovaChestLootTable.flatlandsCaveChest, lootRotation, ChestRoomSet.deepStone, ChestRoomSet.obsidian);
        chestRoom.replaceTile(TileRegistry.deepStoneFloorID, random.getOneOf(TileRegistry.deepStoneFloorID, TileRegistry.deepStoneBrickFloorID));
        return chestRoom;
    }

    @Override
    public CaveRuins getNewCaveRuinsPreset(GameRandom random, AtomicInteger lootRotation) {
        WallSet wallSet = random.getOneOf(WallSet.stone, WallSet.wood);
        FurnitureSet furnitureSet = random.getOneOf(FurnitureSet.pine, FurnitureSet.maple);
        String floorStringID = random.getOneOf("scorchedwoodfloortile", "scorchedstonebrickfloortile");
        return random.getOneOf(CaveRuins.caveRuinGetters).get(random, wallSet, furnitureSet, floorStringID, VulpesNovaLootTablePresets.flatlandsCaveChest, lootRotation);
    }

    public CaveRuins getNewDeepCaveRuinsPreset(GameRandom random, AtomicInteger lootRotation) {
        WallSet wallSet = random.getOneOf(WallSet.obsidian, WallSet.deepStone);
        FurnitureSet furnitureSet = random.getOneOf(FurnitureSet.dungeon);
        String floorStringID = random.getOneOf("scorchedwoodfloortile", "scorchedstonebrickfloortile");
        return random.getOneOf(CaveRuins.caveRuinGetters).get(random, wallSet, furnitureSet, floorStringID, VulpesNovaLootTablePresets.flatlandsCaveChest, lootRotation);
    }

    public int getGenerationTerrainTileID() {
        return VulpesNova.cubeMainLandVNID;
    }

    public int getGenerationCaveTileID() {
        return VulpesNova.cubeStoneFloorVNID;
    }

    public int getGenerationCaveRockObjectID() {
        return ObjectRegistry.rockID;
    }

    public int getGenerationDeepCaveTileID() {
        return VulpesNova.cubeDeepStoneFloorVNID;
    }

    public int getGenerationDeepCaveRockObjectID() {
        return ObjectRegistry.deepRockID;
    }

    public void initializeGeneratorStack(BiomeGeneratorStack stack) {
        super.initializeGeneratorStack(stack);
        stack.addRandomSimplexVeinsBranch("cubeTreevn", 2.0F, 0.2F, 1, 0);
        stack.addRandomVeinsBranch("scorchedSheep", 0.035F, 8, 12, 0.1F, 0, false);
        //stack.addRandomVeinsBranch("scorchedWhiteFlowerPatch", 0.05F, 5, 15, 0.4F, 2, false);

        //stack.addRandomVeinsBranch("scorchedCopper", 0.48F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedIron", 0.4F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedGold", 0.24F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedLavanite", 0.18F, 3, 6, 0.4F, 2, false);

        //stack.addRandomVeinsBranch("scorchedDeepCopper", 0.08F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedDeepIron", 0.4F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedDeepGold", 0.24F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedDeepObsidian", 0.4F, 5, 10, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedDeepTungsten", 0.08F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedDeepLifeQuartz", 0.08F, 3, 6, 0.4F, 2, false);
        //stack.addRandomVeinsBranch("scorchedDeepAsh", 0.16F, 2, 3, 0.4F, 2, false);
    }

    public void generateRegionSurfaceTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionSurfaceTerrain(region, stack, random);
        int scorchedTile = VulpesNova.cubeMainLandVNID;

        stack.startPlaceOnVein(this, region, random, "cubeTreevn").onlyOnTile(scorchedTile).chance(0.029999999329447746).placeObject("cubetreevn");

        stack.startPlace(this, region, random).chance(0.001500000013038516).placeObject("surfacerock");
        stack.startPlace(this, region, random).chance(0.0024999999441206455).placeObject("surfacerocksmall");
        //stack.startPlaceOnVein(this, region, random, "scorchedWhiteFlowerPatch").onlyOnTile(scorchedTile).chance(0.4000000059604645).placeObject("whiteflowerpatch");
        TicketSystemList<String> sheepSpawns = (new TicketSystemList<String>()).addObject(100, "sheep").addObject(25, "ram");
        stack.startPlaceOnVein(this, region, random, "scorchedSheep").onlyOnTile(scorchedTile).placeMob(sheepSpawns);
    }

    public void generateRegionCaveTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionCaveTerrain(region, stack, random);
        //stack.startPlace(this, region, random).chance(0.009999999776482582).placeObject("cubecaverocksmall");
        //stack.startPlace(this, region, random).chance(0.029999999329447746).placeCrates("scorchedcrate", "vase");
        //stack.startPlaceOnVein(this, region, random, "scorchedLavanite").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("lavaniteoreobject");
        //stack.startPlaceOnVein(this, region, random, "scorchedCopper").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("copperorescorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedIron").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("ironorescorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedGold").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("goldorescorchedstone");
    }

    public void generateRegionDeepCaveTerrain(Region region, BiomeGeneratorStack stack, GameRandom random) {
        super.generateRegionDeepCaveTerrain(region, stack, random);
        //stack.startPlace(this, region, random).chance(0.009999999776482582).placeObject("scorcheddeepcaverocksmall");
        stack.startPlace(this, region, random).chance(0.029999999329447746).placeCrates("crate");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepCopper").onlyOnObject(VulpesNova).placeObjectForced("copperoredeepscorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepIron").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("ironoredeepscorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepGold").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("goldoredeepscorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepObsidian").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("obsidianrock");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepTungsten").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("tungstenoredeepscorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepLifeQuartz").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("lifequartzdeepscorchedstone");
        //stack.startPlaceOnVein(this, region, random, "scorchedDeepAsh").onlyOnObject(SchObjects.scorchedRockId).placeObjectForced("scorchedashrock");
    }

    public Color getDebugBiomeColor() {
        return new Color(106, 128, 236);
    }

    static {
        caveCritters = (new MobSpawnTable().add(5, "cubaltcavelingvn"));
        deepCaveCritters = (new MobSpawnTable().add(5, "cubaltcavelingvn"));
        caveMobs = (new MobSpawnTable().add(100, "zombie").add(100, "zombiearcher").add(25, "cavemole").add(3, "titancubemobvn"));
        deepCaveMobs = (new MobSpawnTable().add(100, "skeleton").add(100, "skeletonthrower").add(5, "titancubemobvn"));
    }
}

