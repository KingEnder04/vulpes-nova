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
public class EyeOfTheStormVN extends MagicProjectileToolItem {
    public EyeOfTheStormVN() {
        super(800);
        this.rarity = Rarity.UNCOMMON;
        this.animSpeed = 250;
        this.attackDamage.setBaseValue(28).setUpgradedValue(1.0F, 96.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(170);
        this.attackXOffset = 20;
        this.attackYOffset = 20;
        this.attackRange.setBaseValue(1200);
        this.manaCost.setBaseValue(1.0F).setUpgradedValue(1.0F, 55.0F);

    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(mob).pitch(0.8F));
        }

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "eyeofthestormvntip"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    protected float getSettlerProjectileCanHitWidth(HumanMob mob, Mob target, InventoryItem item) {
        return 10.0F;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("thunderboltblueproj", level, player.x, player.y, (float) x, (float) y, (float) this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player), player);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.resetUniqueID(new GameRandom((long) seed));
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float)(10 * i));
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
            }
        }

        this.consumeMana(player, item);
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        int velocity = this.getVelocity(item, mob);
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -10.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("thunderboltblueproj", level, mob.x, mob.y, targetPos.x, targetPos.y, (float)velocity, this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, mob), mob);
            projectile.resetUniqueID(new GameRandom((long) seed));
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float)(12 * i));
            if (level.isServer()) {
                level.getServer().network.sendToClientsAt(new PacketSpawnProjectile(projectile), level);
            }
        }

        return item;
    }
}
