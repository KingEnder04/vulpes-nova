package vulpesnova.VNContent.VNWeapons.VNMagic;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
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
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.UnholyTomeProjectile;

import java.awt.geom.Point2D;

public class UnholyTome extends MagicProjectileToolItem {
    public UnholyTome() {
        super(700);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(83).setUpgradedValue(1.0F, 149.0F);
        this.velocity.setBaseValue(150);
        this.knockback.setBaseValue(60);
        this.attackXOffset = 12;
        this.attackYOffset = 12;
        this.attackCooldownTime.setBaseValue(300);
        this.attackRange.setBaseValue(350);
        this.manaCost.setBaseValue(0.7f).setUpgradedValue(1.0F, 1.0F);
        this.itemAttackerProjectileCanHitWidth = 10.0F;
    }
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "unholytomevntip"));
        return tooltips;
    }

    @Override
   	public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
   			int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(attackerMob).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(1.0F, 1.1F)));
        }

    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        GameRandom random = new GameRandom((long)seed);

        for(int i = -0; i <= 0; ++i) {
            Projectile projectile = new UnholyTomeProjectile(
            	attackerMob.getLevel(),
        		attackerMob.x,
        		attackerMob.y,
        		(float)x,
        		(float)y,
        		this.getProjectileVelocity(item, attackerMob),
        		this.getAttackRange(item),
        		this.getAttackDamage(item),
        		this.getKnockback(item, attackerMob),
        		attackerMob);
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.moveDist(20.0);
            projectile.setAngle(projectile.getAngle() + (float)(10 * i));
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
            }
        }

        this.consumeMana(attackerMob, item);
        return item;
    }

  
}
