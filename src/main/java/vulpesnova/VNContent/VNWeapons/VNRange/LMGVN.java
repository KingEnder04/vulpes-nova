package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import vulpesnova.VulpesNova;

public class LMGVN extends GunProjectileToolItem {
	
    public LMGVN() {
        super(NORMAL_AMMO_TYPES, 1500);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(100);
        this.attackDamage.setBaseValue(13).setUpgradedValue(1.0F, 84.0F);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
        this.moveDist = 80;
        this.attackRange.setBaseValue(1000);
        this.velocity.setBaseValue(400);
        this.knockback.setBaseValue(25);
        this.ammoConsumeChance = 0.2F;
        this.addGlobalIngredient(new String[]{"bulletuser"});
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "lmgvntip"));

    }

    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(VulpesNova.GUNSHOT1, SoundEffect.effect(mob));
    }
}