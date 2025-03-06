package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;

public class RepeatingCrossbow extends BowProjectileToolItem {
	
    public RepeatingCrossbow() {
        super(1700);
        this.attackAnimTime.setBaseValue(250);
        this.rarity = Rarity.UNCOMMON;
        this.attackDamage.setBaseValue(21).setUpgradedValue(1.0F, 91.0F);
        this.velocity.setBaseValue(100);
        this.attackRange.setBaseValue(800);
        this.attackXOffset = 5;
        this.attackYOffset = 14;
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
			int animAttack, int seed, GNDItemMap mapContent)  {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.bow, SoundEffect.effect(attackerMob).pitch(1.1F));
        }

    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "repeatingcrossbowvntip"));
    }

}

