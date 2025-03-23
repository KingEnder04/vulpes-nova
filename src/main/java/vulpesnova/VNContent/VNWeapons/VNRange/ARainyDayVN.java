package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;

public class ARainyDayVN extends GunProjectileToolItem {
    public ARainyDayVN() {
    	
        super(NORMAL_AMMO_TYPES, 1000);
        this.rarity = Rarity.UNCOMMON;
        this.attackAnimTime.setBaseValue(70);
        this.attackDamage.setBaseValue(18).setUpgradedValue(1.0F, 90.0F);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
        this.moveDist = 80;
        this.attackRange.setBaseValue(1200);
        this.velocity.setBaseValue(400);
        this.knockback.setBaseValue(25);
        this.ammoConsumeChance = 0.3F;
        this.addGlobalIngredient(new String[]{"bulletuser"});
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "arainydayvntip"));
        tooltips.add(Localization.translate("itemtooltip", "arainydayvntip2"));
        tooltips.add(Localization.translate("itemtooltip", "arainydayvntip3"));
        tooltips.add(Localization.translate("itemtooltip", "arainydayvntip4"));
        tooltips.add(Localization.translate("itemtooltip", "arainydayvntip5"));
    }
    
    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.handgun, SoundEffect.effect(mob));
    }
}