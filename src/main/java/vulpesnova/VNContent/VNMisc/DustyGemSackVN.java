package vulpesnova.VNContent.VNMisc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;
import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.Container;
import necesse.inventory.container.ContainerActionResult;
import necesse.inventory.container.slots.ContainerSlot;
import necesse.inventory.item.matItem.MultiTextureMatItem;
import vulpesnova.VNContent.DustyGemSackLootTableVN;

public class DustyGemSackVN extends MultiTextureMatItem {
    public DustyGemSackVN() {
        super(4, 50, new String[0]);
        this.rarity = Rarity.COMMON;
        this.setItemCategory(new String[]{"misc"});
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "dustygemsackvntip"));
        tooltips.add(Localization.translate("itemtooltip", "rclickinvopentip"));
        return tooltips;
    }

    public Supplier<ContainerActionResult> getInventoryRightClickAction(Container container, InventoryItem item, int slotIndex, ContainerSlot slot) {
        return () -> {
            if (container.getClient().isServer()) {
                ArrayList<InventoryItem> itemList = new ArrayList();
                DustyGemSackLootTableVN.dustyGemSackvn.addItems(itemList, GameRandom.globalRandom, 1.0F, new Object[]{container.getClient()});
                Iterator var4 = itemList.iterator();

                while(var4.hasNext()) {
                    InventoryItem inventoryItem = (InventoryItem)var4.next();
                    container.getClient().playerMob.getInv().addItemsDropRemaining(inventoryItem, "addback", container.getClient().playerMob, true, false);
                }
            }

            slot.setAmount(item.getAmount() - 1);
            if (item.getAmount() <= 0) {
                slot.setItem((InventoryItem)null);
            }

            return new ContainerActionResult(154617259 * (item.getAmount() + GameRandom.prime(4)));
        };
    }
}
