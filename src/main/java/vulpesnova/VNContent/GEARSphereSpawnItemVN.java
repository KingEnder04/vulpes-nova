package vulpesnova.VNContent;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.snow.SnowBiome;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsBiomeVN;

import java.awt.geom.Line2D;


public class GEARSphereSpawnItemVN extends ConsumableItem {
    public GEARSphereSpawnItemVN() {
        super(1, true);
        this.itemCooldownTime.setBaseValue(2000);
        this.setItemCategory(new String[]{"consumable", "bossitems"});
        this.dropsAsMatDeathPenalty = true;
        this.keyWords.add("boss");
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
        this.incinerationTimeMillis = 30000;
    }


    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent) {
        if (level instanceof IncursionLevel) {
            return "inincursion";
        } else if (!level.isCave) {
            return "notcave";
        } else {
            int tileX;
            int tileY;
            if (player == null) {
                tileX = GameMath.getTileCoordinate(x);
                tileY = GameMath.getTileCoordinate(y);
            } else {
                tileX = player.getTileX();
                tileY = player.getTileY();
            }

            return level.getIdentifier().equals(LevelIdentifier.CAVE_IDENTIFIER) && level.getBiome(tileX, tileY) instanceof FlatlandsBiomeVN ? null : "notflatlands";
        }
    }

	@Override
    public InventoryItem onAttemptPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap map, String error) {
        if (level.isServer() && player != null && player.isServerClient() && error.equals("inincursion")) {
            player.getServerClient().sendChatMessage(new LocalMessage("misc", "cannotsummoninincursion"));
        }
        	
        return super.onAttemptPlace(level, x, y, player, item, map, error);
    }

    @Override
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item,
			GNDItemMap mapContent) {
        if (level.isServer()) {
            if (level instanceof IncursionLevel) {
                GameMessage summonError = ((IncursionLevel)level).canSummonBoss("gearspherebossmobvn");
                if (summonError != null) {
                    if (player != null && player.isServerClient()) {
                        player.getServerClient().sendChatMessage(summonError);
                    }

                    return item;
                }
            }

            System.out.println("The GEAR Sphere has been summoned at " + level.getIdentifier() + ".");
            float angle = (float) GameRandom.globalRandom.nextInt(360);
            float nx = (float)Math.cos(Math.toRadians((double)angle));
            float ny = (float)Math.sin(Math.toRadians((double)angle));
            float distance = 960.0F;
            Mob mob = MobRegistry.getMob("gearspherebossmobvn", level);
            level.entityManager.addMob(mob, (float)(player.getX() + (int)(nx * distance)), (float)(player.getY() + (int)(ny * distance)));
            level.getServer().network.sendToClientsAtEntireLevel(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), level);
            if (level instanceof IncursionLevel) {
                ((IncursionLevel)level).onBossSummoned(mob);
            }
        }

        if (this.isSingleUse(player)) {
            item.setAmount(item.getAmount() - 1);
        }

        return item;
    }
    
	@Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "portablegearcontactbeaconvntip1"));
        tooltips.add(Localization.translate("itemtooltip", "portablegearcontactbeaconvntip2"));
        return tooltips;
    }
    
	@Override
    public String getTranslatedTypeName() {
        return Localization.translate("item", "relic");
    }
}
