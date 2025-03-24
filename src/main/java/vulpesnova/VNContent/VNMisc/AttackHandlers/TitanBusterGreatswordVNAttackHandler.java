package vulpesnova.VNContent.VNMisc.AttackHandlers;

import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordAttackHandler;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;
import vulpesnova.VNContent.VNMobs.VNPets.BabyTitanCubeMobVN;
import vulpesnova.VNContent.VNWeapons.VNMelee.TitanBusterGreatswordVN;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class TitanBusterGreatswordVNAttackHandler extends GreatswordAttackHandler {
	
    public TitanBusterGreatswordVNAttackHandler(TitanBusterGreatswordVN titanBusterGreatswordVN, ItemAttackerMob attacker, ItemAttackSlot slot, InventoryItem item, GreatswordToolItem toolItem, int seed, int startX, int startY, GreatswordChargeLevel... chargeLevels) {
        super(attacker, slot, item, toolItem, seed, startX, startY, chargeLevels);
    }

    @Override
    public void onEndAttack(boolean bySelf) {
        super.onEndAttack(bySelf);
        Point2D.Float dir = GameMath.getAngleDir(this.currentAngle);
        switch (this.currentChargeLevel) {
            case 2:
                this.summonCubeMob(dir);
            case 1:
                this.summonCubeMob(dir);
            default:
        }
    }

    private void summonCubeMob(Point2D.Float dir) {
    	
        if (this.attackerMob.getLevel().isServer()) {
        	
            ServerClient client = attackerMob.getServer().getLocalServerClient();
            BabyTitanCubeMobVN mob = new BabyTitanCubeMobVN();
            this.attackerMob.serverFollowersManager.addFollower("babytitancubemobvn", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 10, (BiConsumer<ItemAttackerMob, Mob>)null, false);
            Point2D.Float spawnPoint = this.findSpawnLocation(mob, this.attackerMob.getLevel(), client.playerMob);
            mob.updateDamage(this.toolItem.getAttackDamage(this.item));
            mob.setEnchantment(this.toolItem.getEnchantment(this.item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            this.attackerMob.getLevel().entityManager.addMob(mob, spawnPoint.x, spawnPoint.y);
            
        }

    }

    public Point2D.Float findSpawnLocation(AttackingFollowingMob mob, Level level, PlayerMob player) {
        return findSpawnLocation(mob, level, player.x, player.y);
    }

    public static Point2D.Float findSpawnLocation(Mob mob, Level level, float centerX, float centerY) {
        ArrayList<Point2D.Float> possibleSpawns = new ArrayList<Point2D.Float>();

        for(int cX = -1; cX <= 1; ++cX) {
            for(int cY = -1; cY <= 1; ++cY) {
                if (cX != 0 || cY != 0) {
                    float posX = centerX + (float)(cX * 32);
                    float posY = centerY + (float)(cY * 32);
                    if (!mob.collidesWith(level, (int)posX, (int)posY)) {
                        possibleSpawns.add(new Point2D.Float(posX, posY));
                    }
                }
            }
        }

        if (possibleSpawns.size() > 0) {
            return (Point2D.Float)possibleSpawns.get(GameRandom.globalRandom.nextInt(possibleSpawns.size()));
        } else {
            return new Point2D.Float(centerX, centerY);
        }
    }
}
