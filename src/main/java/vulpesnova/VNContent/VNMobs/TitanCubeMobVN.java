package vulpesnova.VNContent.VNMobs;

import necesse.engine.CameraShake;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MobSpawnLocation;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ability.EmptyMobAbility;
import necesse.entity.mobs.ability.IntMobAbility;
import necesse.entity.mobs.ability.MobAbility;
import necesse.entity.mobs.ai.behaviourTree.AINode;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import necesse.entity.mobs.ai.behaviourTree.decorators.IsolateRunningAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.CollisionChaserAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.EscapeAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.TargetFinderAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.WandererAINode;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.bosses.bossAIUtils.AttackStageInterface;
import necesse.entity.mobs.hostile.bosses.bossAIUtils.AttackStageManagerNode;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TitanCubeMobVN extends HostileMob {

    // Loaded in vulpesnova.VulpesNova.initResources()
    public static GameTexture texture;
    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.4f, "shapeshardsvn", 3, 6)

    );
	protected float height;
	protected long wobbleTimerOffset;
	protected long jumpStartTime;
	protected int jumpAnimationTime;
	protected float jumpStartX;
	protected float jumpStartY;
	protected float jumpTargetX;
	protected float jumpTargetY;
	public final TitanJumpMobAbility jumpAbility;
	protected long squishStartTime;
	protected int squishAnimationTime;
	public CameraShake squishShake;
	public IntMobAbility squishLaunchAbility;
	public static GameDamage collisionDamage;
	public EmptyMobAbility flickSoundAbility;
	public EmptyMobAbility popSoundAbility;
	public long nextJumpTime;
	public long endTime;
	
    // MUST HAVE an empty constructor
    public TitanCubeMobVN() {
        super(600);
        setSpeed(15);
        setFriction(1);
        collisionDamage = new GameDamage(60);
        this.setKnockbackModifier(0.2F);
        this.jumpAbility = (TitanJumpMobAbility) this.registerAbility(new TitanJumpMobAbility());
        
        this.squishLaunchAbility = (IntMobAbility) this.registerAbility(new IntMobAbility() {
			protected void run(int value) {
				TitanCubeMobVN.this.squishStartTime = TitanCubeMobVN.this.getLocalTime();
				TitanCubeMobVN.this.squishAnimationTime = value;
				TitanCubeMobVN.this.squishShake = new CameraShake(TitanCubeMobVN.this.getLocalTime(),
				TitanCubeMobVN.this.squishAnimationTime, 50, 2.0F, 2.0F, true);
			}
		});
        
        this.flickSoundAbility = (EmptyMobAbility) this.registerAbility(new EmptyMobAbility() {
			protected void run() {
				if (TitanCubeMobVN.this.isClient()) {
					SoundManager.playSound(GameResources.slimesplash,
							SoundEffect.effect(TitanCubeMobVN.this).pitch(1.0F));
					SoundManager.playSound(GameResources.flick, SoundEffect.effect(TitanCubeMobVN.this).pitch(0.8F));
				}

			}
		});
        
		this.popSoundAbility = (EmptyMobAbility) this.registerAbility(new EmptyMobAbility() {
			protected void run() {
				if (TitanCubeMobVN.this.isClient()) {
					SoundManager.playSound(GameResources.pop,
							SoundEffect.effect(TitanCubeMobVN.this).volume(0.3F).pitch(0.5F).falloffDistance(1400));
				}

			}
		});     
        this.moveAccuracy = 20;
        collision = new Rectangle(-48, 32, 64, 64);
        hitBox = new Rectangle(-48, 32, 64, 64);
        selectBox = new Rectangle(-48, 32, 64, 64);
    }

    @Override
    public void init() {
        super.init();
        // Setup AI
        this.ai =  new BehaviourTreeAI<TitanCubeMobVN>(this, new TitanCubeAI<TitanCubeMobVN>(600,40000, new GameDamage(20), ()->{
			return !this.getLevel().isCave && this.getLevel().getServer().world.worldEntity.isNight();
		}));      

    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        MobSpawnLocation location = (new MobSpawnLocation(this, targetX, targetY))
        		.checkMobSpawnLocation().checkMaxHostilesAround(10, 25, client);
        
        if (this.getLevel().isCave) {
            location = location.checkLightThreshold(client);
        } else {
            location = location.checkMaxStaticLightThreshold(10)
            		.checkMinLightThreshold(50);
        }

        return location.validAndApply();
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        // Spawn flesh debris particles
        for (int i = 0; i < 3; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(), texture,
                    GameRandom.globalRandom.nextInt(5), // Randomize between the debris sprites
                    8,
                    64,
                    x, y, 20f, // Position
                    knockbackX, knockbackY // Basically start speed of the particles
            ), Particle.GType.IMPORTANT_COSMETIC);
        }
    }


    @Override
	protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList,
			Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
		
		super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
		GameLight light = level.getLightLevel(x / 32, y / 32);
		float widthPercent = GameUtils
				.getAnimFloatContinuous(this.getWorldEntity().getLocalTime() - this.wobbleTimerOffset, 600);
		float heightPercent = GameUtils
				.getAnimFloatContinuous(this.getWorldEntity().getLocalTime() - this.wobbleTimerOffset + 200L, 600);
		long timeSinceSquishStart;
		float jumpPercentProgress;
		float compressProgress;
		
		if (this.jumpStartTime != 0L) {
			timeSinceSquishStart = this.getLocalTime() - this.jumpStartTime;
			jumpPercentProgress = GameMath.limit((float) timeSinceSquishStart / (float) this.jumpAnimationTime, 0.0F,
					1.0F);
			if (jumpPercentProgress < 0.4F) {
				compressProgress = jumpPercentProgress / 0.4F;
				widthPercent = 1.0F - compressProgress;
				heightPercent = 1.0F;
			} else if (jumpPercentProgress < 0.8F) {
				compressProgress = GameMath.limit((jumpPercentProgress - 0.4F) / 0.4F, 0.0F, 1.0F);
				widthPercent = compressProgress;
				heightPercent = 1.0F - compressProgress;
			} else {
				compressProgress = GameMath.limit((jumpPercentProgress - 0.8F) / 0.2F, 0.0F, 1.0F);
				widthPercent = 1.0F;
				heightPercent = compressProgress;
			}
		} else if (this.squishStartTime != 0L) {
			timeSinceSquishStart = this.getLocalTime() - this.squishStartTime;
			int expandTime = Math.min(150, this.squishAnimationTime / 2);
			if (timeSinceSquishStart < (long) (this.squishAnimationTime - expandTime)) {
				if (!this.squishShake.isOver(this.getLocalTime())) {
					Point2D.Float shake = this.squishShake.getCurrentShake(this.getLocalTime());
					x = (int) ((float) x + shake.x);
					y = (int) ((float) y + shake.y);
				}

				compressProgress = GameMath.limit(
						(float) timeSinceSquishStart / (float) (this.squishAnimationTime - expandTime), 0.0F, 1.0F);
				heightPercent = GameMath.lerp(compressProgress, 1.0F, -1.0F);
				widthPercent = 1.0F;
			} else {
				compressProgress = GameMath
						.limit((float) (timeSinceSquishStart - (long) (this.squishAnimationTime - expandTime))
								/ (float) expandTime, 0.0F, 1.0F);
				heightPercent = GameMath.lerp(compressProgress, -1.0F, 1.0F);
				widthPercent = GameMath.lerp(compressProgress, 1.0F, 0.0F);
			}
		}

		float widthFloat = GameMath.lerp(widthPercent, 0.9F, 1.1F);
		int width = (int) (128.0F * widthFloat);
		jumpPercentProgress = GameMath.lerp(heightPercent, 0.9F, 1.1F);
		int height = (int) (128.0F * jumpPercentProgress);
		int drawX = camera.getDrawX(x) - width / 2;
		int drawY = camera.getDrawY(y) - 10 + (128 - height);
		
		drawY += this.getBobbing(x, y);
	    drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
	    
	    Point sprite = this.getAnimSprite(x, y, this.getDir());

     
       
		final DrawOptions body = texture.initDraw().sprite(sprite.x, sprite.y, 128).light(light).size(width, height)
				.pos(drawX, drawY - (int) this.height);
		
		
		float shadowSize = 1.0F - GameMath.limit(this.height / 700.0F, 0.0F, 0.8F);
		int shadowWidth = (int) ((float) width * shadowSize);
		int shadowHeight = (int) ((float) height * shadowSize);
		int shadowDrawX = camera.getDrawX(x) - shadowWidth / 2;
		int shadowDrawY = camera.getDrawY(y) - 10 + (128 - shadowHeight);
		
		TextureDrawOptions shadowOptions = Textures.motherSlime.shadow.initDraw().sprite(0, 0, 256).light(light)
				.size(shadowWidth, shadowHeight).pos(shadowDrawX, shadowDrawY);				
		list.add(new MobDrawable() {
			public void draw(TickManager tickManager) {
				body.draw();
			}
		});
		tileList.add((tm) -> {
			shadowOptions.draw();
		});
	}

    @Override
	public int getFlyingHeight() {
		return (int) this.height;
	}
    @Override
    public int getRockSpeed() {
    	
        // Change the speed at which this mobs animation plays
        return 25;
    }
    
    @Override
    public boolean canBePushed(Mob other) {
    	return false;
    }
    
	public GameDamage getCollisionDamage(Mob target) {
		return collisionDamage;
	}

	public int getCollisionKnockback(Mob target) {
		return 150;
	}

	public boolean canCollisionHit(Mob target) {
		return target instanceof PlayerMob && this.height < 20.0F;
	}

    @Override
    public boolean isLavaImmune() {
        return true;
    }
    
    @Override
    public boolean canBeHit(Attacker attacker) {
		return this.height > 50.0F ? false : super.canBeHit(attacker);
	}
    
    public class TitanJumpMobAbility extends MobAbility {
		public void runAndSend(float targetX, float targetY, int animationTime) {
			Packet content = new Packet();
			PacketWriter writer = new PacketWriter(content);
			writer.putNextFloat(targetX);
			writer.putNextFloat(targetY);
			writer.putNextInt(animationTime);
			this.runAndSendAbility(content);
		}

		public void executePacket(PacketReader reader) {
			TitanCubeMobVN.this.jumpStartTime = TitanCubeMobVN.this.getLocalTime();
			TitanCubeMobVN.this.jumpStartX = TitanCubeMobVN.this.x;
			TitanCubeMobVN.this.jumpStartY = TitanCubeMobVN.this.y;
			TitanCubeMobVN.this.jumpTargetX = reader.getNextFloat();
			TitanCubeMobVN.this.jumpTargetY = reader.getNextFloat();
			TitanCubeMobVN.this.jumpAnimationTime = reader.getNextInt();
			if (!TitanCubeMobVN.this.isServer()) {
				SoundManager.playSound(GameResources.slimesplash, SoundEffect.effect(TitanCubeMobVN.this).pitch(0.6F));
			}

		}
	}

    public void tickMovement(float delta) {
    	
		long timeSinceSquishStart;
		if (this.jumpStartTime != 0L) {
			timeSinceSquishStart = this.getLocalTime() - this.jumpStartTime;
			float jumpPercentProgress = GameMath.limit((float) timeSinceSquishStart / (float) this.jumpAnimationTime,
					0.0F, 1.0F);
			if (timeSinceSquishStart <= (long) this.jumpAnimationTime) {
				float heightProgress = (float) Math.pow((double) jumpPercentProgress, 1.5);
				float moveProgress = (float) Math.pow((double) Math.min(jumpPercentProgress * 1.2F, 1.0F),
						0.800000011920929);
				this.x = GameMath.lerp(moveProgress, this.jumpStartX, this.jumpTargetX);
				this.y = GameMath.lerp(moveProgress, this.jumpStartY, this.jumpTargetY);
				this.height = (float) Math.sin(Math.PI * (double) heightProgress) * 140.0F;
			} else {
				this.spawnLandParticles();
				this.height = 0.0F;
				this.jumpStartTime = 0L;
				this.wobbleTimerOffset = this.getLocalTime() - 300L;
			}
		} else if (this.squishStartTime != 0L) {
			timeSinceSquishStart = this.getLocalTime() - this.squishStartTime;
			if (timeSinceSquishStart > (long) this.squishAnimationTime) {
				this.squishStartTime = 0L;
				this.wobbleTimerOffset = this.getLocalTime();
			}

			this.height = 0.0F;
		} else {
			
			this.height = 0.0F;
		}		
	
		super.tickMovement(delta);
	}
    
    public Mob getCurrentTarget() {
		return (Mob) this.ai.blackboard.getObject(Mob.class, "currentTarget");
	}
 	
    public void spawnLandParticles() {
		if (!this.isServer()) {
			int particles = 120;
			float anglePerParticle = 360.0F / (float) particles;

			for (int i = 0; i < particles; ++i) {
				int angle = (int) ((float) i * anglePerParticle
						+ GameRandom.globalRandom.nextFloat() * anglePerParticle);
				int startRange = GameRandom.globalRandom.getIntBetween(20, 40);
				float startX = this.x + (float) Math.sin(Math.toRadians((double) angle)) * (float) startRange;
				float startY = this.y + (float) Math.cos(Math.toRadians((double) angle)) * (float) startRange * 0.6F;
				float dx = (float) Math.sin(Math.toRadians((double) angle))
						* (float) GameRandom.globalRandom.getIntBetween(40, 60);
				float dy = (float) Math.cos(Math.toRadians((double) angle))
						* (float) GameRandom.globalRandom.getIntBetween(40, 60) * 0.6F;
				this.getLevel().entityManager
						.addParticle(startX, startY + 110.0F, i % 4 == 0 ? GType.IMPORTANT_COSMETIC : GType.COSMETIC)
						.colorRandom(new Color(244,12,0), 0.1F, 0.2F, 0.2F)
						.movesFriction(dx, dy, 0.8F)
						.heightMoves(0.0F, 50.0F).lifeTime(1000);
			}

			SoundManager.playSound(GameResources.magicbolt2, SoundEffect.effect(this).volume(0.7F).pitch(0.8F));
			SoundManager.playSound(GameResources.slimesplash, SoundEffect.effect(this).pitch(0.8F));
			this.getLevel().getClient().startCameraShake(this, 300, 40, 4.0F, 4.0F, true);
		}
	}

    public static class TitanCubeAI<T extends TitanCubeMobVN> extends SequenceAINode<T> {

    	public Supplier<Boolean> shouldEscape;
    	private int wanderFrequency;
    	public int randomMod = 0;
    	public String baseOptionsKey = "baseOptions";
    	private int jumpDistanceMax;
		public TitanCubeAI(int searchDistance, int wanderFrequency, GameDamage damage, final Supplier<Boolean> shouldEscape) {
			this.shouldEscape = shouldEscape;
			this.jumpDistanceMax = 300;
			this.addChild(new TargetFinderAINode<T>(searchDistance) {
				
				public GameAreaStream<? extends Mob> streamPossibleTargets(T mob, Point base,
						TargetFinderDistance<T> distance) {
					return TargetFinderAINode.streamPlayers(mob, base, distance);
				}

			});
			this.addChild(new WandererAINode<T>(wanderFrequency));
			AttackStageManagerNode<T> attackStages = new AttackStageManagerNode<T>();
			this.addChild(new IsolateRunningAINode<T>(attackStages));
			
			attackStages.addChild(new WaitForJumpDoneStage());			
			attackStages.addChild(new ChaseIdleStage((m) -> {
				return this.getIdleTime((T) m, 1000, 10000);
			}));
			
			attackStages.addChild(new JumpSlamStage());
			attackStages.addChild(new WaitForJumpDoneStage());
			attackStages.addChild(new ChaseIdleStage((m) -> {
				return this.getIdleTime((T) m, 3000, 10000);
			}));
			
			attackStages.addChild(new EscapeAINode<T>() {				
				public boolean shouldEscape(T mob, Blackboard<T> blackboard) {
					return shouldEscape != null && (Boolean) shouldEscape.get();					
				}
			});	
			//attackStages.addChild(new SquishLaunchStage());
		}

		public int getIdleTime(T mob, int minTime, int maxTime) {
			return GameMath.lerp(mob.getHealthPercent(), minTime, maxTime);
		}

		public Mob getCurrentTarget() {
			return (Mob) this.getBlackboard().getObject(Mob.class, "currentTarget");
		}
		
		public class ChaseIdleStage extends CollisionChaserAINode<T> implements AttackStageInterface<T> {
			
			public Function<T, Integer> idleTimeMSGetter;
			public int timer;

			public ChaseIdleStage(Function<T, Integer> idleTimeMSGetter) {
				super("currentTarget");
				this.idleTimeMSGetter = idleTimeMSGetter;
			
			}

			public ChaseIdleStage(int idleTimeMS) {
				this((m) -> {
					return idleTimeMS;
				});
			}

			public ChaseIdleStage(int noHealthIdleTime, int fullHealthIdleTime) {
				this((m) -> {
					int delta = fullHealthIdleTime - noHealthIdleTime;
					float healthPerc = (float) m.getHealth() / (float) m.getMaxHealth();
					return noHealthIdleTime + (int) ((float) delta * healthPerc);
				});
			}

			protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard) {
				super.onRootSet(root, mob, blackboard);
			}

			public void init(T mob, Blackboard<T> blackboard) {
				super.init(mob, blackboard);
			}

			public void onStarted(T mob, Blackboard<T> blackboard) {
				this.timer = 0;
			}

			public void onEnded(T mob, Blackboard<T> blackboard) {
			}

			public AINodeResult tick(T mob, Blackboard<T> blackboard) {	
				super.tick(mob, blackboard);
				this.timer += 50;
				
				float targetDistance = mob.getDistance(TitanCubeAI.this.getCurrentTarget());	
				
				return (this.timer >= (Integer) this.idleTimeMSGetter.apply(mob) && targetDistance <= TitanCubeAI.this.jumpDistanceMax) ? AINodeResult.SUCCESS : AINodeResult.RUNNING;
			}

			@Override
			public boolean attackTarget(T arg0, Mob arg1) {				
				return true;
			}
		}


		public class WaitForJumpDoneStage extends AINode<T> {
			protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard) {
			}

			public void init(T mob, Blackboard<T> blackboard) {
			}

			public AINodeResult tick(T mob, Blackboard<T> blackboard) {
				return mob.jumpStartTime == 0L ? AINodeResult.SUCCESS : AINodeResult.RUNNING;
			}
		}

		public class JumpSlamStage extends AINode<T> implements AttackStageInterface<T> {
			public long nextJumpTime;
			public long endTime;

			protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard) {
			}

			public void init(T mob, Blackboard<T> blackboard) {
			}

			public void onStarted(T mob, Blackboard<T> blackboard) {
				this.endTime = mob.getTime() + (long) GameMath.lerp(mob.getHealthPercent(), 5000, 8000);
				this.nextJumpTime = 0L;
			}

			public AINodeResult tick(T mob, Blackboard<T> blackboard) {
				if (this.endTime <= mob.getTime()) {
					return AINodeResult.SUCCESS;
				} else {
					if (this.nextJumpTime <= mob.getTime() && mob.jumpStartTime == 0L) {
						Mob target = TitanCubeAI.this.getCurrentTarget();
						if (target != null) {
						
							
								int randomXOffset = GameRandom.globalRandom.getIntBetween(-5, 5);
								int randomYOffset = GameRandom.globalRandom.getIntBetween(-5, 5);
								float exp = GameMath.expSmooth(mob.getHealthPercent(), 1.0F, 0.3F);
								int animationTime = GameMath.lerp(exp, 400, 1200);							
									
								float dxAdd = getPositionAfterMillis(target.dx, animationTime);
								float dyAdd = getPositionAfterMillis(target.dy, animationTime);
								
								Point2D.Float jumpTargetOffset = new Point2D.Float((target.x+dxAdd) - mob.x, (target.y+dyAdd) - mob.y - 48.0F);		
								
								float currentJumpDistance = (float) Math.sqrt(jumpTargetOffset.x * jumpTargetOffset.x + jumpTargetOffset.y * jumpTargetOffset.y);

								if (currentJumpDistance > TitanCubeAI.this.jumpDistanceMax) {
								    float scale = TitanCubeAI.this.jumpDistanceMax / currentJumpDistance;
								    jumpTargetOffset.x *= scale;
								    jumpTargetOffset.y *= scale;
								}
								
								mob.jumpAbility.runAndSend(	mob.x + jumpTargetOffset.x + (float) randomXOffset,
															mob.y + jumpTargetOffset.y + (float) randomYOffset, animationTime);
								int cooldownTime = GameMath.lerp(exp, 100, 1000);
								this.nextJumpTime = mob.getTime() + (long) animationTime + (long) cooldownTime;
						
						} else {
							this.nextJumpTime = mob.getTime() + 1000L;
						}
					}

					return AINodeResult.RUNNING;
				}
			}

			public void onEnded(T mob, Blackboard<T> blackboard) {
			}
		}
		
    }

}

