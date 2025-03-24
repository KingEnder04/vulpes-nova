package vulpesnova.VNContent.VNWeapons.VNRange;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.WaitForSecondsEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.TheRavensNestProjectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.greatbowProjectileToolItem.GreatbowProjectileToolItem;
import necesse.level.maps.Level;
import java.awt.*;

public class GrandPhoenixGreatbowVN extends GreatbowProjectileToolItem {
    public GrandPhoenixGreatbowVN() {
        super(1800);
        this.attackAnimTime.setBaseValue(1200);
        this.rarity = Rarity.EPIC;
        this.attackDamage.setBaseValue(122.0F).setUpgradedValue(1.0F, 135.0F);
        this.velocity.setBaseValue(200);
        this.attackRange.setBaseValue(1400);
        this.attackXOffset = 12;
        this.attackYOffset = 38;
        this.particleColor = new Color(169, 150, 236);
        this.setItemCategory("equipment", "weapons", "rangedweapons");
    }

    @Override
    protected void addExtraBowTooltips(ListGameTooltips tooltips, InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        super.addExtraBowTooltips(tooltips, item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "grandphoenixgreatbowvntip"), 400);
    }

    @Override
    public Projectile getProjectile(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item,
			int seed, ArrowItem arrow, boolean consumeAmmo, GNDItemMap mapContent) {
    	 
        return new TheRavensNestProjectile(level,
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
   
    @Override
    public void fireProjectiles(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item, int seed,
			ArrowItem arrow, boolean dropItem, GNDItemMap mapContent) {   

        for(int i = 0; i < 1; ++i) {
            level.entityManager.addLevelEventHidden(new WaitForSecondsEvent((float)i * 0.1F) {
                public void onWaitOver() {
                    int rndY = GameRandom.globalRandom.getIntBetween(-45, 45);
                    Projectile projectile = GrandPhoenixGreatbowVN.this.getProjectile(this.level, x, y + rndY, attackerMob, item, seed, arrow, dropItem, mapContent);
                    projectile.setModifier(new ResilienceOnHitProjectileModifier(GrandPhoenixGreatbowVN.this.getResilienceGain(item)));
                   
                    projectile.getUniqueID(GameRandom.globalRandom);
                    this.level.entityManager.projectiles.addHidden(projectile);
                    if (level.isServer()) {
                        level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
                    }

                }
            });
        }

    }
}

