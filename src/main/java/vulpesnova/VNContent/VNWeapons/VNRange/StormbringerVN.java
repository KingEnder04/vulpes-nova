package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import vulpesnova.VulpesNova;

public class StormbringerVN extends GunProjectileToolItem {
    public StormbringerVN() {
        super(NORMAL_AMMO_TYPES, 800);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(140);
        this.attackDamage.setBaseValue(18).setUpgradedValue(1.0F, 78.0F);
        this.attackXOffset = 18;
        this.attackYOffset = 14;
        this.moveDist = 80;
        this.attackRange.setBaseValue(1000);
        this.velocity.setBaseValue(500);
        this.knockback.setBaseValue(25);
        this.ammoConsumeChance = 0.3F;
        this.addGlobalIngredient(new String[]{"bulletuser"});
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }

    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "stormbringervntip"));
    }
    
    @Override
    public void playFireSound(AttackAnimMob mob) {
    	 SoundManager.playSound(VulpesNova.BLASTER1, SoundEffect.effect(mob).pitch(GameRandom.globalRandom.getFloatBetween(-0.1F, 0.1F)));
    }
    
    
}
