package vulpesnova.VNContent.VNMaterials;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.matItem.MatItem;

public class AwakeninatorMaterialItem extends MatItem {

	@Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "awakeninatorvntip"));
        return tooltips;
    }
    public AwakeninatorMaterialItem() {
        super(999, Rarity.UNIQUE);
    }

}
