package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.bulletItem.BulletItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.CodBlasterProjectile;

public class Codblaster extends GunProjectileToolItem {
	
    public Codblaster() {
        super(NORMAL_AMMO_TYPES, 400);
        this.rarity = Rarity.UNCOMMON;
        this.attackDamage.setBaseValue(24).setUpgradedValue(1.0F, 97.0F);
        this.attackXOffset = 4;
        this.attackYOffset = 16;
        this.moveDist = 16;
        this.attackRange.setBaseValue(1000);
        this.velocity.setBaseValue(80);
        this.knockback.setBaseValue(100);
        this.addGlobalIngredient(new String[]{"bulletuser"});
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "codblastervntip"));
    }
    
    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.watersplash, SoundEffect.effect(mob));        
    }
    
    @Override
    public void fireProjectiles(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item, int seed,
			BulletItem bullet, boolean dropItem, GNDItemMap mapContent) {
    	
		float distance = GameMath.diamondDistance(attackerMob.x, attackerMob.y, (float) x, (float) y);
		float t = 20.0F / distance;
		float projectileX = (1.0F - t) * attackerMob.x + t * (float) x;
		float projectileY = (1.0F - t) * attackerMob.y + t * (float) y;
		GameRandom random = new GameRandom((long) seed);
		GameRandom spreadRandom = new GameRandom((long) (seed + 10));
		Projectile projectile = this.getProjectile(item, bullet, projectileX, projectileY, (float) x, (float) y,  2000, attackerMob);
		
		projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
		projectile.dropItem = dropItem;
		projectile.getUniqueID(random);
		level.entityManager.projectiles.addHidden(projectile);
		if (this.moveDist != 0) {
			projectile.moveDist((double) this.moveDist);
		}

		projectile.setAngle(projectile.getAngle() + spreadRandom.getFloatOffset(0.0F, 3.0F));
		if (level.isServer()) {
			level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile,
					attackerMob.getServer().getLocalServerClient());
		}
		
	}

    @Override
    public Projectile getProjectile(InventoryItem item, BulletItem bulletItem, float x, float y, float targetX,
			float targetY, int range, ItemAttackerMob attackerMob) {
    	
		return new CodBlasterProjectile(x,
				y,
				targetX,
				targetY,
				this.getProjectileVelocity(item, attackerMob),
				this.getAttackRange(item),
        		this.getAttackDamage(item),
        		this.getKnockback(item, attackerMob),
				attackerMob);
		
	}

  }

