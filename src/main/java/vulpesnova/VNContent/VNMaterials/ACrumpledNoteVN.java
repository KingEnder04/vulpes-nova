package vulpesnova.VNContent.VNMaterials;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.matItem.MatItem;

public class ACrumpledNoteVN extends MatItem {

	@Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "acrumplednotevntip1"));
        tooltips.add(Localization.translate("itemtooltip", "acrumplednotevntip2"));
        tooltips.add(Localization.translate("itemtooltip", "acrumplednotevntip3"));
        tooltips.add(Localization.translate("itemtooltip", "acrumplednotevntip4"));
        tooltips.add(Localization.translate("itemtooltip", "acrumplednotevntip5"));
        return tooltips;
    }
	
    public ACrumpledNoteVN() {
        super(9999, Rarity.UNIQUE);
    }

}
