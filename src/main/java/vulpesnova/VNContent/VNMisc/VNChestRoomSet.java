package vulpesnova.VNContent.VNMisc;

import necesse.engine.registries.BiomeRegistry;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.ColumnSet;
import necesse.level.maps.presets.set.PresetSet;
import necesse.level.maps.presets.set.WallSet;
import vulpesnova.VulpesNova;

public class VNChestRoomSet {

    //public static final ChestRoomSet factory;
    static {
        //factory = (ChestRoomSet)((new ChestRoomSet("gearfactoryfloorvn", "stonepressureplate", WallSet.obsidian, ColumnSet.obsidian, "gearstorageboxvn", new String[]{"obsidianflametrap", "obsidianarrowtrap"})).cave(new Biome[]{VulpesNova.FLATLANDS}));
        //factory = (ChestRoomSet)((ChestRoomSet)((ChestRoomSet)((ChestRoomSet)(new ChestRoomSet("stonefloor", "stonepressureplate", WallSet.wood, ColumnSet.wood, "storagebox", new String[]{"woodarrowtrap"})).surface(new Biome[]{BiomeRegistry.FOREST, BiomeRegistry.PLAINS})).cave(new Biome[]{BiomeRegistry.FOREST, BiomeRegistry.PLAINS})).deepCave(new Biome[]{BiomeRegistry.FOREST, BiomeRegistry.PLAINS})).incursion();
    }
}

