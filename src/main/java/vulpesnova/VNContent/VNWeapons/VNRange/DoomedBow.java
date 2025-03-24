package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.DoomedBowProjectile;

public class DoomedBow extends BowProjectileToolItem {
    public DoomedBow() {
        super(800);
        this.attackAnimTime.setBaseValue(1000);
        this.rarity = Rarity.EPIC;
        this.attackDamage.setBaseValue(70).setUpgradedValue(1.0F, 120.0F);
        this.velocity.setBaseValue(150);
        this.attackRange.setBaseValue(1000);
        this.attackXOffset = 12;
        this.attackYOffset = 28;
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }
    
    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
			int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.bow, SoundEffect.effect(attackerMob).pitch(1.1F));
        }

    }
    
    @Override
    protected void addExtraBowTooltips(ListGameTooltips tooltips, InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        super.addExtraBowTooltips(tooltips, item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "doomedbowvntip"));
    }

    
    @Override
    public Projectile getProjectile(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item,
			int seed, ArrowItem arrow, boolean consumeAmmo, GNDItemMap mapContent) {
    	 
        return new DoomedBowProjectile(
        		attackerMob,
        		attackerMob.x,
        		attackerMob.y,
        		(float)x,
        		(float)y,
        		this.getProjectileVelocity(item, attackerMob),
        		this.getAttackRange(item),
        		this.getAttackDamage(item),
        		this.getKnockback(item, attackerMob)
        		);
    }   
}
