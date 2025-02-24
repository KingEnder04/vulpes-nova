package vulpesnova.VNContent.VNWeapons.VNMagic;


import necesse.engine.GlobalData;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.camera.MainGameFollowCamera;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNProjectiles.CrimsonTempestVNProjectile;

import java.awt.Point;
import java.awt.geom.Point2D;

public class CrimsonTempestVN extends MagicProjectileToolItem {
	
	static int PROJECTILE_COUNT = 1;
	static int MAX_JUMPS = 3;
	static int MAX_JUMP_DISTANCE = 600;
	static int MAX_JUMP_TARGETTING_ANGLE = 60;
	
    public CrimsonTempestVN() {
        super(1200);
        this.rarity = Rarity.LEGENDARY;
        this.animSpeed = 250;
        this.attackDamage.setBaseValue(32).setUpgradedValue(1.0F, 110.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(170);
        this.attackXOffset = 20;
        this.attackYOffset = 20;
        this.attackRange.setBaseValue(1200);
        this.manaCost.setBaseValue(1.3F).setUpgradedValue(1.0F, 2.5F);

    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(mob).pitch(0.8F));
        }

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip2"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip3"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip4"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvntip5"));
        return tooltips;
    }

    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
        return null;
    }

    protected float getSettlerProjectileCanHitWidth(HumanMob mob, Mob target, InventoryItem item) {
        return 10.0F;
    }

    public void doAttack(Level level, int x, int y, Mob source, Mob target, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader)
    {
    	
    	Point targetPoint;
		if (source.isPlayer) {
			GameCamera c = ((necesse.engine.state.MainGame)GlobalData.getCurrentState()).getCamera();
			targetPoint = c.getMouseLevelPos();
		}
		else {
			if (target == null) return;
			targetPoint = target.getPositionPoint();
		}
    
    
           for(int i = 0; i < PROJECTILE_COUNT; i++) {    	
           	   Projectile projectile = new CrimsonTempestVNProjectile(level,
	           			source.getPositionPoint(),
	           			source.x,
	           			source.y,
          				(float) targetPoint.x,
          				(float) targetPoint.y,
          				(float) this.getFlatVelocity(item),
          				this.getAttackRange(item),
          				MAX_JUMPS,
          				MAX_JUMPS,
          				MAX_JUMP_DISTANCE,
          				MAX_JUMP_TARGETTING_ANGLE,
          				this.getAttackDamage(item),
          				source);
                  projectile.resetUniqueID(new GameRandom((long) seed));
                  level.entityManager.projectiles.addHidden(projectile);
                  projectile.setAngle(projectile.getAngle() + GameRandom.getFloatBetween(GameRandom.globalRandom, -1.0F, 1.0F) * (PROJECTILE_COUNT-1));
                  
            if(level.isServer()) {
	   	        if (source.isPlayer) 
	   	        {
	   	            level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, ((PlayerMob)source).getServerClient());
	   	        }
	   	        else
	   	        {
	   	        	level.getServer().network.sendToClientsAtEntireLevel(new PacketSpawnProjectile(projectile), level);
	   	        }
   	        }
           }

    }
    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
    	
    	doAttack(level, x, y, player,null,attackHeight, item, slot, animAttack,seed, contentReader);
    
        this.consumeMana(player, item);
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
    	
    	doAttack(level, mob.getX(), mob.getY(), mob, target, attackHeight, item, null, 200, seed, null);

        return item;
    }
}
