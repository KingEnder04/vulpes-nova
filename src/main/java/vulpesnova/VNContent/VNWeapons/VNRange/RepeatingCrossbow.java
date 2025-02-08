package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;

public class RepeatingCrossbow extends BowProjectileToolItem {
    public RepeatingCrossbow() {
        super(1700);
        this.animSpeed = 250;
        this.rarity = Rarity.UNCOMMON;
        this.attackDamage.setBaseValue(21).setUpgradedValue(1.0F, 91.0F);
        this.velocity.setBaseValue(100);
        this.attackRange.setBaseValue(800);
        this.attackXOffset = 5;
        this.attackYOffset = 14;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.bow, SoundEffect.effect(mob).pitch(1.1F));
        }

    }

    protected void addTooltips(ListGameTooltips tooltips, InventoryItem item, boolean isSettlerWeapon) {
        tooltips.add(Localization.translate("itemtooltip", "repeatingcrossbowvntip"));
    }

}

