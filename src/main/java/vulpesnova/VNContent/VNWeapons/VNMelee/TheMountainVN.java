package vulpesnova.VNContent.VNWeapons.VNMelee;


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
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.meleeProjectileToolItem.MeleeProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.TheMountainShotVNProjectile;

public class TheMountainVN extends MeleeProjectileToolItem {
    public TheMountainVN() {
        super(1000);
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(430);
        this.attackDamage.setBaseValue(38).setUpgradedValue(1.0F, 111.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(120);
        this.attackXOffset = 6;
        this.attackYOffset = 6;
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
			int animAttack, int seed, GNDItemMap mapContent)  {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(attackerMob));
        }

    }
    
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "themountainvntip"));
        
        return tooltips;
    }

    @Override
	public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        for(int i = -1; i <= 2; ++i) {
        	
        	//Level level, float x, float y, float targetX, float targetY, int speed, int distance, GameDamage damage, Mob owner
            Projectile projectile = new TheMountainShotVNProjectile(level,
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
            projectile.setAngle(projectile.getAngle() + (float)(9 * i));
            attackerMob.addAndSendAttackerProjectile(projectile);          
        }
        return item;
    }

  
}
