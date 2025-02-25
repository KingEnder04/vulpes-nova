package vulpesnova.VNContent.VNMisc.AttackHandlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.Packet;
import necesse.engine.network.packet.PacketLevelEvent;
import necesse.engine.network.packet.PacketShowAttack;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry.Debuffs;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundPlayer;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.mobAbilityLevelEvent.KatanaDashLevelEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.MousePositionAttackHandler;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.SortedDrawable;
import necesse.gfx.ui.HUD;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.KatanaToolItem;
import necesse.level.maps.hudManager.HudDrawElement;
import vulpesnova.VulpesNova;
import vulpesnova.VNContent.VNWeapons.VNMagic.CrimsonTempestVN;

public class CrimsonTempestAttackHandler extends MousePositionAttackHandler {
	public int chargeTime;
	public boolean fullyCharged;
	public boolean minimumCharged;
	public CrimsonTempestVN chargeItem;
	public long startTime;
	public InventoryItem item;
	public int seed;
	public Color particleColors;
	public boolean endedByInteract;
	public SoundPlayer chargingSound;
	protected HudDrawElement hudDrawElement;

	public CrimsonTempestAttackHandler(PlayerMob player, PlayerInventorySlot slot, InventoryItem item,
			CrimsonTempestVN katanaItem, int chargeTime, Color particleColors, int seed) {
		
		super(player, slot, 20);
		this.item = item;
		this.chargeItem = katanaItem;
		this.chargeTime = chargeTime;
		this.particleColors = particleColors;
		this.seed = seed;
		this.startTime = player.getWorldEntity().getLocalTime();
		this.minimumCharged = false;
		this.chargingSound = SoundManager.playSound(VulpesNova.ELECTRIC_CHARGE, SoundEffect.effect(player).volume(0.5F));
	}

	public long getTimeSinceStart() {
		return this.player.getWorldEntity().getLocalTime() - this.startTime;
	}

	public float getChargePercent() {
		return (float) this.getTimeSinceStart() / (float) this.chargeTime;
	}

	public void onUpdate() {
		
		super.onUpdate();
		
		if (this.player.isClient() && this.hudDrawElement == null) {
			this.hudDrawElement = this.player.getLevel().hudManager.addElement(new HudDrawElement() {
				
				public void addDrawables(List<SortedDrawable> list, GameCamera camera, PlayerMob perspective) {
					if (CrimsonTempestAttackHandler.this.player.getAttackHandler() != CrimsonTempestAttackHandler.this) {
						this.remove();
					} else {
						
						

					}
				}
			});
			
		}

		if(this.chargingSound != null && this.chargingSound.isDone()) {
			this.chargingSound.startOver();
		}
		
		float chargePercent = this.getChargePercent();
		InventoryItem showItem = this.item.copy();
		
		showItem.getGndData().setFloat("chargePercent", chargePercent);
		showItem.getGndData().setBoolean("chargeUp", true);
		
		Packet attackContent = new Packet();
		
		this.player.showAttack(showItem, this.lastX, this.lastY, this.seed, attackContent);
		if (this.player.isServer()) {
			
			ServerClient client = this.player.getServerClient();			
			this.player.getServer().network.sendToClientsWithEntityExcept(
					new PacketShowAttack(this.player, showItem, this.lastX, this.lastY, this.seed, attackContent),
					this.player, client);
			
		}

		
		
		if (chargePercent >= 1.0F && !this.fullyCharged) {
			this.fullyCharged = true;
			this.chargingSound = SoundManager.playSound(VulpesNova.ELECTRIC_CHARGE_COMPLETE, SoundEffect.effect(player).volume(0.5F));
			if (this.player.isClient()) {
				int particles = 35;
				float anglePerParticle = 360.0F / (float) particles;
				ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(
						new Particle.GType[]{GType.CRITICAL, GType.IMPORTANT_COSMETIC, GType.COSMETIC});

				for (int i = 0; i < particles; ++i) {
					
					int angle = (int) ((float) i * anglePerParticle
							+ GameRandom.globalRandom.nextFloat() * anglePerParticle);
					
					float dx = (float) Math.sin(Math.toRadians((double) angle))
							* (float) GameRandom.globalRandom.getIntBetween(30, 70);
					
					float dy = (float) Math.cos(Math.toRadians((double) angle))
							* (float) GameRandom.globalRandom.getIntBetween(30, 70) * 0.8F;
					
					this.player.getLevel().entityManager.addParticle(this.player, typeSwitcher.next())
							.movesFriction(dx, dy, 0.8F).color(this.particleColors).heightMoves(0.0F, 30.0F)
							.lifeTime(500);
				}

				
			}
		}
		else if (chargePercent >= 0.25F && !this.minimumCharged) {
			this.minimumCharged = true;
			
			if (this.player.isClient()) {
				int particles = 5;
				float anglePerParticle = 360.0F / (float) particles;
				ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(
						new Particle.GType[]{GType.CRITICAL, GType.IMPORTANT_COSMETIC, GType.COSMETIC});

				for (int i = 0; i < particles; ++i) {
					
					int angle = (int) ((float) i * anglePerParticle
							+ GameRandom.globalRandom.nextFloat() * anglePerParticle);
					
					float dx = (float) Math.sin(Math.toRadians((double) angle))
							* (float) GameRandom.globalRandom.getIntBetween(30, 50);
					
					float dy = (float) Math.cos(Math.toRadians((double) angle))
							* (float) GameRandom.globalRandom.getIntBetween(30, 50) * 0.8F;
					
					this.player.getLevel().entityManager.addParticle(this.player, typeSwitcher.next())
							.movesFriction(dx, dy, 0.8F).color(this.particleColors).heightMoves(0.0F, 30.0F)
							.lifeTime(500);
				}

				
			}
		}

	}

	public void onMouseInteracted(int levelX, int levelY) {
		this.endedByInteract = true;
		this.player.endAttackHandler(false);
	}

	public void onControllerInteracted(float aimX, float aimY) {
		this.endedByInteract = true;
		this.player.endAttackHandler(false);
	}

	public void onEndAttack(boolean bySelf) {
		
		float chargePercent = Math.min(this.getChargePercent(),1.0F);
		if(this.chargingSound != null && this.chargingSound.isPlaying()) {
			this.chargingSound.dispose();
		}
		if (!this.endedByInteract && chargePercent >= 0.25F) {
			
			this.player.constantAttack = true;
			InventoryItem attackItem = this.item.copy();			
			attackItem.getGndData().setFloat("chargePercent", chargePercent);
			
			Packet attackContent = new Packet();
			this.player.showAttack(attackItem, this.lastX, this.lastY, this.seed, attackContent);
			
			if (this.player.isServer()) {
				
				ServerClient client = this.player.getServerClient();
				this.player.getServer().network.sendToClientsWithEntityExcept(
						new PacketShowAttack(this.player, attackItem, this.lastX, this.lastY, this.seed, attackContent),
						this.player, client);
			}

			this.chargeItem.doChargedAttack(this.player, attackItem, chargePercent);		
			
		
		}

		if (this.hudDrawElement != null) {
			this.hudDrawElement.remove();
		}

	}

	
}