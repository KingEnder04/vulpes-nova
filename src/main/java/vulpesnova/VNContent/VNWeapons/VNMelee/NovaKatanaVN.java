package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.inventory.item.toolItem.swordToolItem.KatanaToolItem;
public class NovaKatanaVN extends KatanaToolItem {
    public NovaKatanaVN() {
        super(1400);
        this.rarity = Rarity.UNIQUE;
        this.attackAnimTime.setBaseValue(170);
        this.attackDamage.setBaseValue(60.0F).setUpgradedValue(1.0F, 102.0F);
        this.attackRange.setBaseValue(100);
        this.knockback.setBaseValue(75);
        this.resilienceGain.setBaseValue(1.0F);
        this.maxDashStacks.setBaseValue(20);
        this.dashRange.setBaseValue(600);
        this.attackXOffset = 4;
        this.attackYOffset = 4;
    }
}
