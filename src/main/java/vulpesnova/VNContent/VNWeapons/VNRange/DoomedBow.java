package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.DoomedBowProjectile;

public class DoomedBow extends BowProjectileToolItem {
    public DoomedBow() {
        super(800);
        this.animSpeed = 1300;
        this.rarity = Rarity.EPIC;
        this.attackDamage.setBaseValue(70).setUpgradedValue(1.0F, 120.0F);
        this.velocity.setBaseValue(150);
        this.attackRange.setBaseValue(1000);
        this.attackXOffset = 12;
        this.attackYOffset = 28;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.bow, SoundEffect.effect(mob).pitch(1.1F));
        }

    }

    protected void addTooltips(ListGameTooltips tooltips, InventoryItem item, boolean isSettlerWeapon) {
        tooltips.add(Localization.translate("itemtooltip", "doomedbowvntip"));
    }

    public Projectile getProjectile(Level level, int x, int y, Mob owner, InventoryItem item, int seed, ArrowItem arrow, boolean consumeAmmo, float velocity, int range, GameDamage damage, int knockback, PacketReader contentReader) {
        return new DoomedBowProjectile(owner, owner.x, owner.y, (float)x, (float)y, velocity, range, damage, knockback);
    }
}
