package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.itemAttack.HumanAttackDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.bulletItem.BulletItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import vulpesnova.VulpesNova;
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
       
    }
    

    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "codblastervntip"));
    }

    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.watersplash, SoundEffect.effect(mob));        
    }
    
    protected void fireProjectiles(Level level, int x, int y, PlayerMob player, InventoryItem item, int seed,
			BulletItem bullet, boolean consumeAmmo, PacketReader contentReader) {
    	
		float distance = GameMath.diamondDistance(player.x, player.y, (float) x, (float) y);
		float t = 20.0F / distance;
		float projectileX = (1.0F - t) * player.x + t * (float) x;
		float projectileY = (1.0F - t) * player.y + t * (float) y;
		GameRandom random = new GameRandom((long) seed);
		GameRandom spreadRandom = new GameRandom((long) (seed + 10));
		Projectile projectile = this.getNormalProjectile(projectileX, projectileY, (float) x, (float) y, this.getFlatVelocity(item), 2000,
				this.getAttackDamage(item), this.getKnockback(item, player), player);
		
		projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
		projectile.dropItem = consumeAmmo;
		projectile.getUniqueID(random);
		level.entityManager.projectiles.addHidden(projectile);
		if (this.moveDist != 0) {
			projectile.moveDist((double) this.moveDist);
		}

		projectile.setAngle(projectile.getAngle() + spreadRandom.getFloatOffset(0.0F, 3.0F));
		if (level.isServer()) {
			level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile,
					player.getServerClient());
		}
		
		

	}

   
    public Projectile getNormalProjectile(float x, float y, float targetX, float targetY, float velocity, int range,
			GameDamage toolItemDamage, int knockback, Mob owner) {
		return new CodBlasterProjectile(x, y, targetX, targetY, velocity, range, toolItemDamage, knockback, owner);
	}

  }

