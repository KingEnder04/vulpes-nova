package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
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
import vulpesnova.VNContent.VNProjectiles.AcornProjectile;

import java.awt.*;

public class AcornLobberVN extends GunProjectileToolItem {
	
    public AcornLobberVN() {
        super(NORMAL_AMMO_TYPES, 100);
        this.rarity = Rarity.COMMON;
        this.attackAnimTime.setBaseValue(200);
        this.attackDamage.setBaseValue(7).setUpgradedValue(1.0F, 60.0F);
        this.attackXOffset = 8;
        this.attackYOffset = 10;
        this.attackRange.setBaseValue(1200);
        this.velocity.setBaseValue(200);
        this.ammoConsumeChance = 0.50f;
        this.addGlobalIngredient(new String[]{"bulletuser"});
    }
    
    @Override
    protected void addAmmoTooltips(ListGameTooltips tooltips, InventoryItem item) {
        super.addAmmoTooltips(tooltips, item);
        tooltips.add(Localization.translate("itemtooltip", "acornlobbervntip1"));
        tooltips.add(Localization.translate("itemtooltip", "acornlobbervntip2"));
    }
    
    @Override
    public void playFireSound(AttackAnimMob mob) {
        SoundManager.playSound(GameResources.handgun, SoundEffect.effect(mob));
    }

    @Override
    public void fireProjectiles(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item, int seed,
			BulletItem bullet, boolean dropItem, GNDItemMap mapContent) {
    	
        GameRandom random = new GameRandom((long)seed);
        GameRandom spreadRandom = new GameRandom((long)(seed + 10));
        int range;
        if (this.controlledRange) {
            Point newTarget = this.controlledRangePosition(spreadRandom, attackerMob, x, y, item, this.controlledMinRange, this.controlledInaccuracy);
            x = newTarget.x;
            y = newTarget.y;
            range = (int)attackerMob.getDistance((float)x, (float)y);
        } else {
            range = this.getAttackRange(item);
        }

        for(int i = 0; i <= 0; ++i) {
            Projectile projectile = new AcornProjectile(
            		attackerMob.x,
            		attackerMob.y,
            		(float)x,
            		(float)y,
            		(float)this.getProjectileVelocity(item, attackerMob),
            		range,
            		this.getAttackDamage(item),
            		this.getKnockback(item, attackerMob),
            		attackerMob);
            
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.dropItem = dropItem;
            projectile.getUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            if (this.moveDist != 0) {
                projectile.moveDist((double)this.moveDist);
            }

            projectile.setAngle(projectile.getAngle() + (spreadRandom.nextFloat() - 0.5F) * 20.0F);
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
            }
        }

    }
}

