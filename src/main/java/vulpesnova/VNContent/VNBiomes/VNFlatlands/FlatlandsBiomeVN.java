package vulpesnova.VNContent.VNBiomes.VNFlatlands;

import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.sound.GameMusic;
import necesse.engine.sound.gameSound.GameSound;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;

import java.awt.*;

public class FlatlandsBiomeVN extends Biome {
    public static FishingLootTable cubeSurfaceFish;
    public static MobSpawnTable surfaceMobs;
    public static MobSpawnTable caveMobs;
    public static MobSpawnTable deepSnowCaveMobs;
    public static MobSpawnTable surfaceCritters;
    public static MobSpawnTable caveCritters;
    public static MobSpawnTable deepCaveCritters;
    public static LootItemInterface randomRoyalEggDrop;
    public static LootItemInterface randomIceCrownDrop;

    public FlatlandsBiomeVN() {
    	
    }
    
    
    
	@Override
    protected void loadRainTexture() {
        this.rainTexture = GameTexture.fromFile("rainfall");
    }
    
	@Override
    public Color getRainColor(Level level) {
        return new Color(255, 255, 255, 200);
    }
    
	@Override
    public void tickRainEffect(GameCamera camera, Level level, int tileX, int tileY, float rainAlpha) {
    }
    
	@Override
    public GameSound getRainSound(Level level) {
        return null;
    }
    
	@Override
    public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return new FlatlandsSurfaceLevelVN(islandX, islandY, islandSize, worldEntity);
    }
    
	@Override
    public Level getNewCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new FlatlandsCaveLevelVN(islandX, islandY, dimension, worldEntity);
    }
    
	@Override
    public Level getNewDeepCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
        return new FlatlandsDeepCaveLevelVN(islandX, islandY, dimension, worldEntity);
    }
    
	@Override
    public FishingLootTable getFishingLootTable(FishingSpot spot) {
        return !spot.tile.level.isCave ? cubeSurfaceFish : super.getFishingLootTable(spot);
    }
    
	@Override
    public MobSpawnTable getMobSpawnTable(Level level) {
        if (!level.isCave) {
            return surfaceMobs;
        } else {
            return level.getIslandDimension() == -2 ? deepSnowCaveMobs : caveMobs;
        }
    }
    
	@Override
    public MobSpawnTable getCritterSpawnTable(Level level) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 ? deepCaveCritters : caveCritters;
        } else {
            return surfaceCritters;
        }
    }
    
	@Override
    public LootTable getExtraMobDrops(Mob mob) {
        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
            if (mob.getLevel().getIslandDimension() == -1) {
                return new LootTable(new LootItemInterface[]{randomRoyalEggDrop, super.getExtraMobDrops(mob)});
            }

            if (mob.getLevel().getIslandDimension() == -2) {
                return new LootTable(new LootItemInterface[]{randomIceCrownDrop, super.getExtraMobDrops(mob)});
            }
        }

        return super.getExtraMobDrops(mob);
    }
    
	@Override
    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 
            		? new MusicList(new GameMusic[]{MusicRegistry.SecretsOfTheForest}) 
            		: new MusicList(new GameMusic[]{MusicRegistry.DepthsOfTheForest});
        } else {
            return level.getWorldEntity().isNight() 
            		? new MusicList(new GameMusic[]{MusicRegistry.getMusic("cubicwoods")}) 
            		: new MusicList(new GameMusic[]{MusicRegistry.getMusic("cubicwoods")});
        }
    }
    
	@Override
    public LootTable getExtraBiomeMobDrops(JournalRegistry.LevelType levelType) {
        LootTable lootTable = new LootTable();
        switch (levelType) {
            case CAVE:
                lootTable = new LootTable(new LootItemInterface[]{randomRoyalEggDrop});
                break;
            case DEEP_CAVE:
                lootTable = new LootTable(new LootItemInterface[]{randomIceCrownDrop});
        }

        return lootTable;
    }

    static {
        cubeSurfaceFish = (new FishingLootTable(defaultSurfaceFish)).addWater(120, "icefish");
        
        surfaceMobs = (new MobSpawnTable())
        		.add(15, "cubemobvn")
        		.add(3,"pyramidmobvn")
        		.add(3,"titancubemobvn")
        		.add(7,"spheresorcerermobvn")
        		.add(50,"spheresentinelmobvn")
        		.add(8,"planewalkermobvn");
        
        caveMobs = (new MobSpawnTable())
        		.add(100,"spheresorcerermobvn")
        		.add(100,"planewalkermobvn")
        		.add(100,"nightmarecubemobvn")
        		.add(10,"deadmahmobvn");
        
        deepSnowCaveMobs = (new MobSpawnTable()).add(120, "spheresorcerermobvn").add(70, "planewalkermobvn").add(25, "nightmarecubemobvn").add(50, "cryoflake").add(15, "deadmahmobvn");
        surfaceCritters = (new MobSpawnTable()).add(100, "snowhare").add(60, "bluebird").add(20, "bird").add(60, "duck");
        caveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(300, "cubaltcavelingvn");
        deepCaveCritters = (new MobSpawnTable()).include(Biome.defaultCaveCritters).add(100, "cubaltcavelingvn");
        randomRoyalEggDrop = new LootItemList(new LootItemInterface[]{new ChanceLootItem(0.005F, "portablegearcontactbeaconvn")});
        randomIceCrownDrop = new LootItemList(new LootItemInterface[]{new ChanceLootItem(0.004F, "portablegearcontactbeaconvn")});
    }
}
