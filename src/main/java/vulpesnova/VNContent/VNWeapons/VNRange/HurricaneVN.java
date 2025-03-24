package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;

public class HurricaneVN extends GunProjectileToolItem {
    public HurricaneVN() {
        super(NORMAL_AMMO_TYPES, 300);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(170);
        this.attackDamage.setBaseValue(12).setUpgradedValue(1.0F, 82.0F);
        this.attackXOffset = 18;
        this.attackYOffset = 14;
        this.moveDist = 80;
        this.attackRange.setBaseValue(1000);
        this.velocity.setBaseValue(500);
        this.knockback.setBaseValue(25);
        this.ammoConsumeChance = 0.2F;
        this.addGlobalIngredient(new String[]{"bulletuser"});
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "hurricanevntip"));
    }

    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.handgun, SoundEffect.effect(mob));
    }
}
