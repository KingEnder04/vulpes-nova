package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.meleeProjectileToolItem.MeleeProjectileToolItem;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class TheJadeWaraxe extends MeleeProjectileToolItem {
    public TheJadeWaraxe() {
        super(700);
        this.rarity = Rarity.UNCOMMON;
        this.animSpeed = 320;
        this.attackDamage.setBaseValue(30).setUpgradedValue(1.0F, 94.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(120);
        this.attackXOffset = 4;
        this.attackYOffset = 4;
        this.attackRange.setBaseValue(450);
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress) {
        if (this.animInverted) {
            drawOptions.swingRotationInv(attackProgress);
        } else {
            drawOptions.swingRotation(attackProgress);
        }

    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(mob));
        }

    }

    protected void addTooltips(ListGameTooltips tooltips, InventoryItem item, boolean isSettlerWeapon) {
        tooltips.add(Localization.translate("itemtooltip", "thejadewaraxevntip"));
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    protected float getSettlerProjectileCanHitWidth(HumanMob mob, Mob target, InventoryItem item) {
        return 10.0F;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("jadeproj", level, player.x, player.y, (float) x, (float) y, (float) this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player), player);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.resetUniqueID(new GameRandom((long) seed));
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float)(15 * i));
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
            }
        }
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        int velocity = this.getVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -10.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("jadeproj", level, mob.x, mob.y, targetPos.x, targetPos.y, (float)velocity, this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, mob), mob);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.resetUniqueID(new GameRandom((long) seed));
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float)(15 * i));
            if (level.isServer()) {
                level.getServer().network.sendToClientsAt(new PacketSpawnProjectile(projectile), level);
            }
        }

        return item;
    }
}
