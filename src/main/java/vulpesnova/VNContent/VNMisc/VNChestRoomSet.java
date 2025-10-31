package vulpesnova.VNContent.VNMisc;

import necesse.level.maps.biomes.Biome;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.ColumnSet;
import necesse.level.maps.presets.set.PresetSet;
import necesse.level.maps.presets.set.WallSet;
import vulpesnova.VulpesNova;

public class VNChestRoomSet {

    public VNChestRoomSet() {
    }
    public static ChestRoomSet factory;
    static {
        factory = (ChestRoomSet)(new ChestRoomSet("gearfactoryfloorvn", "stonepressureplate", WallSet.obsidian, ColumnSet.obsidian, "gearstorageboxvn", new String[]{"obsidianflametrap", "obsidianarrowtrap"})).cave(new Biome[]{VulpesNova.FLATLANDS});
    }
}

