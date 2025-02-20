package vulpesnova.VNContent.VNWeapons.VNMagic;


import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
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
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class CrimsonTempestVN extends MagicProjectileToolItem {
    public CrimsonTempestVN() {
        super(1200);
        this.rarity = Rarity.LEGENDARY;
        this.animSpeed = 250;
        this.attackDamage.setBaseValue(32).setUpgradedValue(1.0F, 110.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(170);
        this.attackXOffset = 20;
        this.attackYOffset = 20;
        this.attackRange.setBaseValue(1200);
        this.manaCost.setBaseValue(1.3F).setUpgradedValue(1.0F, 2.5F);

    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(mob).pitch(0.8F));
        }

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip2"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip3"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip4"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip5"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    protected float getSettlerProjectileCanHitWidth(HumanMob mob, Mob target, InventoryItem item) {
        return 10.0F;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        return handleMultipleProjectiles(level, x, y, player, item, seed, player);
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        int velocity = this.getVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -10.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        return handleMultipleProjectiles(level, (int) targetPos.x, (int) targetPos.y, mob, item, seed, mob);
    }

    private InventoryItem handleMultipleProjectiles(Level level, int x, int y, Mob attacker, InventoryItem item, int seed, Mob mob) {
        for (int i = -2; i <= 2; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("thunderboltredproj", level, attacker.x, attacker.y, (float) x, (float) y, (float) this.getVelocity(item, mob), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, mob), mob);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.resetUniqueID(new GameRandom((long) seed));
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float)(12 * i));
            if (level.isServer() || level.isServerLevel()) {
                if (attacker instanceof PlayerMob) {
                    level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, mob.getFollowingServerClient());
                } else {
                    level.getServer().network.sendToClientsAt(new PacketSpawnProjectile(projectile), level);
                }
            }
        }

        // Only consume mana if the attacker is a player
        if (attacker instanceof PlayerMob) {
            this.consumeMana((PlayerMob) attacker, item);
        }

        return item;
    }

}
