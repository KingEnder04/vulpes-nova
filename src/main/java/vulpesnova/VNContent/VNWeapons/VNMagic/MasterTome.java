package vulpesnova.VNContent.VNWeapons.VNMagic;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.MasterTomeProjectile;

public class MasterTome extends MagicProjectileToolItem {
    public MasterTome() {
        super(200);
        this.rarity = Rarity.UNCOMMON;
        this.attackAnimTime.setBaseValue(200);
        this.attackDamage.setBaseValue(40).setUpgradedValue(1.0F, 108.0F);
        this.velocity.setBaseValue(160);
        this.attackXOffset = 12;
        this.attackYOffset = 12;
        this.attackRange.setBaseValue(2000);
        this.knockback.setBaseValue(50);
        this.manaCost.setBaseValue(1.0f).setUpgradedValue(1.0F, 2.0F);
        this.itemAttackerProjectileCanHitWidth = 5.0F;

    }
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "mastertomevntip"));
        return tooltips;
    }

    @Override
   	public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
   			int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(attackerMob).pitch(0.8F));
        }

    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        for (int i = -1; i <= 0; ++i) {
            GameRandom random = new GameRandom((long) seed);
            Projectile projectile = new MasterTomeProjectile(attackerMob.getLevel(),
            		attackerMob,
            		attackerMob.x,
            		attackerMob.y,
            		(float)x,
            		(float)y,
            		this.getProjectileVelocity(item, attackerMob),
            		this.getAttackRange(item),
            		this.getAttackDamage(item),
            		this.getKnockback(item, attackerMob));
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float) (180 * i));
            projectile.moveDist(40.0);
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
            }
        }
        this.consumeMana(attackerMob, item);
        return item;
    }

    
}
