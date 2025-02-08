package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.ProjectileRegistry;
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
import necesse.inventory.item.bulletItem.BulletItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.CodBlasterProjectile;

public class Codblaster extends GunProjectileToolItem {
    public Codblaster() {
        super(NORMAL_AMMO_TYPES, 400);
        this.rarity = Rarity.UNCOMMON;
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
        tooltips.add(Localization.translate("itemtooltip", "codblastervntip"));
    }

    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.watersplash, SoundEffect.effect(mob));
    }
    
    public Projectile getProjectile(InventoryItem item, BulletItem bulletItem, float x, float y, float targetX,
			float targetY, int range, Mob owner) {
        return new CodBlasterProjectile(owner.x, owner.y, (float)x, (float)y, this.getDamage(item), owner);
    }
  }

