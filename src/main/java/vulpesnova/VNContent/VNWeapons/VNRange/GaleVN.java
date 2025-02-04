package vulpesnova.VNContent.VNWeapons.VNRange;


import necesse.engine.localization.Localization;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;

public class GaleVN extends GunProjectileToolItem {
    public GaleVN() {
        super(NORMAL_AMMO_TYPES, 50);
        this.rarity = Rarity.COMMON;
        this.animSpeed = 500;
        this.attackDamage.setBaseValue(35).setUpgradedValue(1.0F, 104.0F);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
        this.attackRange.setBaseValue(800);
        this.velocity.setBaseValue(300);
        this.addGlobalIngredient(new String[]{"bulletuser"});
    }

    protected void addTooltips(ListGameTooltips tooltips, InventoryItem item, boolean isSettlerWeapon) {
        tooltips.add(Localization.translate("itemtooltip", "galevntip"));
    }

    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.handgun, SoundEffect.effect(mob));
    }
}
