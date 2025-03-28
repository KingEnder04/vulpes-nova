package vulpesnova.VNContent.VNMisc;

import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.PresetSet;
import necesse.level.maps.presets.set.WallSet;

import static necesse.level.maps.presets.set.WallSet.loadByStringID;

public class VNChestRoomSet {

    public VNChestRoomSet() {
    }
    public static ChestRoomSet factory;
    static {
        factory = new ChestRoomSet("gearfactoryfloorvn", "stonepressureplate", WallSet.obsidian, "gearstorageboxvn", new String[]{"obsidianflametrap"});
    }
}
