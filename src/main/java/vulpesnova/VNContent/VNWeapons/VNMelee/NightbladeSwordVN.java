package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemModifiers;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.NightbladeVNProjectile;

public class NightbladeSwordVN extends SwordToolItem {
    public NightbladeSwordVN() {
        super(600);
        this.rarity = Rarity.EPIC;
        this.animSpeed = 300;
        this.attackDamage.setBaseValue(28).setUpgradedValue(1.0F, 93.0F);
        this.attackRange.setBaseValue(80);
        this.knockback.setBaseValue(75);

        this.attackXOffset = 9;
        this.attackYOffset = 10;
    }


    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "nightbladevntip"));
        return tooltips;
    }


    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        item = super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);
        float rangeMod = 4.5F;
        float velocity = 140.0F;
        float finalVelocity = (float)Math.round((Float)this.getEnchantment(item).applyModifier(ToolItemModifiers.VELOCITY, (Float)ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * velocity * (Float)player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
        Projectile projectile = new NightbladeVNProjectile(level, player.x, player.y, (float)x, (float)y, finalVelocity, (int)((float)this.getAttackRange(item) * rangeMod), this.getDamage(item), player);
        GameRandom random = new GameRandom((long)seed);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        projectile.moveDist(12.0);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
        }

        return item;
    }

    public int getSettlerAttackRange(HumanMob mob, InventoryItem item) {
        return this.getAttackRange(item) * 5;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
        if (target.getDistance(mob) <= (float) this.getAttackRange(item)) {
            item = super.onSettlerAttack(level, mob, target, attackHeight, seed, item);
        } else {
            mob.attackItem(target.getX(), target.getY(), item);
        }

        float rangeMod = 4.5F;
        float velocity = 140.0F;
        float finalVelocity = (float) Math.round((Float) this.getEnchantment(item).applyModifier(ToolItemModifiers.VELOCITY, (Float) ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * velocity * (Float) mob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
        Projectile projectile = new NightbladeVNProjectile(level, mob.x, mob.y, target.x, target.y, finalVelocity, (int) ((float) this.getAttackRange(item) * rangeMod), this.getDamage(item), mob);
        GameRandom random = new GameRandom((long) seed);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        projectile.moveDist(12.0);
        if (level.isServerLevel()) {
            level.getServer().network.sendToClientsAt(new PacketSpawnProjectile(projectile), level);
        }

        return item;
    }
}
