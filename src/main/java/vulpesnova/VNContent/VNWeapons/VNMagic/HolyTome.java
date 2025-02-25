package vulpesnova.VNContent.VNWeapons.VNMagic;

import java.awt.geom.Point2D;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;

public class HolyTome extends MagicProjectileToolItem {
    public HolyTome() {
        super(600);
        this.rarity = Rarity.RARE;
        this.animSpeed = 300;
        this.attackDamage.setBaseValue(22).setUpgradedValue(1.0F, 88.0F);
        this.velocity.setBaseValue(150);
        this.knockback.setBaseValue(60);
        this.attackXOffset = 12;
        this.attackYOffset = 12;
        this.cooldown = 500;
        this.attackRange.setBaseValue(800);
        this.manaCost.setBaseValue(1.2F).setUpgradedValue(1.0F, 55.0F);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "holytomevntip"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    protected float getSettlerProjectileCanHitWidth(HumanMob mob, Mob target, InventoryItem item) {
        return 12.0F;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(mob).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(1.0F, 1.1F)));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);

        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("holyproj", level, player.x, player.y, (float)x, (float)y, (float)this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player), player);
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.moveDist(20.0);
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
        Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float)velocity, -30.0F);
        mob.attackItem((int)targetPos.x, (int)targetPos.y, item);
        GameRandom random = new GameRandom((long)seed);

        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("holyproj", level, mob.x, mob.y, targetPos.x, targetPos.y, (float)velocity, this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, mob), mob);
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.moveDist(20.0);
            projectile.setAngle(projectile.getAngle() + (float)(10 * i));
            if (level.isServerLevel()) {
                level.getServer().network.sendToClientsAt(new PacketSpawnProjectile(projectile), level);
            }
        }

        return item;
    }
}
