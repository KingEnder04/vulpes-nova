package vulpesnova.VNContent.VNBuffs.VNTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.MinersProstheticTrinketBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolDamageItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CalmingMinersExoskeletonVNBuff extends TrinketBuff {
    public CalmingMinersExoskeletonVNBuff() {
    }
    
	@Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MOB_SPAWN_RATE, -0.4f);
        buff.setModifier(BuffModifiers.TOOL_DAMAGE, 1.0F);
        buff.setModifier(BuffModifiers.MINING_SPEED, 1.0F);
        buff.setModifier(BuffModifiers.MINING_RANGE, 1.0F);
        buff.setModifier(BuffModifiers.BUILDING_SPEED, 1.0F);
        buff.setModifier(BuffModifiers.BUILD_RANGE, 1.0F);
        buff.setModifier(BuffModifiers.ITEM_PICKUP_RANGE, 5.0F);
        buff.setModifier(BuffModifiers.SPELUNKER, true);
        buff.setModifier(BuffModifiers.TREASURE_HUNTER, true);
        eventSubscriber.subscribeEvent(MobObjectDamagedEvent.class, (event) -> {
            if (event.level.isServer()) {
                if (event.totalDamage > 0) {
                    if (event.attacker instanceof ToolDamageItem.ToolDamageItemAttacker) {
                        int centerTileX = event.result.getTileX();
                        int centerTileY = event.result.getTileY();
                        int hitX = event.result.showEffects ? event.result.mouseX : centerTileX * 32 + 16;
                        int hitY = event.result.showEffects ? event.result.mouseY : centerTileY * 32 + 16;
                        if (this.isValidObject(event.result.levelObject.object)) {
                            Point2D.Float hitDir = GameMath.normalize((float)hitX - buff.owner.x, (float)hitY - buff.owner.y);
                            float hitAngle = GameMath.getAngle(hitDir);
                            float angleOffset = 120.0F;
                            int arms = 2;
                            PlayerMob player = event.attacker.getFirstPlayerOwner();
                            ServerClient client = player != null && player.isServerClient() ? player.getServerClient() : null;
                            CollisionFilter collisionFilter = (new CollisionFilter()).customAdder((tp, rectangles) -> {
                                rectangles.add(new Rectangle(tp.tileX * 32, tp.tileY * 32, 32, 32));
                            }).addFilter((tp) -> {
                                return this.isValidObject(tp.object().object);
                            });
                            HashMap<Point, Integer> damageDealt = new HashMap();
                            damageDealt.put(new Point(centerTileX, centerTileY), event.totalDamage);

                            for(int i = 0; i < arms; ++i) {
                                float currentAngle = GameRandom.globalRandom.getFloatOffset(hitAngle, angleOffset / 2.0F);
                                float range = 64.0F;
                                Point2D.Float currentDir = GameMath.getAngleDir(currentAngle);
                                Line2D.Float line = new Line2D.Float((float)hitX, (float)hitY, (float)hitX + currentDir.x * range, (float)hitY + currentDir.y * range);
                                ArrayList<LevelObjectHit> collisions = event.level.getCollisions(line, collisionFilter);
                                int damage = Math.max(1, (int)((float)event.totalDamage * GameRandom.globalRandom.getFloatBetween(0.3F, 1.0F)));
                                Iterator var22 = collisions.iterator();

                                while(var22.hasNext()) {
                                    LevelObjectHit collision = (LevelObjectHit)var22.next();
                                    int currentDamageDealt = (Integer)damageDealt.getOrDefault(collision.getPoint(), 0);
                                    if (currentDamageDealt < event.totalDamage) {
                                        int finalDamage = Math.min(event.totalDamage - currentDamageDealt, damage);
                                        damageDealt.put(collision.getPoint(), currentDamageDealt + finalDamage);
                                        event.level.entityManager.doObjectDamage(event.result.objectLayerID, collision.tileX, collision.tileY, finalDamage, event.toolTier, new MinersProstheticTrinketBuff.MinersProstheticAttacker(buff.owner), client, event.result.showEffects, collision.tileX * 32 + 16, collision.tileY * 32 + 16);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        });
    }

    public boolean isValidObject(GameObject object) {
        return object.isRock;
    }

}
