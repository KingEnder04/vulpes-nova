package vulpesnova.VNContent.VNWeapons.VNMelee;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.util.GameBlackboard;
import necesse.entity.levelEvent.GlaiveShowAttackEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.glaiveToolItem.GlaiveToolItem;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Point2D;

public class IronsoulGlaiveVN extends GlaiveToolItem {
    public IronsoulGlaiveVN() {
        super(1100);
        this.rarity = Rarity.EPIC;
        this.animSpeed = 1200;
        this.attackDamage.setBaseValue(53.0F).setUpgradedValue(1.0F, 103.0F);
        this.attackRange.setBaseValue(160);
        this.knockback.setBaseValue(100);
        this.width = 20.0F;
        this.attackXOffset = 45;
        this.attackYOffset = 45;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "ironsoulglaivevntip"));
        return tooltips;
    }
    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker) {
        super.hitMob(item, event, level, target, attacker);
        target.buffManager.addBuff(new ActiveBuff("cosmicfirevn", target, 2000, attacker), true);
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        super.showAttack(level, x, y, mob, attackHeight, item, seed, contentReader);
        if (level.isClientLevel()) {
            level.entityManager.addLevelEventHidden(new GlaiveShowAttackEvent(mob, x, y, seed, 12.0F) {
                public void tick(float angle) {
                    Point2D.Float angleDir = this.getAngleDir(angle);
                    this.level.entityManager.addParticle(this.attackMob.x + angleDir.x * 40.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y + angleDir.y * 40.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), Particle.GType.COSMETIC).color(new Color(71, 98, 232)).minDrawLight(150).givesLight(45.0F, 1.0F).lifeTime(400);
                    this.level.entityManager.addParticle(this.attackMob.x - angleDir.x * 40.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y - angleDir.y * 40.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), Particle.GType.COSMETIC).color(new Color(211, 28, 83)).minDrawLight(150).givesLight(45.0F, 1.0F).lifeTime(400);
                }
            });
        }

    }
}
