package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import vulpesnova.VulpesNova;

public class StormbolterVN extends GunProjectileToolItem {
    public StormbolterVN() {
        super(NORMAL_AMMO_TYPES, 600);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(500);
        this.attackDamage.setBaseValue(45).setUpgradedValue(1.0F, 100.0F);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
        this.attackRange.setBaseValue(800);
        this.velocity.setBaseValue(300);
        this.addGlobalIngredient(new String[]{"bulletuser"});
    }

    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "stormboltervntip"));
    }

    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(VulpesNova.BLASTER1, SoundEffect.effect(mob).pitch(GameRandom.globalRandom.getFloatBetween(-0.2F, 0.2F)));
    }
}