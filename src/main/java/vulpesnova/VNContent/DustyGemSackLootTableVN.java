package vulpesnova.VNContent;

import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;

public class DustyGemSackLootTableVN extends LootTable {
    public static final OneOfLootItems gems = new OneOfLootItems(new LootItemInterface[]{new LootItem("bloodgemvn"),
                    new LootItem("hookgemvn"),
                    new LootItem("healthgemvn"), new LootItem("regengemvn"),
                    new LootItem("resiliencegemvn"), new LootItem("resilienceregengemvn"),
                    new LootItem("managemvn"), new LootItem("manaregengemvn"),
                    new LootItem("damagegemvn"), new LootItem("critgemvn"),
                    new LootItem("speedgemvn"), new LootItem("dashgemvn"),
                    new LootItem("summongemvn"), new LootItem("armorgemvn")});
    public static final DustyGemSackLootTableVN instance = new DustyGemSackLootTableVN();

    private DustyGemSackLootTableVN() {
        super(new LootItemInterface[]{LootItem.offset("gemdustvn", 7, 5).splitItems(1), gems});
    }

    public static LootTable dustyGemSackvn;
    static{
        dustyGemSackvn = DustyGemSackLootTableVN.instance;
    }
}