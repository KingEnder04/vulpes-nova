package vulpesnova.VNContent.VNMisc;

import java.awt.Color;
import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;
import vulpesnova.VulpesNova;

public class MonsterPheromonesItem extends ConsumableItem {
	
    public MonsterPheromonesItem() {
        super(1, false);
        this.attackAnimTime.setBaseValue(300);
        this.rarity = Rarity.RARE;
        this.itemCooldownTime.setBaseValue(2000);
        this.worldDrawSize = 32;
    }

    @Override
    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress) {
        drawOptions.swingRotationInv(attackProgress);
    }

    @Override
	public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent) {
        return null;
    }

    @Override
    public boolean shouldSendToOtherClients(Level level, int x, int y, PlayerMob player, InventoryItem item,
			String error, GNDItemMap mapContent) {
        return error == null;
    }

    @Override
	public void onOtherPlayerPlace(Level level, int x, int y, PlayerMob player, InventoryItem item,
			GNDItemMap mapContent) {
        SoundManager.playSound(GameResources.shake, SoundEffect.effect(player));
    }

    @Override
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item,
			GNDItemMap mapContent) {
        if (this.singleUse) {
            item.setAmount(item.getAmount() - 1);
        }

        if (level.isServer()) {
            player.buffManager.addBuff(new ActiveBuff(VulpesNova.MONSTER_PHEROMONE_BUFF_VN, player, 300.0F, (Attacker)null), true);
        } else if (level.isClient()) {
            SoundManager.playSound(GameResources.shake, SoundEffect.effect(player));
        }

        return item;
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob mob, int attackHeight, InventoryItem item,  int animAttack, int seed, GNDItemMap map) {
        super.showAttack(level, x, y, mob, attackHeight, item, animAttack, seed, map);

        for(int i = 0; i < 20; ++i) {
            level.entityManager.addParticle(mob.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), mob.y + 2.0F + (float)(GameRandom.globalRandom.nextGaussian() * 4.0), GType.IMPORTANT_COSMETIC).movesConstant(mob.dx / 2.0F, mob.dy / 2.0F).color(new Color(133, 59, 225)).heightMoves(36.0F, 4.0F).lifeTime(750);
        }

    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "monsterpheromonesvntip"));
        tooltips.add(Localization.translate("itemtooltip", "infiniteuse"));
        return tooltips;
    }

}