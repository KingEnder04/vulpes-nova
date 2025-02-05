package vulpesnova.VNContent;

import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.RotationLootItem;

import static necesse.inventory.lootTable.presets.DeepCaveChestLootTable.*;

public class VulpesNovaChestLootTable {
    public static final RotationLootItem flatlandsCaveMainItems = RotationLootItem.presetRotation(new LootItemInterface[]{new LootItem("demonwarriorsealvn"), new LootItem("holypaladinsealvn"), new LootItem("cavedemolishervn"), new LootItem("thebygonecrestvn")});

    public static final LootTable flatlandsCaveChest;
    public VulpesNovaChestLootTable() {

    }

    static {
        flatlandsCaveChest = new LootTable(new LootItemInterface[]{flatlandsCaveMainItems, potions, basicBars, extraItems,new ChanceLootItem(0.5F, "portablegearcontactbeaconvn")});
    }
}
