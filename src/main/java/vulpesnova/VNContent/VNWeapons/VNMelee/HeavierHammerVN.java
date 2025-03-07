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
import vulpesnova.VNContent.VNProjectiles.HeavierHammerShotVNProjectile;

public class HeavierHammerVN extends MeleeProjectileToolItem {
    public HeavierHammerVN() {
        super(600);
        this.rarity = Rarity.UNCOMMON;
        this.attackAnimTime.setBaseValue(400);
        this.attackDamage.setBaseValue(24.0F).setUpgradedValue(1.0F, 80.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(120);     
        this.attackRange.setBaseValue(450);
        this.attackXOffset = 4;
        this.attackYOffset = 4;
        this.itemAttackerProjectileCanHitWidth = 10.0F;
    	this.canBeUsedForRaids = true;
		this.raidTicketsModifier = 0.5F;
		this.useForRaidsOnlyIfObtained = true;
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
        tooltips.add(Localization.translate("itemtooltip", "heavierhammervntip"));
        return tooltips;
    }
    
    @Override
  	public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
  			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        for(int i = 0; i <= 1; ++i) {
        
            Projectile projectile = new HeavierHammerShotVNProjectile(
            		level,
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
            projectile.setAngle(projectile.getAngle() + (float)(8 * i));
            attackerMob.addAndSendAttackerProjectile(projectile);
        }
        return item;
    }
  
}
