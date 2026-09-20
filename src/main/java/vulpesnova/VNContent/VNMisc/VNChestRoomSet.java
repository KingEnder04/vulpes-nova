package vulpesnova.VNContent.VNMisc;

import necesse.engine.registries.BiomeRegistry;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.ColumnSet;
import necesse.level.maps.presets.set.PresetSet;
import necesse.level.maps.presets.set.WallSet;
import vulpesnova.VulpesNova;

public class VNChestRoomSet extends PresetSet<ChestRoomSet> {

    //public static final VNChestRoomSet factory;

    static {
        //factory = (ChestRoomSet)((new ChestRoomSet("gearfactoryfloorvn", "stonepressureplate", WallSet.obsidian, ColumnSet.obsidian, "gearstorageboxvn", new String[]{"obsidianflametrap", "obsidianarrowtrap"})).surface(new Biome[]{VulpesNova.FLATLANDS})).cave(new Biome[]{VulpesNova.FLATLANDS});
        //factory = (ChestRoomSet)((ChestRoomSet)((ChestRoomSet)((ChestRoomSet)(new ChestRoomSet("stonefloor", "stonepressureplate", WallSet.wood, ColumnSet.wood, "storagebox", new String[]{"woodarrowtrap"})).surface(new Biome[]{BiomeRegistry.FOREST, BiomeRegistry.PLAINS})).cave(new Biome[]{BiomeRegistry.FOREST, BiomeRegistry.PLAINS})).deepCave(new Biome[]{BiomeRegistry.FOREST, BiomeRegistry.PLAINS})).incursion();
    }
}

