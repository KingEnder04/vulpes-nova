package vulpesnova.VNContent.VNWeapons.VNMagic;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
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
import vulpesnova.VNContent.VNProjectiles.MagicForDummiesVNProjectile;

import java.awt.geom.Point2D;

public class MagicForDummiesVN extends MagicProjectileToolItem {
    public MagicForDummiesVN() {
        super(1500);
        this.rarity = Rarity.UNIQUE;
        this.animSpeed = 400;
        this.attackDamage.setBaseValue(46).setUpgradedValue(1.0F, 126.0F);
        this.velocity.setBaseValue(100);
        this.attackXOffset = 12;
        this.attackYOffset = 12;
        this.attackRange.setBaseValue(1500);
        this.knockback.setBaseValue(50);
        this.manaCost.setBaseValue(2.0f).setUpgradedValue(1.0F, 3.2F);

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "magicfordummiesvntip"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    protected float getSettlerProjectileCanHitWidth(HumanMob mob, Mob target, InventoryItem item) {
        return 5.0F;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(mob).pitch(0.8F));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        for(int i = -1; i <= 1; ++i) {
            GameRandom random = new GameRandom((long) seed);
            Projectile projectile = new MagicForDummiesVNProjectile(level, player, player.x, player.y, (float) x, (float) y, (float) this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player));
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float) (90 * i));
            projectile.moveDist(40.0);
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
            }
        }
        this.consumeMana(player, item);
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        for(int i = -1; i <= 1; ++i) {
            int velocity = this.getVelocity(item, mob);
            Point2D.Float targetPos = Projectile.getPredictedTargetPos(target, mob.x, mob.y, (float) velocity, -50.0F);
            mob.attackItem((int) targetPos.x, (int) targetPos.y, item);
            GameRandom random = new GameRandom((long) seed);
            Projectile projectile = new MagicForDummiesVNProjectile(level, mob, mob.x, mob.y, targetPos.x, targetPos.y, (float) velocity, this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, mob));
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float) (90 * i));
            projectile.moveDist(40.0);
            if (level.isServerLevel()) {
                level.getServer().network.sendToClientsAt(new PacketSpawnProjectile(projectile), level);
            }
        }
        return item;
    }
}
