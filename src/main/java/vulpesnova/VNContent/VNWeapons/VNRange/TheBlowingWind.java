package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;

public class TheBlowingWind extends BowProjectileToolItem {

    public TheBlowingWind() {
        super(400);
        
        this.attackAnimTime.setBaseValue(350);
        this.rarity = Item.Rarity.EPIC;
        this.attackDamage.setBaseValue(27).setUpgradedValue(1.0F, 102.0F);
        this.velocity.setBaseValue(225);
        this.attackRange.setBaseValue(1100);
        this.attackXOffset = 12;
        this.attackYOffset = 31;
        this.resilienceGain.setBaseValue(0.5f);
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
			int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.bow, SoundEffect.effect(attackerMob).pitch(1.1F));
        }

    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "theblowingwindvntip"));
    }

}
