package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.bulletItem.BulletItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Point2D;

public class AcornLobberVN extends GunProjectileToolItem {
    public AcornLobberVN() {
        super(NORMAL_AMMO_TYPES, 100);
        this.rarity = Rarity.COMMON;
        this.animSpeed = 200;
        this.attackDamage.setBaseValue(7).setUpgradedValue(1.0F, 60.0F);
        this.attackXOffset = 8;
        this.attackYOffset = 10;
        this.attackRange.setBaseValue(1200);
        this.velocity.setBaseValue(200);
        this.ammoConsumeChance = 0.50f;
        this.addGlobalIngredient(new String[]{"bulletuser"});
    }

    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "acornlobbervntip1"));
        tooltips.add(Localization.translate("itemtooltip", "acornlobbervntip2"));
    }

    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.handgun, SoundEffect.effect(mob));
    }

    protected void fireProjectiles(Level level, int x, int y, PlayerMob player, InventoryItem item, int seed, BulletItem bullet, boolean consumeAmmo, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);
        GameRandom spreadRandom = new GameRandom((long)(seed + 10));
        int range;
        if (this.controlledRange) {
            Point newTarget = this.controlledRangePosition(spreadRandom, player, x, y, item, this.controlledMinRange, this.controlledInaccuracy);
            x = newTarget.x;
            y = newTarget.y;
            range = (int)player.getDistance((float)x, (float)y);
        } else {
            range = this.getAttackRange(item);
        }

        for(int i = 0; i <= 0; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("acornprojectilevn", level, player.x, player.y, (float)x, (float)y, (float)this.getProjectileVelocity(item, player), this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, player), player);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.dropItem = consumeAmmo;
            projectile.getUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            if (this.moveDist != 0) {
                projectile.moveDist((double)this.moveDist);
            }

            projectile.setAngle(projectile.getAngle() + (spreadRandom.nextFloat() - 0.5F) * 20.0F);
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
            }
        }

    }

    protected void fireSettlerProjectiles(Level level, HumanMob mob, Mob target, InventoryItem item, int seed, BulletItem bullet, boolean consumeAmmo) {
        GameRandom random = new GameRandom((long)seed);
        GameRandom spreadRandom = new GameRandom((long)(seed + 10));
        int velocity = this.getProjectileVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -10.0F);
        int x = (int)targetPos.x;
        int y = (int)targetPos.y;
        int range;
        if (this.controlledRange) {
            Point newTarget = this.controlledRangePosition(spreadRandom, mob, x, y, item, this.controlledMinRange, this.controlledInaccuracy);
            x = newTarget.x;
            y = newTarget.y;
            range = (int)mob.getDistance((float)x, (float)y);
        } else {
            range = this.getAttackRange(item);
        }

        for(int i = 0; i <= 0; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("acornprojectilevn", level, mob.x, mob.y, target.x, target.y, (float)velocity, this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, mob), mob);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.dropItem = consumeAmmo;
            projectile.getUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            if (this.moveDist != 0) {
                projectile.moveDist((double)this.moveDist);
            }

            projectile.setAngle(projectile.getAngle() + (spreadRandom.nextFloat() - 0.5F) * 20.0F);
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntity(new PacketSpawnProjectile(projectile), projectile);
            }
        }

    }

}

