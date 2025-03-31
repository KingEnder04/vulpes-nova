package vulpesnova.VNContent.VNBiomes.VNFlatlands;

import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.network.server.Server;
import necesse.engine.registries.JournalRegistry;
import necesse.engine.registries.MobRegistry;
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
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;

import java.awt.Color;

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
    
    
    public float getWindModifier(Level level, int tileX, int tileY) {
		return level.isCave ? 0.1F : super.getWindModifier(level, tileX, tileY);
	}

	public Color getWindColor(Level level) {
		return level.getIslandDimension() == -1 ? new Color(177, 182, 255) : super.getWindColor(level);
	}
	
	@Override
    protected void loadRainTexture() {
        this.rainTexture = GameTexture.fromFile("rainfall");
    }
    
	@Override
    public Color getRainColor(Level level) {
		
        return new Color(177, 182, 255, 200);
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
		
		LootTable result = new LootTable();
		
        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {   	        	   
        	result.items.add(new ChanceLootItem(.005F, "portablegearcontactbeaconvn"));        
        }
        
        if(mob.isBoss()) {        	
        	result.items.add(new ChanceLootItem(.1F, "awakeninatorvn"));  	
        }
        
        if(mob.getID() == MobRegistry.getMobID("titancubemobvn")) {
        	result.items.add(new ChanceLootItem(.3F, "novashardvn"));        
        }
        
        return result;
    }
    
	@Override
    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        if (level.isCave) {
            return level.getIslandDimension() == -2 
            		? new MusicList(new GameMusic[]{MusicRegistry.SecretsOfTheForest}) 
            		: new MusicList(new GameMusic[]{MusicRegistry.DepthsOfTheForest});
        } else {
            return level.getWorldEntity().isNight() 
            		? new MusicList(new GameMusic[]{MusicRegistry.getMusic("salzberry_ftt")}) 
            		: new MusicList(new GameMusic[]{MusicRegistry.getMusic("cubicwoods")});
        }
    }
    
	@Override
    public LootTable getExtraBiomeMobDrops(JournalRegistry.LevelType levelType) {
		
        LootTable lootTable = new LootTable();
        
        lootTable.items.add(new ChanceLootItem(1.0F, "novafragmentvn"));
        lootTable.items.add(new ChanceLootItem(.08F, "novashardvn"));
         
        return lootTable;
    }

    static {
        cubeSurfaceFish = (new FishingLootTable(defaultSurfaceFish))
        		.addWater(120, "icefish");
        
        surfaceMobs = (new MobSpawnTable())
        		.add(15, "cubemobvn")
        		//.add(3,"pyramidmobvn")
        		.add(3,"titancubemobvn")
        		.add(7,"spheresorcerermobvn")
        		.add(50,"spheresentinelmobvn")
        		.add(8,"planewalkermobvn");
        
        caveMobs = (new MobSpawnTable())
        		//.add(100,"spheresorcerermobvn")
        		//.add(100,"planewalkermobvn")
        		.add(100,"nightmarecubemobvn")
        		.add(10,"deadmahmobvn");
        
        deepSnowCaveMobs = (new MobSpawnTable())
        		.add(120, "spheresorcerermobvn")
        		.add(70, "planewalkermobvn")
        		.add(25, "nightmarecubemobvn")
        		.add(50, "cryoflake")
        		.add(15, "deadmahmobvn");
       
        surfaceCritters = (new MobSpawnTable())
        		.add(30, "snowhare")
        		.add(60, "bluebird")
        		.add(20, "bird");
        
        caveCritters = (new MobSpawnTable())
        		.include(Biome.defaultCaveCritters)
        		.add(300, "cubaltcavelingvn");
        
        deepCaveCritters = (new MobSpawnTable())
        		.include(Biome.defaultCaveCritters)
        		.add(100, "cubaltcavelingvn");
        
     
    }
}
