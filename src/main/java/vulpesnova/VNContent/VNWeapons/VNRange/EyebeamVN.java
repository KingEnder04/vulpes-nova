package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;

public class EyebeamVN extends GunProjectileToolItem {
    public EyebeamVN() {
        super(NORMAL_AMMO_TYPES, 400);
        this.rarity = Rarity.UNCOMMON;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(26).setUpgradedValue(1.0F, 100.0F);
        this.attackXOffset = 10;
        this.attackYOffset = 14;
        this.attackRange.setBaseValue(800);
        this.velocity.setBaseValue(200);
        this.addGlobalIngredient(new String[]{"bulletuser"});
    }

    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "eyebeamvntip"));

    }

    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.spit, SoundEffect.effect(mob));
    }
}