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
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.ThunderboltVNProjectile;

public class ThunderingRodVN extends MagicProjectileToolItem {
    public ThunderingRodVN() {
        super(200);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(200);
        this.attackDamage.setBaseValue(18).setUpgradedValue(1.0F, 88.0F);
        this.velocity.setBaseValue(200);
        this.attackXOffset = 23;
        this.attackYOffset = 23;
        this.attackRange.setBaseValue(1000);
        this.knockback.setBaseValue(50);
        this.manaCost.setBaseValue(0.7f).setUpgradedValue(1.0F, 1.0F);
        this.itemAttackerProjectileCanHitWidth = 5.0F;
    }
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "thunderingrodvntip"));
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
    	
    	 GameRandom random = new GameRandom((long) seed);
         Projectile projectile = new ThunderboltVNProjectile(
        		attackerMob.getLevel(),
        		attackerMob.x,
        		attackerMob.y,
        		(float)x,
        		(float)y,
        		this.getProjectileVelocity(item, attackerMob),
        		this.getAttackRange(item),
        		this.getAttackDamage(item),
        		this.getKnockback(item, attackerMob),attackerMob);
         projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
         projectile.resetUniqueID(random);
         level.entityManager.projectiles.addHidden(projectile);
         projectile.moveDist(40.0);
         
         if (level.isServer()) {
             level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
         }

         this.consumeMana(attackerMob, item);
         return item;
    }

}
