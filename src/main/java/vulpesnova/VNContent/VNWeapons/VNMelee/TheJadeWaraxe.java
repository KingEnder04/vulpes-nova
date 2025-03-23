package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketLevelEvent;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import vulpesnova.VulpesNova;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;

import necesse.entity.levelEvent.SwordCleanSliceAttackEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
public class TheJadeWaraxe extends SwordToolItem {	
   
    public TheJadeWaraxe(int enchantCost) {
		super(enchantCost);
	}
	public TheJadeWaraxe() {
    	
        this(700);
        this.rarity = Rarity.UNCOMMON;
        
        this.attackAnimTime.setBaseValue(200);
		this.attackRange.setBaseValue(50);
		this.resilienceGain.setBaseValue(1.0F);
		
		this.attackXOffset = 2;
		this.attackYOffset = 2;
        
        this.attackDamage.setBaseValue(30).setUpgradedValue(1.0F, 94.0F);
        this.knockback.setBaseValue(40);
        
        this.setItemCategory("equipment", "weapons", "meleeweapons");
       
    }
	
	@Override
	public GameSprite getAttackSprite(InventoryItem item, PlayerMob player) {
		return super.getAttackSprite(item, player);
	}
	
	@Override
	protected void loadAttackTexture() {
		super.loadAttackTexture();		
	
	}
	
	@Override
	public ItemAttackDrawOptions setupItemSpriteAttackDrawOptions(ItemAttackDrawOptions options, InventoryItem item,
			PlayerMob player, int mobDir, float attackDirX, float attackDirY, float attackProgress, Color itemColor) {
		
		/*float chargePercent = Math.min(item.getGndData().getFloat("chargePercent"), 1.0F);
		
		ItemAttackDrawOptions.AttackItemSprite itemSprite = options.itemSprite(this.getAttackSprite(item, player));
		if (options.dir == 0) {
			itemSprite.itemRotateOffsetAdd(GameMath.lerp(chargePercent, 70.0F, 90.0F));
			itemSprite.itemRotatePoint(this.attackXOffset + GameMath.lerp(chargePercent, 16, 18),
					this.attackYOffset - GameMath.lerp(chargePercent, 0, 4));
		} else if (options.dir == 2) {
			itemSprite.itemRotateOffsetAdd(GameMath.lerp(chargePercent, 150.0F, 170.0F));
			itemSprite.itemRotatePoint(this.attackXOffset + GameMath.lerp(chargePercent, 22, 26),
					this.attackYOffset);
		} else {
			itemSprite.itemRotateOffsetAdd(GameMath.lerp(chargePercent, 120.0F, 140.0F));
			itemSprite.itemRotatePoint(this.attackXOffset + GameMath.lerp(chargePercent, 20, 22),
					this.attackYOffset);
		}

		if (itemColor != null) {
			itemSprite.itemColor(itemColor);
		}*/
		
		return super.setupItemSpriteAttackDrawOptions(options, item, player, mobDir, attackDirX, attackDirY,
				attackProgress, itemColor);
	}
	
	@Override
	public float getSwingRotationAngle(InventoryItem item, int dir) {
			return 60.0F;
	}

	@Override
	public float getSwingRotationOffset(InventoryItem item, int dir, float swingAngle) {
		float offset = super.getSwingRotationOffset(item, dir, swingAngle);		
		
		return item.getGndData().getBoolean("slash") ? offset + 30.0F : offset + 90.0F;
	}
	
	@Override
	public float getHitboxSwingAngle(InventoryItem item, int dir) {
			return 60.0F;	
	}
	
	@Override
	public float getHitboxSwingAngleOffset(InventoryItem item, int dir, float swingAngle) {
		return item.getGndData().getBoolean("slash") ? 30.0F : 90.0F;	
	}
	
	@Override
	public boolean getAnimInverted(InventoryItem item) {
		return super.getAnimInverted(item);
	}
	
	public void showAxeAttack(Level level, final AttackAnimMob mob, final int seed, final InventoryItem item) {
	    

	    level.entityManager.addLevelEventHidden(new SwordCleanSliceAttackEvent(mob, seed, 250, this) {
	        
	        private static final int TRAIL_LIFETIME_TICKS = 250;
	        private static final int MIN_TRAIL_RANGE = 10;
	        private static final int DISTANCE_PER_TRAIL = 5;
	        private static final float THICKNESS_MULTIPLIER = 2.0F;
	        private static final int BASE_OFFSET = 3;
	        
	        private Trail[] trails = null;

	        public void tick(float angle, float currentAttackProgress) {
	            int attackRange = TheJadeWaraxe.this.getAttackRange(item);
	            Point2D.Float base = new Point2D.Float(mob.x, mob.y);

	            int attackDir = mob.getDir();
	            if (attackDir == 0) {
	                base.x += BASE_OFFSET;
	            } else if (attackDir == 2) {
	                base.x -= BASE_OFFSET;
	            }

	            angle = (Float) TheJadeWaraxe.this.getSwingDirection(item, mob).apply(currentAttackProgress);

	            Point2D.Float dir = GameMath.getAngleDir(angle);
	            int sliceDirOffset = TheJadeWaraxe.this.getAnimInverted(item) ? -90 : 90;
	            if (attackDir == 3) {
	                sliceDirOffset = -sliceDirOffset;
	            }

	            Point2D.Float sliceDir = GameMath.getAngleDir(angle + (float) sliceDirOffset);

	            if (this.trails == null) {
	                initializeTrails(level, attackRange, base, dir, sliceDir, currentAttackProgress);
	            } else {
	                updateTrails(attackRange, base, dir, sliceDir, currentAttackProgress);
	            }
	        }

	        private void initializeTrails(Level level, int attackRange, Point2D.Float base, Point2D.Float dir, 
	                                      Point2D.Float sliceDir, float currentAttackProgress) {
	            int trailCount = Math.max(2, (attackRange - MIN_TRAIL_RANGE - 10) / DISTANCE_PER_TRAIL);
	            this.trails = new Trail[trailCount];

	           
	            for (int i = 0; i < trails.length; i++) {
	            	
	            	TrailVector nv = getVector(currentAttackProgress, attackRange, i, base, dir, sliceDir);	            	
	                Trail trail = new Trail(nv, 
	                                        level, new Color(182, 218, 220), TRAIL_LIFETIME_TICKS);	              
	                trails[i] = trail;
	                trail.removeOnFadeOut = false;
	                trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
	                level.entityManager.addTrail(trail);
	            }
	        }

	        private void updateTrails(int attackRange, Point2D.Float base, Point2D.Float dir, 
                    Point2D.Float sliceDir, float currentAttackProgress) {
					for (int i = 0; i < this.trails.length; i++) {
					  this.trails[i].addPoint(getVector(currentAttackProgress, attackRange, i, base, dir, sliceDir));
					}
			}


	        public TrailVector getVector(float currentAttackProgress, int attackRange, int index, 
	                                     Point2D.Float base, Point2D.Float dir, Point2D.Float sliceDir) {	       

	            float thickness = GameMath.lerp((float) index / (float) (this.trails.length - 1), 25.0F, 10.0F);
	            if (currentAttackProgress < 0.33F) {
	                thickness *= THICKNESS_MULTIPLIER * currentAttackProgress;
	            } else if (currentAttackProgress > 0.66F) {
	                thickness *= THICKNESS_MULTIPLIER * (1.0F - currentAttackProgress);
	            }

	            int distanceOffset = attackRange - index * DISTANCE_PER_TRAIL;
	            GameRandom random = new GameRandom((long) seed).nextSeeded(index + 5);
	            float xOffset = random.getFloatOffset(0.0F, 10.0F);
	            float yOffset = random.getFloatOffset(0.0F, 10.0F);

	            Point2D.Float edgePos = new Point2D.Float(base.x + dir.x * distanceOffset + xOffset,
	                                                      base.y + dir.y * distanceOffset + yOffset);
	            
	            return new TrailVector(edgePos.x, edgePos.y, sliceDir.x, sliceDir.y, thickness, 0.0F);
	        }

	        public void onDispose() {
	            super.onDispose();
	            if (this.trails != null) {
	                for (Trail trail : this.trails) {
	                    trail.removeOnFadeOut = true;
	                }
	            }
	        }
	    });
	}

	@Override
    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress) {
     /*   if (this.animInverted) {
            drawOptions.swingRotationInv(attackProgress);
        } else {
            drawOptions.swingRotation(attackProgress);
        }*/
    	drawOptions.rotation(this.getSwingRotation(item, drawOptions.dir, attackProgress) - 90.0F);
		//super.setDrawAttackRotation(item, drawOptions, attackDirX, attackDirY, attackProgress);
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item,
			int animAttack, int seed, GNDItemMap mapContent)  {
              
        super.showAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
        SoundManager.playSound(GameResources.swing2, SoundEffect.effect(attackerMob));
        if (level.isClient()) {
			this.showAxeAttack(level, attackerMob, seed, item);
        }
    }
  
    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "thejadewaraxevntip"));
        
        return tooltips;
    }
    
    @Override
  	public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight,
  			InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        
    	boolean isSlash = item.getGndData().getBoolean("slash");
		item.getGndData().setBoolean("slash", !isSlash);
    	if (animAttack == 0) {
	    	int animTime = this.getAttackAnimTime(item, attackerMob);
	    	
			ToolItemMobAbilityEvent event = new ToolItemMobAbilityEvent(attackerMob, seed, item, x - attackerMob.getX(),
					y - attackerMob.getY() + attackHeight, animTime, animTime, new HashMap<Integer, Long>());			
			level.entityManager.addLevelEventHidden(event);
			
			if (level.isServer() && attackerMob.isServer()) {
				level.getServer().network.sendToClientsWithEntityExcept(new PacketLevelEvent(event), event,
						attackerMob.getServer().getLocalServerClient());
			}
			
		
    	}
    	//super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);
        return item;
    }
    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker) {
		super.hitMob(item, event, level, target, attacker);	
		if( target.buffManager.hasBuff(VulpesNova.JADE_WAR_AXE_BLEED_VN.getID())){
			ActiveBuff currentBuff = target.buffManager.getBuff(VulpesNova.JADE_WAR_AXE_BLEED_VN.getID());
			currentBuff.addStack(30000, attacker);
		}
		else {
			target.addBuff(new ActiveBuff(VulpesNova.JADE_WAR_AXE_BLEED_VN, target, 30.0F, attacker), true);
		}
	}
   
}