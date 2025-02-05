package vulpesnova.VNContent;

import necesse.inventory.lootTable.LootTable;

public class VulpesNovaLootTablePresets {
    public static LootTable flatlandsCaveChest;

    public VulpesNovaLootTablePresets() {
    }

    static {

        flatlandsCaveChest = VulpesNovaChestLootTable.flatlandsCaveChest;
    }
}
