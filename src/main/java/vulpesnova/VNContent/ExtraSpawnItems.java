package vulpesnova.VNContent;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.registries.ItemRegistry;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventoryManager;
import net.bytebuddy.asm.Advice;


// When a player makes a new character it'll spawn with a fox token, and a complimentary 4 Nova Shards to help at the beginning.
public class ExtraSpawnItems {
    @ModMethodPatch(target = PlayerInventoryManager.class, name = "giveStarterItems", arguments = {})
    public static class GiveStarterItemsPatch {
        @Advice.OnMethodExit
        public static void onExit(@Advice.This PlayerInventoryManager thiz) {

            if (thiz.getAmount(ItemRegistry.getItem("acrumplednotevn"), false, false, false, false, "startitem") == 0) {
                thiz.main.addItem(thiz.player.getLevel(), thiz.player, new InventoryItem("acrumplednotevn"), "startitem", null);
            }

            if (thiz.getAmount(ItemRegistry.getItem("foxtokenvn"), false, false, false, false, "startitem") == 0) {
                thiz.main.addItem(thiz.player.getLevel(), thiz.player, new InventoryItem("foxtokenvn"), "startitem", null);
            }

            if (thiz.getAmount(ItemRegistry.getItem("travelscroll"), false, false, false, false, "startitem") == 0) {
                thiz.main.addItem(thiz.player.getLevel(), thiz.player, new InventoryItem("travelscroll", 5), "startitem", null);
            }

            if (thiz.getAmount(ItemRegistry.getItem("sushirolls"), false, false, false, false, "startitem") == 0) {
                thiz.main.addItem(thiz.player.getLevel(), thiz.player, new InventoryItem("sushirolls", 3), "startitem", null);
            }
        }
    }
}