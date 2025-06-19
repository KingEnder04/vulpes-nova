package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
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
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.meleeProjectileToolItem.MeleeProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.HeaviestHammerShotVNProjectile;

public class HeaviestHammerVN extends MeleeProjectileToolItem {
    public HeaviestHammerVN() {
        super(800);
        this.rarity = Rarity.RARE;
        this.attackAnimTime.setBaseValue(450);
        this.attackDamage.setBaseValue(30).setUpgradedValue(1.0F, 95.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(120);        
        this.attackXOffset = 4;
        this.attackYOffset = 4;
        this.attackRange.setBaseValue(500);
        this.itemAttackerProjectileCanHitWidth = 10.0F;
        this.setItemCategory("equipment", "weapons", "meleeweapons");
    }
    
    @Override
    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress) {
        if (this.animInverted) {
            drawOptions.swingRotationInv(attackProgress);
        } else {
            drawOptions.swingRotation(attackProgress);
        }

    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
			int animAttack, int seed, GNDItemMap mapContent) {    	
        if (level.isClient()) {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(attackerMob));
        }
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "heaviesthammervntip"));
        return tooltips;
    }

    @Override
  	public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
  			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = new HeaviestHammerShotVNProjectile(level,
            		attackerMob.x,
            		attackerMob.y,
            		(float) x,
            		(float) y, 
            		this.getProjectileVelocity(item, attackerMob),
            		this.getAttackRange(item),
            		this.getAttackDamage(item),
            		this.getKnockback(item, attackerMob),
            		attackerMob);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.resetUniqueID(new GameRandom((long) seed));          
            projectile.setAngle(projectile.getAngle() + (float)(12 * i));
            attackerMob.addAndSendAttackerProjectile(projectile);
        }
        return item;
    }    
}
