package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.ToolItemModifiers;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.NightbladeVNProjectile;

public class NightbladeSwordVN extends SwordToolItem {
    public NightbladeSwordVN() {
        super(600);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(28).setUpgradedValue(1.0F, 93.0F);
        this.attackRange.setBaseValue(80);
        this.knockback.setBaseValue(75);
        this.attackXOffset = 9;
        this.attackYOffset = 10;
  
    }

	@Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "nightbladevntip"));
        return tooltips;
    }

    
    @Override
  	public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
  			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
    	
        item = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        float rangeMod = 4.5F;
        float velocity = 140.0F;
        float finalVelocity = (float)Math.round( (Float)this.getEnchantment(item).getModifier(ToolItemModifiers.VELOCITY) * velocity * (Float)attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
        
        Projectile projectile = new NightbladeVNProjectile(level,
        		attackerMob.x,
        		attackerMob.y,
        		(float)x,
        		(float)y, 
        		finalVelocity,
        		(int)((float)this.getAttackRange(item) * rangeMod),
        		this.getAttackDamage(item), attackerMob);
        
        GameRandom random = new GameRandom((long)seed);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        projectile.moveDist(12.0);
        
        if (level.isServer()) {
            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
        }

        return item;
    }

}
