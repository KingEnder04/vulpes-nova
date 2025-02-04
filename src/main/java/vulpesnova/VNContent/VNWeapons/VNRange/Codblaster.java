package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;

public class Codblaster extends GunProjectileToolItem {
    public Codblaster() {
        super(NORMAL_AMMO_TYPES, 400);
        this.rarity = Rarity.UNCOMMON;
        this.animSpeed = 320;
        this.attackDamage.setBaseValue(24).setUpgradedValue(1.0F, 97.0F);
        this.attackXOffset = 12;
        this.attackYOffset = 14;
        this.moveDist = 80;
        this.attackRange.setBaseValue(1000);
        this.velocity.setBaseValue(400);
        this.knockback.setBaseValue(25);
        this.addGlobalIngredient(new String[]{"bulletuser"});
    }

    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "codblastertip"));
    }

    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.watersplash, SoundEffect.effect(mob));
    }
}
