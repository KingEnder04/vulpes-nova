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
import vulpesnova.VNContent.VNProjectiles.ThunderboltBlueVNProjectile;

public class EyeOfTheStormVN extends MagicProjectileToolItem {
    public EyeOfTheStormVN() {
        super(800);
        this.rarity = Rarity.UNCOMMON;
        this.attackAnimTime.setBaseValue(250);
        this.attackDamage.setBaseValue(28).setUpgradedValue(1.0F, 96.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(170);
        this.attackXOffset = 20;
        this.attackYOffset = 20;
        this.attackRange.setBaseValue(1200);
        this.manaCost.setBaseValue(1.0F).setUpgradedValue(1.0F, 55.0F);
        this.itemAttackerProjectileCanHitWidth = 10.0F;

    }

    @Override
   	public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
   			int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(attackerMob).pitch(0.8F));
        }

    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "eyeofthestormvntip"));
        return tooltips;
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        for(int i = -1; i <= 1; ++i) {
            Projectile projectile = new ThunderboltBlueVNProjectile(
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
            projectile.resetUniqueID(new GameRandom((long) seed));
            level.entityManager.projectiles.addHidden(projectile);
            projectile.setAngle(projectile.getAngle() + (float)(10 * i));
            if (level.isServer()) {
                level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, attackerMob.getServer().getLocalServerClient());
            }
        }

        this.consumeMana(attackerMob, item);
        return item;
    }

   
}
