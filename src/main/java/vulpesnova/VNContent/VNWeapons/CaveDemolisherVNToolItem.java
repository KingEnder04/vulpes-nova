package vulpesnova.VNContent.VNWeapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.DynamiteStickProjectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.ThrowToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.CaveDemolisherVNProjectile;

import java.awt.*;

public class CaveDemolisherVNToolItem extends ThrowToolItem {
    public CaveDemolisherVNToolItem() {
        this.stackSize = 1;
        this.attackAnimTime.setBaseValue(500);
        this.attackRange.setBaseValue(500);
        this.attackDamage.setBaseValue(200.0F);
        this.velocity.setBaseValue(100);
        this.rarity = Rarity.RARE;
        this.resilienceGain.setBaseValue(0.0F);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "destructivetip"));
        tooltips.add(Localization.translate("itemtooltip", "cavedemolishervntip"));
        return tooltips;
    }

    public Point getControllerAttackLevelPos(Level level, float aimDirX, float aimDirY, PlayerMob player, InventoryItem item) {
        int range = this.getAttackRange(item);
        return new Point((int)(player.x + aimDirX * (float)range), (int)(player.y + aimDirY * (float)range));
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);
        Point targetPos = this.controlledRangePosition(random, player, x, y, item, 0, 40);
        int newRange = (int)player.getDistance((float)targetPos.x, (float)targetPos.y);
        CaveDemolisherVNProjectile projectile = new CaveDemolisherVNProjectile(player.x, player.y, (float)targetPos.x, (float)targetPos.y, this.getThrowingVelocity(item, player), newRange, this.getAttackDamage(item), player);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        projectile.setLevel(level);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
        }

        item.setAmount(item.getAmount() - 0);
        return item;
    }
}
