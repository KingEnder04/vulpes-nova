package vulpesnova.VNContent.VNWeapons.VNMagic;


import necesse.engine.GlobalData;
import necesse.engine.commands.CommandLog;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import vulpesnova.VulpesNova;
import vulpesnova.VNContent.VNMisc.AttackHandlers.CrimsonTempestAttackHandler;
import vulpesnova.VNContent.VNProjectiles.CrimsonTempestVNProjectile;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class CrimsonTempestVN extends MagicProjectileToolItem implements ItemInteractAction {
	
	static int PROJECTILE_COUNT = 1;
	static int MAX_JUMPS = 3;
	static int MAX_JUMP_DISTANCE = 600;
	static int MAX_JUMP_TARGETTING_ANGLE = 60;
	static int MAX_CHARGE_STACKS = 100;
	static float PROJECTILES_TO_STACK_PERCENTAGE = 0.25F;
	
	
    public CrimsonTempestVN() {
        super(1200);
        this.rarity = Rarity.LEGENDARY;
        this.attackDamage.setBaseValue(32).setUpgradedValue(1.0F, 110.0F);
        this.knockback.setBaseValue(40);
        this.velocity.setBaseValue(175);
        this.attackAnimTime.setBaseValue(300);
        this.attackXOffset = 20;
        this.attackYOffset = 20;
        this.attackRange.setBaseValue(1200);
        this.manaCost.setBaseValue(1.3F).setUpgradedValue(1.0F, 2.5F);
        
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
     

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvninfotip1"));
        tooltips.add(Localization.translate("itemtooltip", "crimsontempestvninfotip2"));
        
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
    	SoundManager.playSound(VulpesNova.ELECTRIC_SHOOT, SoundEffect.effect(source).volume(0.7F));
    	Point targetPoint;
		if (source.isPlayer) {
			GameCamera c = ((necesse.engine.state.MainGame)GlobalData.getCurrentState()).getCamera();
			targetPoint = c.getMouseLevelPos();
			this.consumeMana((PlayerMob)source, item);
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
          				this.getAttackDamage(item).modDamage(2.0F),
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
    
       // this.consumeMana(player, item);
        return item;
    }

    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int attackHeight, int seed, InventoryItem item) {
    	
    	doAttack(level, mob.getX(), mob.getY(), mob, target, attackHeight, item, null, 200, seed, null);

        return item;
    }
	public boolean canLevelInteract(Level level, int x, int y, PlayerMob player, InventoryItem item) {
		return !player.isRiding();
	}

	public int getLevelInteractCooldownTime(InventoryItem item, PlayerMob player) {
		return 0;
	}

	public boolean getConstantInteract(InventoryItem item) {
		return false;
	}

	public InventoryItem onLevelInteract(Level level, int x, int y, PlayerMob player, int attackHeight,
			InventoryItem item, PlayerInventorySlot slot, int seed, PacketReader contentReader) {
		
		
		float stacksPercent = (float) player.buffManager.getStacks(VulpesNova.CRIMSON_TEMPEST_CHARGE_STACKS_BUFF)
				/ (float) MAX_CHARGE_STACKS;				 
		float animModifier = (float) GameMath.lerp(Math.min(Math.pow((double) (stacksPercent * 2.0F), 0.5), 1.0), 8L,
				1L);				
		int animTime = (int) ((float) this.getAttackAnimTime(item, player) * animModifier);
		CrimsonTempestAttackHandler chargeAttackHandler = new CrimsonTempestAttackHandler(player, slot, item, this, animTime, new Color(200,0,0), GameRandom.globalRandom.nextInt());
		player.startAttackHandler(chargeAttackHandler.startFromInteract());
		return item;
	}

	public void doChargedAttack(PlayerMob player, InventoryItem item, float chargePercent) {
		
		SoundManager.playSound(VulpesNova.ELECTRIC_EXPLOSION, SoundEffect.effect(player).volume(0.8F));
		int numProjectiles = Math.round(chargePercent / PROJECTILES_TO_STACK_PERCENTAGE);		
		item.getGndData().setBoolean("chargeUp", false);
		
		
    	Point targetPoint;
		
		GameCamera c = ((necesse.engine.state.MainGame)GlobalData.getCurrentState()).getCamera();
		targetPoint = c.getMouseLevelPos();	
		
		Point2D.Double offset = new Point2D.Double(targetPoint.x - player.x, targetPoint.y - player.y);    
		Point2D.Double perp = GameMath.getPerpendicularDir(offset);
		
		Level level = player.getLevel();
       for(int i = 0; i < numProjectiles; i++) {  	
    	   this.consumeMana(player, item);    	  
    	 
       	   Projectile projectile = new CrimsonTempestVNProjectile(level,
           			player.getPositionPoint(),
           			player.x + Math.round(perp.x*0.1)*(i-numProjectiles/2),
           			player.y + Math.round(perp.y*0.1)*(i-numProjectiles/2),
      				(float) targetPoint.x,
      				(float) targetPoint.y,
      				(float) this.getFlatVelocity(item),
      				this.getAttackRange(item),
      				MAX_JUMPS,
      				MAX_JUMPS,
      				MAX_JUMP_DISTANCE,
      				MAX_JUMP_TARGETTING_ANGLE*2,
      				this.getAttackDamage(item),
      				player);      	        	   
    
       	CommandLog.print(player.getClient(), null, String.format("%1$,.2f",chargePercent));
              projectile.resetUniqueID(GameRandom.globalRandom);
              projectile.setAngle(projectile.getAngle() + GameRandom.getFloatBetween(GameRandom.globalRandom, -0.3F, 0.3F) * (numProjectiles - 1));
              level.entityManager.projectiles.add(projectile);
              
	        if(level.isServer()) {   	      
	   	        level.getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, player.getServerClient());
	        }
       }
	}

	
}
