package vulpesnova;

import java.awt.Color;
import necesse.engine.journal.JournalEntry;
import necesse.engine.journal.MultiJournalChallenge;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.sound.GameMusic;
import necesse.engine.sound.gameSound.GameSound;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SimpleSetBonusBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.SimpleTrinketBuff;
import necesse.entity.mobs.gameDamageType.DamageType;
import necesse.entity.mobs.hostile.bosses.EvilsProtectorMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.armorItem.HelmetArmorItem;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.miscItem.VinylItem;
import necesse.inventory.item.placeableItem.StonePlaceableItem;
import necesse.inventory.item.placeableItem.consumableItem.food.FoodConsumableItem;
import necesse.inventory.item.placeableItem.followerSummonItem.petFollowerPlaceableItem.PetFollowerPlaceableItem;
import necesse.inventory.item.placeableItem.mapItem.BiomeMapItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.item.toolItem.axeToolItem.CustomAxeToolItem;
import necesse.inventory.item.toolItem.pickaxeToolItem.CustomPickaxeToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.inventory.item.toolItem.shovelToolItem.CustomShovelToolItem;
import necesse.inventory.item.trinketItem.CombinedTrinketItem;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.ConditionLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.presets.*;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.*;
import necesse.level.gameObject.furniture.StorageBoxInventoryObject;
import necesse.level.gameTile.SimpleFloorTile;
import necesse.level.maps.biomes.desert.DesertBiome;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.snow.SnowBiome;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.levelData.settlementData.settler.Settler;

import vulpesnova.VNContent.*;
import vulpesnova.VNContent.VNArmors.VNAncientJungleSet.AncientJungleBoots;
import vulpesnova.VNContent.VNArmors.VNAncientJungleSet.AncientJungleChestplate;
import vulpesnova.VNContent.VNArmors.VNAncientJungleSet.AncientJungleHelmet;
import vulpesnova.VNContent.VNArmors.VNChilledBloodplate.*;
import vulpesnova.VNContent.VNArmors.VNCubaltSet.CubaltBootsVN;
import vulpesnova.VNContent.VNArmors.VNCubaltSet.CubaltChestplateVN;
import vulpesnova.VNContent.VNArmors.VNCubaltSet.CubaltHelmetVN;
import vulpesnova.VNContent.VNArmors.VNMeatSet.MeatBoots;
import vulpesnova.VNContent.VNArmors.VNMeatSet.MeatHat;
import vulpesnova.VNContent.VNArmors.VNMeatSet.MeatRobe;
import vulpesnova.VNContent.VNArmors.VNStoneSet.StoneBoots;
import vulpesnova.VNContent.VNArmors.VNStoneSet.StoneChestplate;
import vulpesnova.VNContent.VNArmors.VNStoneSet.StoneHelmet;
import vulpesnova.VNContent.VNArmors.VNWindSet.WindBootsVN;
import vulpesnova.VNContent.VNArmors.VNWindSet.WindChestplateVN;
import vulpesnova.VNContent.VNArmors.VNWindSet.WindHatVN;
import vulpesnova.VNContent.VNArmors.VNWoodSet.WoodenBoots;
import vulpesnova.VNContent.VNArmors.VNWoodSet.WoodenChestplate;
import vulpesnova.VNContent.VNArmors.VNWoodSet.WoodenHelmet;

import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsBiomeVN;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsDeepCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.VNFlatlands.FlatlandsSurfaceLevelVN;
import vulpesnova.VNContent.VNBiomes.VNMinersHaven.MinersHavenBiomeVN;
import vulpesnova.VNContent.VNBiomes.VNMinersHaven.MinersHavenCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.VNMinersHaven.MinersHavenDeepCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.VNMinersHaven.MinersHavenSurfaceLevelVN;

import vulpesnova.VNContent.VNBuffs.BleedingBuff;
import vulpesnova.VNContent.VNBuffs.CosmicFireVNBuff;
import vulpesnova.VNContent.VNBuffs.CrimsonTempestChargeStackBuff;
import vulpesnova.VNContent.VNBuffs.VNTrinkets.FoxTokenVNBuff;
import vulpesnova.VNContent.VNBuffs.VNArmorBuffs.*;
import vulpesnova.VNContent.VNBuffs.MonsterPheromonesBuff;
import vulpesnova.VNContent.VNBuffs.VNTrinkets.VNJewelry.AmethystAmuletVNActiveBuff;
import vulpesnova.VNContent.VNBuffs.VNTrinkets.VNJewelry.AmethystAmuletVNCooldownBuff;
import vulpesnova.VNContent.VNBuffs.VNTrinkets.VNJewelry.AmethystAmuletVNTrinketBuff;
import vulpesnova.VNContent.VNBuffs.VNTrinkets.VNSeals.*;
import vulpesnova.VNContent.VNJournal.HarvestBlockBerriesInFlatlandsChallenge;
import vulpesnova.VNContent.VNJournal.KillPlanewalkerCrowdChallenge;
import vulpesnova.VNContent.VNJournal.KillTitancubesInFlatlandsChallenge;
import vulpesnova.VNContent.VNBuffs.WarAxeBleedingBuff;
import vulpesnova.VNContent.VNBuffs.VNTrinkets.*;
import vulpesnova.VNContent.VNMaterials.*;
import vulpesnova.VNContent.VNMisc.*;
import vulpesnova.VNContent.VNMobs.*;
import vulpesnova.VNContent.VNMobs.VNBosses.GEARSphereBossMobVN;
import vulpesnova.VNContent.VNMobs.VNBosses.GEARSphereMinionPodVN;
import vulpesnova.VNContent.VNMobs.VNPets.BabyTitanCubeMobVN;
import vulpesnova.VNContent.VNMobs.VNPets.PetSentientCrownVN;
import vulpesnova.VNContent.VNMobs.TitanCubeMobVN;
import vulpesnova.VNContent.VNMobs.VNPets.BabyCubeMobVN;
import vulpesnova.VNContent.VNProjectiles.*;
import vulpesnova.VNContent.VNWeapons.*;
import vulpesnova.VNContent.VNWeapons.VNMagic.*;
import vulpesnova.VNContent.VNWeapons.VNMelee.*;
import vulpesnova.VNContent.VNWeapons.VNRange.*;
import vulpesnova.VNContent.VNWeapons.VNRange.VNAmmos.*;
import vulpesnova.VNContent.VNWeapons.VNSummon.*;
import vulpesnova.helpers.RecipeHelper;

import static necesse.engine.registries.BiomeRegistry.registerBiome;
import static necesse.engine.registries.BuffRegistry.registerBuff;
import static necesse.engine.registries.ItemRegistry.registerItem;
import static necesse.engine.registries.MobRegistry.registerMob;
import static necesse.engine.registries.ObjectRegistry.getObject;
import static necesse.engine.registries.ObjectRegistry.registerObject;
import static necesse.engine.registries.ProjectileRegistry.registerProjectile;
import static necesse.engine.registries.RecipeTechRegistry.registerTech;
import static necesse.engine.registries.TileRegistry.registerTile;

@ModEntry
public class VulpesNova {
	//this is a very simple change to make sure its working again version 3	
    public static Tech TABLEOFAWAKENINGVN;
 
    public static GameSound COD_FLOPPIN ;
    public static GameSound ELECTRIC_EXPLOSION;
    public static GameSound ELECTRIC_SHOOT;
    public static GameSound ELECTRIC_CHARGE;
    public static GameSound ELECTRIC_CHARGE_COMPLETE;
    public static GameSound BLASTER1;
	public static GameSound GUNSHOT1;
	
    public static final int CUBALT_VN_TOOL_DPS = 150;

    public static int cubeSandVNID;
    public static int cubeMainLandVNID;
    public static int cubeStoneFloorVNID;
    public static int cubeStoneTiledFloorVNID;
    public static int cubeDeepStoneFloorVNID;
    public static int cubeDeepStoneTiledFloorVNID;
    public static int gearFactoryFloorVNID;
    public static int cubeWoodFloorVNID;

    public static Buff COSMIC_FIRE_VN;
    public static Buff MONSTER_PHEROMONE_BUFF_VN;
    public static Buff BLEEDING_BUFF_VN;
    public static Buff JADE_WAR_AXE_BLEED_VN;    
    public static Buff PROTECTOR_SEAL_VN_ACTIVE;
    public static Buff PROTECTOR_SEAL_VN_COOLDOWN;
    public static Buff RUINED_GOLEM_VN_ACTIVE;
    public static Buff RUINED_GOLEM_VN_COOLDOWN;
    public static Buff FLOWING_ENERGY_VN_ACTIVE;
    public static Buff FLOWING_ENERGY_VN_COOLDOWN;
    public static Buff SPEEDSTER_VN_ACTIVE;
    public static Buff SPEEDSTER_VN_COOLDOWN;
    public static Buff DEMON_WARRIOR_VN_ACTIVE;
    public static Buff DEMON_WARRIOR_VN_COOLDOWN;
    public static Buff HOLY_PALADIN_VN_ACTIVE;
    public static Buff HOLY_PALADIN_VN_COOLDOWN;
    public static Buff ARCHBISHOP_COWL_VN_ACTIVE;
    public static Buff ARCHBISHOP_COWL_VN_COOLDOWN;
    public static Buff AMETHYST_AMULET_VN_ACTIVE;
    public static Buff AMETHYST_AMULET_VN_COOLDOWN;
 
    public static Buff CRIMSON_TEMPEST_CHARGE_STACKS_BUFF;
    
    public static GameTexture GEARSPHEREbody;
    public static GameTexture GEARSPHEREhead;
    public static GameTexture GEARSPHEREleg;

    public static Biome FLATLANDS;
    public static Biome MINERSHAVEN;

    public static GameMusic HUBMUSICVN;
    public static GameMusic CUBELEVELMUSICVN;
    public static GameMusic CUBELEVELDEEPMUSICVN;
    
    public static int FLATLANDS_SURFACE_CHALLENGES_ID;
    public static LootTable FLATLANDS_SURFACE_REWARD = new LootTable(
			new LootItemInterface[]{new LootItem("sentientcrownvn")});

    public static int KILL_TITANCUBES_ID;
    public static int HARVEST_BLOCKBERRIES_ID;
    public static int KILL_PLANEWALKERS_CROWD_ID;
    
    public void preInit() {

    }

    // lol i have no idea what im doing
    // FOX THINGS
    
    //==========================================================
    //
    // Helpers: To increase readability and ease of maintenance.
    //
    //==========================================================
    
    // Helper method to register simple floor tiles
    private static int registerFloorTile(String id, Color color, float hardness) {
        return registerTile(id, new SimpleFloorTile(id, color), hardness, true);
    }
    
    protected static class RegisterWallObjectResult{
    	public int[] wallIDs;
    	public int columnID;
    	public RegisterWallObjectResult(int[] wallIDs, int columnID) {
    		this.wallIDs=wallIDs;
    		this.columnID=columnID;
    	}
    }
    // Helper method to register walls. It will also register the column object corresponding to the wall type.
    // Returns RegisterWallObjectResult
    private static VulpesNova.RegisterWallObjectResult registerWallObjects(String baseName, String wallName, Color color, float hardness, float blastResistance) {
        int[] wallIDs = WallObject.registerWallObjects(baseName+"vn", wallName, 0, color, hardness, blastResistance);
        WallObject wall = (WallObject) getObject(wallIDs[0]);
        return new RegisterWallObjectResult(wallIDs, registerObject(baseName + "columnvn", new ColumnObject(baseName + "columnvn", wall.mapColor, ToolType.ALL), 10.0F, true));
    }

    
    protected static class RegisterRockObjectResult{
    	public int baseRockID;
    	public int[] rockIDs;
    	public int[] smallRockIDs;
    	public RegisterRockObjectResult(int baseRockID, int[] rockIDs, int[] smallRockIDs) {
            this.baseRockID = baseRockID;
            this.rockIDs = rockIDs;
            this.smallRockIDs = smallRockIDs;
        }
    }
    
    // Helper method to register rock objects. It will also register the small rock variant. 
    // Returns: Two int IDs, the first for the normal rock, and the second for the small rock variant.
    
    private static RegisterRockObjectResult registerRockObjects(String rockID, Color color, String baseTile, int toolTier) {
        RockObject rock = new RockObject(rockID+"rockvn", color, baseTile);
        int r1 = registerObject(rockID+"rockvn", rock, 0.0F, false);
        rock.toolTier = toolTier;
        
        int[] r2 = SingleRockObject.registerSurfaceRock(rock, rockID + "groundrockvn", new Color(127, 127, 127), -1.0F, true);
        int[] r3 = SingleRockObject.registerSurfaceRock(rock, rockID + "groundrocksmallvn", new Color(127, 127, 127), -1.0F, true);
        
        return new RegisterRockObjectResult(r1, r2, r3);
    }
    
    //==========================================================
    
  
    public static GameMusic registerMusic(String id, String path, String name, Color color1, Color color2) {
    	GameMusic music_id = MusicRegistry.registerMusic(id, path,	new StaticMessage(name), color1, color2, null);
    	ItemRegistry.registerItem(id + "vinyl", new VinylItem(music_id), 50.0F, true, false);
    	return music_id;
    }
    
    public void init() {
        System.out.println("Hello and Welcome to Vulpes Nova!");

        GunProjectileToolItem.NORMAL_AMMO_TYPES.add("novabulletvn");
        GunProjectileToolItem.NORMAL_AMMO_TYPES.add("windbulletvn");

        // Use our helper methods to register Tiles.
        cubeSandVNID = registerFloorTile("cubesandtilevn", new Color(44, 50, 91), 2.0f);
        cubeMainLandVNID = registerTile("cubemainlandtilevn", new CubeMainLandTileVN(), 2.0f, true);
        cubeStoneFloorVNID = registerFloorTile("cubestonefloorvn", new Color(161, 89, 238), 2.0f);
        cubeDeepStoneFloorVNID = registerFloorTile("cubedeepstonefloorvn", new Color(126, 53, 204), 2.0f);
        cubeWoodFloorVNID = registerFloorTile("cubewoodfloorvn", new Color(117, 126, 197), 4.0f);
        cubeStoneTiledFloorVNID = registerFloorTile("cubestonetiledfloorvn", new Color(94, 53, 177), 2.0f);
        cubeDeepStoneTiledFloorVNID = registerFloorTile("cubedeepstonetiledfloorvn", new Color(76, 37, 154), 2.0f);
        gearFactoryFloorVNID = registerFloorTile("gearfactoryfloorvn", new Color(242, 240, 243), 2.0f);

        register_objects();
        
        FLATLANDS = registerBiome("flatlandsvn", new FlatlandsBiomeVN(), 100, true, "flatlandsvn");
        MINERSHAVEN = registerBiome("minershavenvn", new MinersHavenBiomeVN(), 10, true, "minershavenvn");
        
        register_items();

        register_mobs();
        
        registerItem("portablegearcontactbeaconvn", new GEARSphereSpawnItemVN(), 10,true);
        
       /* registerItem("importedcubevn", new ImportedAnimalSpawnItem(12, true, "cubemobvn"), 200.0F, true);
        registerItem("importedpyramidvn", new ImportedAnimalSpawnItem(12, true, "pyramidmobvn"), 200.0F, true);
        registerItem("importednightmarecubevn", new ImportedAnimalSpawnItem(12, true, "nightmarecubemobvn"), 200.0F, true);
        registerItem("importedfoxvn", new ImportedAnimalSpawnItem(1, true, "foxmobvn"), 200.0F, true);
        registerItem("importedluckychickenvn", new ImportedAnimalSpawnItem(1, true, "luckychickenmobvn"), 200.0F, true);
        registerItem("importedcavespidervn", new ImportedAnimalSpawnItem(12, false, "giantcavespider"), 200.0F, true);
        registerItem("importedgustvn", new ImportedAnimalSpawnItem(12, false, "gustmobvn"), 200.0F, true);*/

        register_projectiles();

        register_buffs();        
        
        register_levels();
        
        register_journal_entries();

       
        
        CUBELEVELMUSICVN = registerMusic("cubicwoods", "music/cubicwoods", "Cubic Woods", new Color(45, 154, 164), new Color(119, 74, 196));
        CUBELEVELDEEPMUSICVN = registerMusic("gearsturning", "music/gearsturning", "Gears Turning", new Color(45, 118, 164), new Color(24, 79, 141));
   
        TABLEOFAWAKENINGVN = registerTech("tableofawakeningvn", "tableofawakeningvn");
        
        
        LootTablePresets.globalMobDrops.items.add(new ChanceLootItem(0.02F,"novafragmentvn"));
        
        LootTablePresets.globalMobDrops.items.add(new ConditionLootItem("novashardvn", (r, o) -> {
			Mob self = (Mob) LootTable.expectExtra(Mob.class, o, 0);
			return self.isBoss();
		}));
        
        LootTablePresets.globalMobDrops.items.add(new ConditionLootItem("novashardvn", (r, o) -> { 	
			Mob self = (Mob) LootTable.expectExtra(Mob.class, o, 0);
			return r.getChance(0.08) ? self.isBoss() : false;
		}));      

    }

   
	private void register_objects() {
		// Register our objects
        TableOfAwakeningVN.registerTableOfAwakeningVN();

        registerObject("gearstorageboxvn", new StorageBoxInventoryObject("gearstorageboxvn", 40, new Color(97, 95, 132)), 20.0F, true);

        registerObject("cubetreevn", new TreeObject(
                "cubetreevn", "cubelogvn", "cubesaplingvn", 
                new Color(86, 69, 40), 45, 60, 110, "fruitpalmleaves"), 
            0.0F, false
        );

        registerObject("cubesaplingvn", new TreeSaplingObject(
                "cubesaplingvn", "cubetreevn", 1800, 2700, false,
                new String[]{"cubemainlandtilevn", "sandtile", "grasstile", "dirttile", "farmland", "snowtile"}), 
            50.0F, true
        );

        registerObject("blockberrybushvn", 
            new FruitBushObject("blockberrybushvn", "blockberrysaplingvn", 900.0F, 1800.0F, "blockberryvn", 1.0F, 2, 
                new Color(60, 29, 95)).setDebrisColor(new Color(50, 115, 44)), 
            0.0F, false
        );

        registerObject("blockberrysaplingvn", new BlockberrySaplingObjectVN(
                "blockberrysaplingvn", "blockberrybushvn", 1200, 2100, false,
                new String[]{"cubemainlandtilevn", "sandtile", "grasstile", "dirttile", "farmland", "snowtile"}), 
            30.0F, true
        );
        
        // Register Walls
        registerWallObjects("cubewood", "cubewoodwallvn", new Color(74, 84, 166), 2.0F, 6.0F);
        registerWallObjects("cubestone", "cubestonewallvn", new Color(81, 45, 168), 0.5F, 1.0F);
        registerWallObjects("cubedeepstone", "cubedeepstonewallvn", new Color(81, 45, 168), 0.5F, 1.0F);
        registerWallObjects("gearfactory", "gearfactorywallvn", new Color(222, 221, 227), 0.5F, 1.0F);
        
        registerRockObjects("cube", new Color(109, 35, 241), "cubestonevn", 1);
        registerRockObjects("cubedeep", new Color(57, 11, 141), "cubedeepstonevn", 3);
        
               
        registerObject("cubaltorecuberockvn", new RockOreObject(
                (RockObject) getObject("cuberockvn"), "oremask", "cubaltorevn",
                new Color(150, 115, 65), "cubaltoreitemvn", 1, 3, 2, false), 0.0F, false
        );

        registerObject("ironorecuberock", new RockOreObject(
                (RockObject) getObject("cuberockvn"), "oremask", "ironore",
                new Color(150, 115, 65), "ironore", 1, 3, 2, false), 0.0F, false
        );

        registerObject("copperorecuberock", new RockOreObject(
                (RockObject) getObject("cuberockvn"), "oremask", "copperore",
                new Color(110, 52, 29), "copperore", 1, 3, 2, false), 0.0F, false
        );

        registerObject("goldorecuberock", new RockOreObject(
                (RockObject) getObject("cuberockvn"), "oremask", "goldore",
                new Color(255, 195, 50), "goldore", 1, 3, 2, false), 0.0F, false
        );

        registerObject("quartzrock", new RockOreObject(
                (RockObject) getObject("rock"), "oremask", "quartzore",
                new Color(232, 227, 216), "quartz", 1, 3, 2, false), 0.0F, false
        );

        registerObject("ivyorerock", new RockOreObject(
                (RockObject) getObject("rock"), "oremask", "ivyore",
                new Color(19, 89, 21), "ivyore", 1, 3, 2, false), 0.0F, false
        );

        registerObject("frostshardrock", new RockOreObject(
                (RockObject) getObject("rock"), "oremask", "frostshardore",
                new Color(90, 231, 197), "frostshard", 1, 3, 2, false), 0.0F, false
        );

        // miners haven deepcave ores

        registerObject("glacialoredeeprock", new RockOreObject(
                (RockObject) getObject("deeprock"), "oremask", "glacialore",
                new Color(65, 210, 224), "glacialore", 1, 3, 2, false), 0.0F, false
        );

        registerObject("myceliumoredeeprock", new RockOreObject(
                (RockObject) getObject("deeprock"), "oremask", "myceliumore",
                new Color(189, 117, 91), "myceliumore", 1, 3, 2, false), 0.0F, false
        );

        registerObject("ancientfossiloredeeprock", new RockOreObject(
                (RockObject) getObject("deeprock"), "oremask", "ancientfossilore",
                new Color(80, 136, 85), "ancientfossilore", 1, 3, 2, false), 0.0F, false
        );

        // Other Objects
        registerObject("gearcontactbeaconvn", new GEARContactBeaconVNObject(), 0, false);
	}

	private void register_items() {
		// Wooden (Mage) Set
        registerItem("woodenhelmetvn", new WoodenHelmet(), 110.0F, true);
        registerItem("woodenchestplatevn", new WoodenChestplate(), 160.0F, true);
        registerItem("woodenbootsvn", new WoodenBoots(), 80.0F, true);

        // Anicent Jungle Wooden Set (Mage, Not supposed to be in yet)
        registerItem("ancientjunglehelmetvn", new AncientJungleHelmet(), 2000.0F, true);
        registerItem("ancientjunglechestplatevn", new AncientJungleChestplate(), 2000.0F, true);
        registerItem("ancientjunglebootsvn", new AncientJungleBoots(), 2000.0F, true);
        
        // Wind (Ranger) Set
        registerItem("windhatvn", new WindHatVN(), 110.0F, true);
        registerItem("windchestplatevn", new WindChestplateVN(), 160.0F, true);
        registerItem("windbootsvn", new WindBootsVN(), 80.0F, true);
        
        // Meat (Summoner) Set
        registerItem("meathatvn", new MeatHat(), 110.0F, true);
        registerItem("meatrobevn", new MeatRobe(), 160.0F, true);
        registerItem("meatbootsvn", new MeatBoots(), 80.0F, true);
        
        // Stone (Melee) Set
        registerItem("stonehelmetvn", new StoneHelmet(), 110.0F, true);
        registerItem("stonechestplatevn", new StoneChestplate(), 160.0F, true);
        registerItem("stonebootsvn", new StoneBoots(), 80.0F, true);

        // Chilled Bloodplate Set
        registerItem("chilledbloodplatehatvn", new ChilledBloodplateHat(), 200.0F, true);
        registerItem("chilledbloodplatehoodvn", new ChilledBloodplateHood(), 200.0F, true);
        registerItem("chilledbloodplatehelmetvn", new ChilledBloodplateHelmet(), 200.0F, true);
        registerItem("chilledbloodplatechestplatevn", new ChilledBloodplateChestplate(), 240.0F, true);
        registerItem("chilledbloodplatebootsvn", new ChilledBloodplateBoots(), 120.0F, true);

        // Cubalt Set
        registerItem("cubalthelmetvn", new CubaltHelmetVN(), 110.0F, true);
        registerItem("cubaltchestplatevn", new CubaltChestplateVN(), 160.0F, true);
        registerItem("cubaltbootsvn", new CubaltBootsVN(), 80.0F, true);

        //Vanity :3 Set
        registerItem("luckychickenmaskvn", (new HelmetArmorItem(0, (DamageType)null, 0, Item.Rarity.COMMON, "luckychickenmaskvn")).drawBodyPart(false), 50.0F, true);
        registerItem("luckychickencostumeshirtvn", new ChestArmorItem(0, 0, Item.Rarity.COMMON, "luckychickencostumeshirtvn", "luckychickencostumearmsvn"), 50.0F, true);
        registerItem("luckychickencostumebootsvn", new BootsArmorItem(0, 0, Item.Rarity.COMMON, "luckychickencostumebootsvn"), 50.0F, true);
        
        // Trinkets
        registerItem("foxtokenvn", 				new SimpleTrinketItem(Item.Rarity.UNIQUE, "foxtokenvnbuff", 200), 300.0F, true);
        registerItem("thecollectorsmagnetvn", 	new SimpleTrinketItem(Item.Rarity.LEGENDARY, "collectorsmagnetvnbuff", 200).addDisables(new String[]{"itemattractor"}), 800.0F, true);
        registerItem("nightmareheadvn", 		new SimpleTrinketItem(Item.Rarity.LEGENDARY, "nightmareheadvnbuff", 400), 400.0F, true);
        registerItem("trueexplorersatchelvn", 	new CombinedTrinketItem(Item.Rarity.UNIQUE, 1500, new String[]{"explorersatchel", "explorercloak", "spikedbatboots", "ancientrelics", "nightmareheadvn"}), 200.0F, true);
        registerItem("foxtailtrinketvn", 		new SimpleTrinketItem(Item.Rarity.NORMAL, "foxtailvnbuff", 100), 400.0F, true);
        registerItem("berserkerorbvn", 			new SimpleTrinketItem(Item.Rarity.LEGENDARY, "berserkerorbvnbuff", 800).addDisables(new String[]{"frenzyorb"}), 800.0F, true);
        registerItem("cosmictalismanvn", 		new SimpleTrinketItem(Item.Rarity.LEGENDARY, "cosmictalismanvnbuff", 1000).addDisables(new String[]{"nightmaretalisman", "dreamcatcher"}), 1200.0F, true);
        registerItem("yourgemcollectionvn", 	new SimpleTrinketItem(Item.Rarity.UNIQUE, "yourgemcollectionvnbuff", 1000), 5000.0F, true);
        registerItem("clovertokenvn", 			new SimpleTrinketItem(Item.Rarity.RARE, "clovertokenvnbuff", 200), 500.0F, true);
        registerItem("windmedallionvn", 		new SimpleTrinketItem(Item.Rarity.RARE, "windmedallionvnbuff", 100), 200.0F, true);
        registerItem("treemedallionvn", 		new SimpleTrinketItem(Item.Rarity.RARE, "treemedallionvnbuff", 100), 200.0F, true);
        registerItem("meatmedallionvn", 		new SimpleTrinketItem(Item.Rarity.RARE, "meatmedallionvnbuff", 100), 200.0F, true);
        registerItem("stonemedallionvn", 		new SimpleTrinketItem(Item.Rarity.RARE, "stonemedallionvnbuff", 100), 200.0F, true);
        registerItem("medallionboardvn", 		new SimpleTrinketItem(Item.Rarity.UNIQUE, "medallionboardvnbuff", 300).addDisables(new String[]{"windmedallionvn", "treemedallionvn", "stonemedallionvn", "meatmedallionvn"}), 800.0F, true);
        registerItem("fortunecollectionvn", 	new SimpleTrinketItem(Item.Rarity.UNIQUE, "fortunecollectionvnbuff", 800).addDisables(new String[]{"medallionboardvn", "windmedallionvn", "treemedallionvn", "stonemedallionvn", "meatmedallionvn", "clovertokenvn"}), 1000.0F, true);
        registerItem("thebygonecrestvn", 		new SimpleTrinketItem(Item.Rarity.UNIQUE, "thebygonecrestvnbuff", 1000), 800.0F, true);
        registerItem("frostbittenpawvn", 		new CombinedTrinketItem(Item.Rarity.RARE, 700, new String[]{"polarclaw", "frozenwave"}), 200.0F, true);
        registerItem("frozenpendantvn", 		new CombinedTrinketItem(Item.Rarity.RARE, 700, new String[]{"frozensoul", "lifependant"}), 200.0F, true);
        registerItem("glacialfrostvn", 			new CombinedTrinketItem(Item.Rarity.RARE, 700, new String[]{"frozenpendantvn", "frostbittenpawvn"}), 200.0F, true);
        registerItem("frozenheavenvn", 			new CombinedTrinketItem(Item.Rarity.RARE, 1500, new String[]{"halovn", "glacialfrostvn"}), 200.0F, true);
        registerItem("halovn", 					new SimpleTrinketItem(Item.Rarity.RARE, "halovnbuff", 300), 800.0F, true);
        registerItem("demonicgauntletvn", 		(new SimpleTrinketItem(Item.Rarity.RARE, "demonicgauntletvnbuff", 300)).addDisables(new String[]{"claygauntlet", "demonicgaunletvn", "tungstengauntletvn"}), 200.0F, true);
        registerItem("tungstengauntletvn", 		(new SimpleTrinketItem(Item.Rarity.RARE, "tungstengauntletvnbuff", 300)).addDisables(new String[]{"claygauntlet", "demonicgaunletvn", "tungstengauntletvn"}), 400.0F, true);
        registerItem("novagauntletvn", 			(new SimpleTrinketItem(Item.Rarity.RARE, "novagauntletvnbuff", 500)).addDisables(new String[]{"claygauntlet", "demonicgauntletvn", "tungstengauntletvn"}), 800.0F, true);
        registerItem("calmingminersexoskeletonvn", (new CalmingMinersExoskeletonVNTrinket()).addDisables(new String[]{"minersbonquet", "calmingrose", "toolbox", "diggingclaw"}), 800.0F, true);
        registerItem("minersexoskeletonvn", 	(new MinersExoskeletonVNTrinket()).addDisables(new String[]{"minersbonquet", "calmingrose", "toolbox", "diggingclaw"}), 800.0F, true);
        registerItem("gladiatorsembracevn", 	new CombinedTrinketItem(Item.Rarity.UNCOMMON, 800, new String[]{"manica", "challengerspauldron"}), 200.0F, true);
        registerItem("galacticgraspvn", 		new CombinedTrinketItem(Item.Rarity.UNCOMMON, 1000, new String[]{"gladiatorsembracevn", "novagauntletvn"}), 200.0F, true);
        registerItem("cubaltshieldvn", 			new CubaltShieldVNToolItem(Item.Rarity.UNCOMMON, 1200), 2000, true);
        registerItem("protectorsealvn", 		new AOESimpleTrinketItem(Item.Rarity.UNCOMMON, "protectorsealvntrinketbuff", 120), 500.0F, true);
        registerItem("ruinedgolemsealvn", 		new AOESimpleTrinketItem(Item.Rarity.UNCOMMON, "ruinedgolemsealvntrinketbuff", 120), 500.0F, true);
        registerItem("flowingenergysealvn", 	new AOESimpleTrinketItem(Item.Rarity.UNCOMMON, "flowingenergysealvntrinketbuff", 120), 500.0F, true);
        registerItem("speedstersealvn", 		new SimpleTrinketItem(Item.Rarity.UNCOMMON, "speedstersealvntrinketbuff", 120), 500.0F, true);
        registerItem("demonwarriorsealvn", 		new AOESimpleTrinketItem(Item.Rarity.UNCOMMON, "demonwarriorsealvntrinketbuff", 120), 500.0F, true);
        registerItem("holypaladinsealvn", 		new AOESimpleTrinketItem(Item.Rarity.UNCOMMON, "holypaladinsealvntrinketbuff", 120), 500.0F, true);
        registerItem("archbishopcowlvn", 		new SimpleTrinketItem(Item.Rarity.UNCOMMON, "archbishopcowlvntrinketbuff", 1500), 2000.0F, true);
        registerItem("amethystamuletvn", 		new SimpleTrinketItem(Item.Rarity.RARE, "amethystamuletvntrinketbuff", 120), 500.0F, true);

        // Lawyer Up, Hit the Gems
        registerItem("healthgemvn", 			new SimpleTrinketItem(Item.Rarity.UNCOMMON, "healthgemvnbuff", 200), 100.0F, true);
        registerItem("regengemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "regengemvnbuff", 200), 100.0F, true);
        registerItem("resiliencegemvn", 		new SimpleTrinketItem(Item.Rarity.UNCOMMON, "resiliencegemvnbuff", 200), 100.0F, true);
        registerItem("resilienceregengemvn", 	new SimpleTrinketItem(Item.Rarity.UNCOMMON, "resilienceregengemvnbuff", 200), 100.0F, true);
        registerItem("managemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "managemvnbuff", 200), 100.0F, true);
        registerItem("manaregengemvn", 			new SimpleTrinketItem(Item.Rarity.UNCOMMON, "manaregengemvnbuff", 200), 100.0F, true);
        registerItem("bloodgemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "bloodgemvnbuff", 200), 100.0F, true);
        registerItem("damagegemvn", 			new SimpleTrinketItem(Item.Rarity.UNCOMMON, "damagegemvnbuff", 200), 100.0F, true);
        registerItem("critgemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "critgemvnbuff", 200), 100.0F, true);
        registerItem("speedgemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "speedgemvnbuff", 200), 100.0F, true);
        registerItem("dashgemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "dashgemvnbuff", 200), 100.0F, true);
        registerItem("summongemvn", 			new SimpleTrinketItem(Item.Rarity.UNCOMMON, "summongemvnbuff", 200), 100.0F, true);
        registerItem("armorgemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "armorgemvnbuff", 200), 100.0F, true);
        registerItem("hookgemvn", 				new SimpleTrinketItem(Item.Rarity.UNCOMMON, "hookgemvnbuff", 200), 100.0F, true);

        // Items
        registerItem("acrumplednotevn", new ACrumpledNoteVN(), 1000, true);
        registerItem("novafragmentvn", new NovaFragmentMaterialItem(), 2, true);
        registerItem("novashardvn", new NovaShardMaterialItem(), 10, true);
        registerItem("novaclustervn", new NovaClusterMaterialItem(), 100, true);
        registerItem("shapeshardsvn", new ShapeShardsMaterialItem(), 10, true);
        registerItem("cubaltpickaxevn", new CustomPickaxeToolItem(400, 150, 2, 20, 50, 50, 800, Item.Rarity.UNCOMMON) {
            public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
                ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
                tooltips.add(Localization.translate("itemtooltip", "tungstentooltip"), 350);
                return tooltips;
            }
        }, 160.0F, true);
        registerItem("cubaltaxevn", new CustomAxeToolItem(400, 150, 2, 20, 50, 50, 800, Item.Rarity.UNCOMMON), 160.0F, true);
        registerItem("cubaltshovelvn", new CustomShovelToolItem(400, 150, 2, 20, 50, 50, 800, Item.Rarity.UNCOMMON), 160.0F, true);
        registerItem("cubaltoreitemvn", (new MatItem(250, Item.Rarity.UNCOMMON, new String[0])).setItemCategory(new String[]{"materials", "ore"}), 1.0F, true);
        registerItem("cubaltbarvn", (new MatItem(100, Item.Rarity.UNCOMMON, new String[0])).setItemCategory(new String[]{"materials", "bars"}), 4.0F, true);
        registerItem("cubelogvn", (new MatItem(250, Item.Rarity.UNCOMMON, new String[]{"anylog"})).setItemCategory(new String[]{"materials", "logs"}), 2.0F, true);
        registerItem("cubestonevn", new StonePlaceableItem(500), 0.1F, true);
        registerItem("cubedeepstonevn", new StonePlaceableItem(500), 0.1F, true);
        registerItem("blockberryvn", (new FoodConsumableItem(100, Item.Rarity.UNCOMMON, Settler.FOOD_SIMPLE, 15, 300, new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.ARMOR_FLAT, 5)})).spoilDuration(960).addGlobalIngredient(new String[]{"anycompostable", "anyfruit"}).setItemCategory(new String[]{"consumable", "rawfood"}), 4.0F, true);
        registerItem("nightbladevn", new NightbladeSwordVN(), 300, true);
        registerItem("celestialembervn", new CelestEmberStaff(), 800, true);
        registerItem("saplingswordvn", new SaplingSword(), 60, true);
        registerItem("holytomevn", new HolyTome(), 400, true);
        registerItem("unholytomevn", new UnholyTome(), 400, true);
        registerItem("novabulletvn", new NovaBulletItem(), 10, true);
        registerItem("lmgvn", new LMGVN(), 600, true);
        registerItem("bigbrainonastickvn", new BigBrainOnAStickVN(), 100, true);
        registerItem("verdantstaffvn", new VerdantStaff(), 60, true);
        registerItem("doomedbowvn", new DoomedBow(), 500, true);
        registerItem("novaarrowvn", new NovaArrowItem(), 10, true);
        registerItem("goldarrowvn", new GoldArrowItem(), 10, true);
        registerItem("codblastervn", new Codblaster(), 200, true);
        registerItem("glassshardswordvn", new GlassShardSword(), 150, true);
        registerItem("repeatingcrossbowvn", new RepeatingCrossbow(), 500, true);
        registerItem("ironsoulglaivevn", new IronsoulGlaiveVN(), 500, true);
        registerItem("queenspiderstaffvn", new QueenSpiderStaff(), 100, true);
        registerItem("necromancersrequiemvn", new NecromancersRequiemVN(), 1000, true);
        registerItem("acornlobbervn", new AcornLobberVN(), 60, true);
        registerItem("woodenbulletvn", new WoodenBulletVNItem(), 5, true);
        registerItem("cloudsvn", new CloudMaterialItem(), 5, true);
        registerItem("windarrowvn", new WindArrowItem(), 10, true);
        registerItem("theblowingwindvn", new TheBlowingWind(), 300, true);
        registerItem("awakeninatorvn", new AwakeninatorMaterialItem(), 400, true);
        registerItem("galevn", new GaleVN(), 200, true);
        registerItem("hurricanevn", new HurricaneVN(), 100, true);
        registerItem("windbulletvn", new WindRoundItem(), 5, true);
        registerItem("novaheartvn", new NovaHeartItem(), 200, true);
        // ignore this i couldn't be bothered to make a new mod just to add this lol registerItem("onehealthconsumevn", new ToOneHealthItem(), 0, true);
        registerItem("sentientcrownvn", new PetFollowerPlaceableItem("petsentientcrownvn", Item.Rarity.LEGENDARY), 1000.0F, true);
        registerItem("thejadewaraxevn", new TheJadeWaraxe(), 600, true);
        registerItem("heavierhammervn", new HeavierHammerVN(), 300, true);
        registerItem("heaviesthammervn", new HeaviestHammerVN(), 800, true);
        registerItem("themountainvn", new TheMountainVN(), 800, true);
        registerItem("thunderingrodvn", new ThunderingRodVN(), 100, true);
        registerItem("eyeofthestormvn", new EyeOfTheStormVN(), 500, true);
        registerItem("crimsontempestvn", new CrimsonTempestVN(), 800, true);
        registerItem("monsterpheromonesvn", new MonsterPheromonesItem(), 600, true);
        registerItem("arachnoremotevn", new ArachnoRemoteVN(), 600, true);
        registerItem("novicetomevn", new NoviceTome(), 200, true);
        registerItem("intermediatetomevn", new IntermediateTome(), 200, true);
        registerItem("experttomevn", new ExpertTome(), 700, true);
        registerItem("mastertomevn", new MasterTome(), 1000, true);
        registerItem("magicfordummiesvn", new MagicForDummiesVN(), 2000, true);
        registerItem("stormboltervn", new StormbolterVN(), 300, true);
        registerItem("stormbringervn", new StormbringerVN(), 300, true);
        registerItem("arainydayvn", new ARainyDayVN(), 300, true);
        registerItem("dustygemsackvn", new DustyGemSackVN(), 50, true);
        registerItem("gemdustvn", new GemDustMaterialItem(), 4, true);
        registerItem("novakatanavn", new NovaKatanaVN(), 2000, true);
        registerItem("titanbustergreatswordvn", new TitanBusterGreatswordVN(), 200.0F, true);
        registerItem("cubecallervn", new CubeCallerVN(), 800, true);
        registerItem("eyebeamvn", new EyebeamVN(), 800, true);
        registerItem("spherecererhatvn", new SpherecererHatVN(), 800, true);
        registerItem("bastionboxvn", new BastionBoxItemVN(), 200, true);
        registerItem("gearresiliencematrixvn", new GEARResilienceMatrixItemVN(), 200, true);
        registerItem("cavedemolishervn", new CaveDemolisherVNToolItem(), 60.0F, true);
        registerItem("grandphoenixgreatbowvn", new GrandPhoenixGreatbowVN(), 1000.0F, true);
        registerItem("minershavenmapvn", new BiomeMapItem(Item.Rarity.RARE, 18, new String[]{"minershavenvn"}), 120.0F, true);
        registerItem("rawvulpinevn", (new FoodConsumableItem(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 10, 240, new ModifierValue[]{new ModifierValue(BuffModifiers.DASH_STACKS, -1)})).debuff().spoilDuration(240).addGlobalIngredient(new String[]{"anyrawmeat"}).setItemCategory(new String[]{"consumable", "rawfood"}), 4.0F, true);
        registerItem("roastedvulpinevn", (new FoodConsumableItem(250, Item.Rarity.NORMAL, Settler.FOOD_FINE, 20, 480, new ModifierValue[]{new ModifierValue(BuffModifiers.DASH_STACKS, 1)})).spoilDuration(240), 12.0F, true);
	}

	private void register_mobs() {
		// Register our mob
        registerMob("nightmaremobvn", NightmareMobVN.class, true);
        registerMob("snowynightmaremobvn", SnowyNightmareMobVN.class, true);
        registerMob("foxmobvn", FoxMobVN.class, true);
        registerMob("gustmobvn", GustMobVN.class, true);
        registerMob("apocawindmobvn", ApocaWindMobVN.class, false);
        registerMob("petsentientcrownvn", PetSentientCrownVN.class, true);
        registerMob("cubaltcavelingvn", CubaltCavelingVN.class, true);
        registerMob("gemstonecavelingvn", GemStoneCavelingVN.class, true);
        registerMob("deepgemstonecavelingvn", DeepGemStoneCavelingVN.class, true);
        registerMob("luckychickenmobvn", LuckyChickenMobVN.class, true);
        registerMob("gearspherebossmobvn", GEARSphereBossMobVN.class, true,true);
        registerMob("gearcubemobvn", GEARCubeMobVN.class, true);
        registerMob("planewalkermobvn", PlanewalkerMobVN.class, true);
        //registerMob("allseeingcubemobvn", AllSeeingCubeMobVN.class, true);
        registerMob("babycubemobvn", BabyCubeMobVN.class,false);
        registerMob("babytitancubemobvn", BabyTitanCubeMobVN.class,false);
        registerMob("cubemobvn", CubeMobVN.class, true);
        registerMob("nightmarecubemobvn", NightmareCubeMobVN.class, true);
        registerMob("icecubemobvn", IceCubeMobVN.class, true);
        registerMob("titancubemobvn", TitanCubeMobVN.class, true);
        registerMob("pyramidmobvn", PyramidMobVN.class, true);
        registerMob("spheresorcerermobvn", SphereSorcererMobVN.class, true);
        registerMob("spheresentinelmobvn", SphereSentinelMobVN.class, true);
        registerMob("deadmahmobvn", DeadmahMobVN.class,true);
	}

	private void register_projectiles() {
		registerProjectile("celestialember", CelestEmberProjectile.class, "celestialemberprojvn", "celestialemberprojvn_shadow");
        registerProjectile("nightblade", NightbladeVNProjectile.class, "nightbladeprojvn", "nightbladeprojvn_shadow");
        registerProjectile("leafproj", SaplingSwordProjectile.class, "leafprojvn", "leafprojvn");
        registerProjectile("holyproj", HolyTomeProjectile.class, "holyprojvn", "holyprojvn_shadow");
        registerProjectile("unholyproj", UnholyTomeProjectile.class, "unholyprojvn", "unholyprojvn_shadow");
        
        registerProjectile("verdantflowershot", VerdantStaffFlowerProjectile.class, "verdantflowerprojvn", "swampdwellerstaffflower_shadow");
        registerProjectile("verdantflowerpetal", VerdantStaffPetalProjectile.class, "verdantflowerpetalprojvn", "swampdwellerstaffpetal_shadow");
        
        registerProjectile("doomedarrowproj", DoomedBowProjectile.class, "doomedarrowprojvn", "chain");
        registerProjectile("novaarrowproj", NovaArrowProjectile.class, "novaarrowprojvn", "arrow_shadow");
        registerProjectile("goldarrowproj", GoldArrowProjectile.class, "goldarrowprojvn", "arrow_shadow");
        registerProjectile("chain", NovaBulletProjectile.class, "chain", "chain");
        
        registerProjectile("woodbulletproj", WoodenBulletVNProjectile.class, "chain", "chain");       
        registerProjectile("windarrowproj", WindArrowProjectile.class, "windarrowprojvn", "arrow_shadow");
        registerProjectile("windroundproj", WindRoundProjectile.class, "chain", "chain");
        registerProjectile("jadeproj", JadeShotProjectile.class, "jadeprojvn", "jadeprojvn_shadow");
        
        registerProjectile("heavierhammerproj", HeavierHammerShotVNProjectile.class, "heavierhammerprojvn", "stone_shadow");
        registerProjectile("heaviesthammerproj", HeaviestHammerShotVNProjectile.class, "heaviesthammerprojvn", "stone_shadow");
        
        registerProjectile("themountainproj", TheMountainShotVNProjectile.class, "themountainprojvn", "stone_shadow");
        
        registerProjectile("thunderboltproj", ThunderboltVNProjectile.class, "thunderboltprojvn", "thunderboltprojvn_shadow");
        registerProjectile("thunderboltblueproj", ThunderboltBlueVNProjectile.class, "thunderboltblueprojvn", "thunderboltprojvn_shadow");
        registerProjectile("thunderboltredproj", ThunderboltRedVNProjectile.class, "thunderboltredprojvn", "thunderboltprojvn_shadow");
        
        registerProjectile("novicetomeproj", NoviceTomeProjectile.class, "novicetomeprojvn", "novicetomeprojvn_shadow");
        registerProjectile("intermediatetomeproj", IntermediateTomeProjectile.class, "intermediatetomeprojvn", "novicetomeprojvn_shadow");
        registerProjectile("experttomeproj", ExpertTomeProjectile.class, "experttomeprojvn", "novicetomeprojvn_shadow");
        registerProjectile("mastertomeproj", MasterTomeProjectile.class, "mastertomeprojvn", "novicetomeprojvn_shadow");
        
        registerProjectile("magicfordummiesproj", MagicForDummiesVNProjectile.class, "magicfordummiesprojvn", "novicetomeprojvn_shadow");
        
        registerProjectile("spherecererproj", SpherecererShotVNProjectile.class, "spherecererprojvn", "spherecererprojvn_shadow");
        registerProjectile("spheresentinelproj", SphereSentinelShotVNProjectile.class, "spheresentinelprojvn", "novicetomeprojvn_shadow");
        
        registerProjectile("cavedemolisherproj", CaveDemolisherVNProjectile.class, "cavedemolisherprojvn", "cavedemolisherprojvn_shadow");
        
        registerProjectile("gearsphereminionpodproj", GEARSphereMinionPodVN.class, "gearsphereminionpodvn", "queenspideregg_shadow");
        registerProjectile("acornproj", AcornProjectile.class, "acornprojvn", "acornprojvn_shadow");
        registerProjectile("codblasterproj", CodBlasterProjectile.class, "codblasterprojvn", "codblasterprojvn_shadow");
        
        registerProjectile("crimsontempestvnproj", CrimsonTempestVNProjectile.class, "thunderboltredprojvn", "thunderboltprojvn_shadow");
	}

	private void register_buffs() {
		// Armors
        registerBuff("woodensetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.MAGIC_DAMAGE, 0.15f), new ModifierValue<Float>(BuffModifiers.MAGIC_CRIT_CHANCE, 0.05f), new ModifierValue<Integer>(BuffModifiers.MAX_MANA_FLAT, 30),}));
        registerBuff("ancientjunglesetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.COMBAT_REGEN, 0.05F), new ModifierValue<Float>(BuffModifiers.SPEED, 0.05F), new ModifierValue<Float>(BuffModifiers.SWIM_SPEED, 0.10F), new ModifierValue<Float>(BuffModifiers.CRIT_CHANCE, 0.05F)}));
        registerBuff("windsetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ATTACK_SPEED, 0.25F), new ModifierValue<Float>(BuffModifiers.CRIT_CHANCE, 0.05F), new ModifierValue<Float>(BuffModifiers.SPEED, 0.10F)}));
        registerBuff("meatsetvnbonusbuff", new MeatSetBonusBuff());
        registerBuff("cubaltsetvnbonusbuff", new CubaltSetVNBonusBuff());
        registerBuff("stonesetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ATTACK_SPEED, 0.10f), new ModifierValue<Integer>(BuffModifiers.MAX_RESILIENCE_FLAT, 10), new ModifierValue<Integer>(BuffModifiers.ARMOR_FLAT, 3)}));
        registerBuff("chilledbloodplatehatsetvnbonusbuff", new ChilledBloodplateHatSetBonusBuff());
        registerBuff("chilledbloodplatehoodsetvnbonusbuff", new ChilledBloodplateHoodSetBonusBuff());
        registerBuff("chilledbloodplatehelmetsetvnbonusbuff", new ChilledBloodplateHelmetSetBonusBuff());
        
        // Trinkets
        registerBuff("collectorsmagnetvnbuff", new CollectorsMagnetBuff());
        registerBuff("nightmareheadvnbuff", new NightmareHeadVNBuff());
        registerBuff("foxtokenvnbuff", new FoxTokenVNBuff());
        registerBuff("foxtailvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.DASH_STACKS, 1), new ModifierValue<Float>(BuffModifiers.DASH_COOLDOWN, -0.10F)}));
        registerBuff("berserkerorbvnbuff", new BerserkerOrbVNBuff());
        registerBuff("cosmictalismanvnbuff", new SimpleTrinketBuff("cosmictalismanvntip", new ModifierValue[]{new ModifierValue(BuffModifiers.COMBAT_MANA_REGEN, 3F), new ModifierValue<Integer>(BuffModifiers.MAX_MANA_FLAT, 50)}));
        registerBuff("yourgemcollectionvnbuff", new YourGemCollectionVNBuff());
        registerBuff("clovertokenvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.CRIT_CHANCE, 0.25F), new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE, -0.15F)}));
        registerBuff("windmedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.RANGED_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.RANGED_CRIT_CHANCE, 0.05F)}));
        registerBuff("stonemedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.MELEE_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.MELEE_CRIT_CHANCE, 0.05F)}));
        registerBuff("meatmedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.SUMMON_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.05F)}));
        registerBuff("treemedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.MAGIC_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.05F), new ModifierValue<Float>(BuffModifiers.MAGIC_CRIT_CHANCE, 0.05F)}));
        registerBuff("medallionboardvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE, 0.1F), new ModifierValue<Float>(BuffModifiers.CRIT_DAMAGE, 0.1F), new ModifierValue<Float>(BuffModifiers.CRIT_CHANCE, 0.1F)}));
        registerBuff("fortunecollectionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE, -0.05F), new ModifierValue<Float>(BuffModifiers.CRIT_DAMAGE, 0.1F), new ModifierValue<Float>(BuffModifiers.CRIT_CHANCE, 0.35F)}));
        registerBuff("thebygonecrestvnbuff", new TheBygoneCrestVNBuff());
        registerBuff("halovnbuff", new HaloVNBuff());
        registerBuff("demonicgauntletvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_RESILIENCE_FLAT, 20), new ModifierValue<Float>(BuffModifiers.RESILIENCE_DECAY, -0.80F)}));
        registerBuff("tungstengauntletvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_RESILIENCE_FLAT, 30), new ModifierValue<Float>(BuffModifiers.RESILIENCE_DECAY, -0.90F)}));
        registerBuff("novagauntletvnbuff", new NovaGauntletVNBuff());
        registerBuff("calmingminersexoskeletonvnbuff", new CalmingMinersExoskeletonVNBuff());
        registerBuff("minersexoskeletonvnbuff", new MinersExoskeletonVNBuff());
        registerBuff("protectorsealvntrinketbuff", new ProtectorSealVNTrinketBuff());
        PROTECTOR_SEAL_VN_ACTIVE = registerBuff("protectorsealvnactivebuff", new ProtectorSealVNActiveBuff());
        registerBuff("ruinedgolemsealvntrinketbuff", new RuinedGolemSealVNTrinketBuff());
        RUINED_GOLEM_VN_ACTIVE = registerBuff("ruinedgolemsealvnactivebuff", new RuinedGolemSealVNActiveBuff());
        registerBuff("flowingenergysealvntrinketbuff", new FlowingEnergySealVNTrinketBuff());
        FLOWING_ENERGY_VN_ACTIVE = registerBuff("flowingenergyvnactivebuff", new FlowingEnergySealVNActiveBuff());
        registerBuff("speedstersealvntrinketbuff", new SpeedsterSealVNTrinketBuff());
        SPEEDSTER_VN_ACTIVE = registerBuff("speedstersealvnactivebuff", new SpeedsterSealVNActiveBuff());
        registerBuff("demonwarriorsealvntrinketbuff", new DemonWarriorSealVNTrinketBuff());
        DEMON_WARRIOR_VN_ACTIVE = registerBuff("demonwarriorsealvnactivebuff", new DemonWarriorSealVNActiveBuff());
        registerBuff("holypaladinsealvntrinketbuff", new HolyPaladinSealVNTrinketBuff());
        HOLY_PALADIN_VN_ACTIVE = registerBuff("holypaladinsealvnactivebuff", new HolyPaladinSealVNActiveBuff());
        registerBuff("archbishopcowlvntrinketbuff", new ArchbishopCowlVNTrinketBuff());
        ARCHBISHOP_COWL_VN_ACTIVE = registerBuff("archbishopcowlvnactivebuff", new ArchbishopCowlVNActiveBuff());
        registerBuff("amethystamuletvntrinketbuff", new AmethystAmuletVNTrinketBuff());
        AMETHYST_AMULET_VN_ACTIVE = registerBuff("amethystamuletvnactivebuff", new AmethystAmuletVNActiveBuff());
        JADE_WAR_AXE_BLEED_VN = registerBuff("jadeaxebleedingvn", new WarAxeBleedingBuff());
        // Gem Trinkets
        registerBuff("healthgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_HEALTH_FLAT, 50)}));
        registerBuff("regengemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 1.00F)}));
        registerBuff("resiliencegemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_RESILIENCE_FLAT, 50)}));
        registerBuff("resilienceregengemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.RESILIENCE_REGEN_FLAT, 0.50F)}));
        registerBuff("managemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_MANA_FLAT, 100)}));
        registerBuff("manaregengemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.COMBAT_MANA_REGEN_FLAT, 1.00F)}));
        registerBuff("bloodgemvnbuff", new BloodGemBuff());
        registerBuff("damagegemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.ALL_DAMAGE, 0.10F)}));
        registerBuff("critgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.CRIT_CHANCE, 0.10F)}));
        registerBuff("speedgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Float>(BuffModifiers.SPEED, 0.25F)}));
        registerBuff("dashgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.DASH_STACKS, 1)}));
        registerBuff("summongemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.MAX_SUMMONS, 1)}));
        registerBuff("armorgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.ARMOR_FLAT, 10)}));
        registerBuff("hookgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue<Integer>(BuffModifiers.FISHING_LINES, 1)}));

        // Buffs/Debuffs
        COSMIC_FIRE_VN = registerBuff("cosmicfirevn", new CosmicFireVNBuff());
        BLEEDING_BUFF_VN = registerBuff("bleedingvn", new BleedingBuff());
        MONSTER_PHEROMONE_BUFF_VN = registerBuff("monsterpheromonesvn", new MonsterPheromonesBuff());
        PROTECTOR_SEAL_VN_COOLDOWN = registerBuff("protectorsealvncooldown", new ShownCooldownBuff());
        RUINED_GOLEM_VN_COOLDOWN = registerBuff("ruinedgolemsealvncooldown", new ShownCooldownBuff());
        FLOWING_ENERGY_VN_COOLDOWN = registerBuff("flowingenergysealvncooldown", new ShownCooldownBuff());
        SPEEDSTER_VN_COOLDOWN = registerBuff("speedstersealvncooldown", new ShownCooldownBuff());
        DEMON_WARRIOR_VN_COOLDOWN = registerBuff("demonwarriorsealvncooldown", new ShownCooldownBuff());
        HOLY_PALADIN_VN_COOLDOWN = registerBuff("holypaladinsealvncooldown", new ShownCooldownBuff());
        ARCHBISHOP_COWL_VN_COOLDOWN = registerBuff("archbishopcowlvncooldown", new ShownCooldownBuff());
        AMETHYST_AMULET_VN_COOLDOWN = registerBuff("amethystamuletvncooldown", new AmethystAmuletVNCooldownBuff());
        CRIMSON_TEMPEST_CHARGE_STACKS_BUFF = registerBuff("crimsontempestvncharge", new CrimsonTempestChargeStackBuff());
	}

	private void register_levels() {
		// Register our levels        
        LevelRegistry.registerLevel("flatlandssurfacevn", FlatlandsSurfaceLevelVN.class);
        LevelRegistry.registerLevel("flatlandscavevn", FlatlandsCaveLevelVN.class);
        LevelRegistry.registerLevel("flatlandsdeepcavevn", FlatlandsDeepCaveLevelVN.class);

        LevelRegistry.registerLevel("minershavensurfacevn", MinersHavenSurfaceLevelVN.class);
        LevelRegistry.registerLevel("minershavencavevn", MinersHavenCaveLevelVN.class);
        LevelRegistry.registerLevel("minershavendeepcavevn", MinersHavenDeepCaveLevelVN.class);
	}

    private void register_journal_entries() {
    	
    	JournalEntry flatlandsSurface = JournalRegistry.registerJournalEntry("flatlandssurfacevn",
				new JournalEntry(FLATLANDS, JournalRegistry.LevelType.SURFACE));
    
		flatlandsSurface.addBiomeLootEntry(new String[]{"cubelogvn", "blockberryvn"});
		
		flatlandsSurface.addMobEntries(new String[]{"foxmobvn", "penguin", "snowhare", "bluebird", "bird", "cubemobvn", "pyramidmobvn",
				"titancubemobvn", "planewalkermobvn", "spheresorcerermobvn", "spheresentinelmobvn"});
		
		flatlandsSurface.addTreasureEntry(
				new LootTable[]{LootTablePresets.surfaceRuinsChest});
		
		HARVEST_BLOCKBERRIES_ID = JournalChallengeRegistry.registerChallenge("harvestblockberries", new HarvestBlockBerriesInFlatlandsChallenge());
		KILL_TITANCUBES_ID = JournalChallengeRegistry.registerChallenge("killtitancubes", new KillTitancubesInFlatlandsChallenge());
		KILL_PLANEWALKERS_CROWD_ID = JournalChallengeRegistry.registerChallenge("killplanewalkercrowd", new KillPlanewalkerCrowdChallenge(20, 3000));
		FLATLANDS_SURFACE_CHALLENGES_ID = JournalChallengeRegistry.registerChallenge("flatlandsvnsurface",
				(new MultiJournalChallenge(new Integer[]{
						KILL_TITANCUBES_ID,
						HARVEST_BLOCKBERRIES_ID,
						KILL_PLANEWALKERS_CROWD_ID
						}))
						.setReward(FLATLANDS_SURFACE_REWARD));
		flatlandsSurface.addEntryChallenges(new Integer[]{FLATLANDS_SURFACE_CHALLENGES_ID});
	}

	public void initResources() {
        NightmareMobVN.texture = GameTexture.fromFile("mobs/nightmaremobvn");
        SnowyNightmareMobVN.texture = GameTexture.fromFile("mobs/snowynightmaremobvn");
        FoxMobVN.texture = GameTexture.fromFile("mobs/foxmobvn");
        GustMobVN.texture = GameTexture.fromFile("mobs/gustmobvn");
        ApocaWindMobVN.texture = GameTexture.fromFile("mobs/apocawindmobvn");
        PetSentientCrownVN.texture = GameTexture.fromFile("mobs/petsentientcrownvn");
        LuckyChickenMobVN.texture = GameTexture.fromFile("mobs/luckychickenmobvn");
        PlanewalkerMobVN.texture = GameTexture.fromFile("mobs/planewalkermobvn");
        AllSeeingCubeMobVN.texture = GameTexture.fromFile("mobs/allseeingcubemobvn");
        BabyCubeMobVN.texture = GameTexture.fromFile("mobs/babycubemobvn");
        BabyTitanCubeMobVN.texture = GameTexture.fromFile("mobs/babytitancubemobvn");
        CubeMobVN.texture = GameTexture.fromFile("mobs/cubemobvn");
        NightmareCubeMobVN.texture = GameTexture.fromFile("mobs/nightmarecubemobvn");
        IceCubeMobVN.texture = GameTexture.fromFile("mobs/icecubemobvn");
        TitanCubeMobVN.texture = GameTexture.fromFile("mobs/titancubemobvn");
        TitanCubeMobVN.shadowTexture = GameTexture.fromFile("mobs/titancubemobvn_shadow");
        PyramidMobVN.texture = GameTexture.fromFile("mobs/pyramidmobvn");
        SphereSorcererMobVN.texture = GameTexture.fromFile("mobs/spheresorcerermobvn");
        SphereSentinelMobVN.texture = GameTexture.fromFile("mobs/spheresentinelmobvn");
        SphereSentinelMobVN.shadow = GameTexture.fromFile("mobs/spheresentinelmobvn_shadow");
        SphereSentinelMobVN.sink = GameTexture.fromFile("mobs/spheresentinelmobvn_sink");
        DeadmahMobVN.texture = GameTexture.fromFile("mobs/deadmahmobvn");
        GEARSPHEREbody = GameTexture.fromFile("mobs/gearspherebossmobbodyvn");
        GEARSPHEREhead = GameTexture.fromFile("mobs/gearspherebossmobheadvn");
        GEARSPHEREleg = GameTexture.fromFile("mobs/gearspherebossmobheadvn");
        GEARCubeMobVN.texture = GameTexture.fromFile("mobs/gearcubemobvn");
      

        CubaltShieldVNToolItem.holdTexture = GameTexture.fromFile("player/holditems/cubaltshieldvn");
        
        ELECTRIC_EXPLOSION = GameSound.fromFile("soundeffects/electric_explosion_med_cut_f.ogg");
        ELECTRIC_SHOOT = GameSound.fromFile("soundeffects/zap_short.ogg");
        ELECTRIC_CHARGE = GameSound.fromFile("soundeffects/electric_charge.ogg");
        ELECTRIC_CHARGE_COMPLETE = GameSound.fromFile("soundeffects/voltage.ogg");
        
        COD_FLOPPIN = GameSound.fromFile("soundeffects/fishflop2.ogg");
        
        BLASTER1 = GameSound.fromFile("soundeffects/blaster1.ogg");
        GUNSHOT1 = GameSound.fromFile("soundeffects/gunshot1.ogg");
        //electronicactivatevn = GameSound.fromFile("sound/soundeffects/electronicactivatevn");
        
        
        SealBuffGlyphParticle.buffGlyph = GameTexture.fromFile("buffs/sealbuffglyph");
    }

    public void postInit() {
    	
    	RecipeHelper.initialize();
    	
        // look at necesse.inventory.lootTable.presets

        CaveChestLootTable.snowMainItems.items.add(
                new LootItem("hurricanevn"));
        CaveChestLootTable.extraItems.items.add( //
                LootItem.between("novafragmentvn", 6, 42));
        DeepCaveChestLootTable.swampMainItems.items.add(
                new LootItem("thejadewaraxevn"));
        DeepCaveChestLootTable.extraItems.items.add(
                LootItem.between("novashardvn", 4, 16));
        DungeonChestLootTable.mainItems.items.add(
                new LootItem("arachnoremotevn"));
        SurfaceRuinsChestLootTable.mainItem.add(
                new LootItem("verdantstaffvn"));
        /*
        CaveChestLootTable.potions.add(
                LootItem.between("novafragmentvn", (int)1, (int)16));
        CaveChestLootTable.bars.add(
                LootItem.offset("itemStringID", (int)middle, (int)offset));
        */
        EvilsProtectorMob.lootTable.items.add(
                new LootItem("protectorsealvn"));

        // Mobs no way
        // Spawn tables use a ticket/weight system. In general, common mobs have about 100 tickets.

        Biome.defaultSurfaceCritters
        .add(30,"foxmobvn");
        
        Biome.defaultDeepCaveMobs
                .add(10, "nightmaremobvn")
                .add(5, "deadmahmobvn");
  
        JournalRegistry.getJournalEntry("forestsurface").addMobEntries("foxmobvn");
        
        
        ForestBiome.caveCritters
                .add(90,"gemstonecavelingvn");

        ForestBiome.deepCaveCritters
                .add(90,"deepgemstonecavelingvn");


        SnowBiome.caveMobs
                .add(10,"snowynightmaremobvn")
                .add(5,"icecubemobvn");

        SnowBiome.surfaceMobs
                .add(10,"icecubemobvn")
        		.addLimited(3,"gustmobvn", 5, 32*(128*128));  
        
        SnowBiome.surfaceCritters
                .add(8,"luckychickenmobvn");        
    	
        SnowBiome.caveCritters
                .add(5,"luckychickenmobvn");
        
        JournalRegistry.getJournalEntry("snowsurface").addMobEntries("foxmobvn");
        JournalRegistry.getJournalEntry("snowsurface").addMobEntries("gustmobvn");
        JournalRegistry.getJournalEntry("snowsurface").addMobEntries("icecubemobvn");
        JournalRegistry.getJournalEntry("snowsurface").addMobEntries("luckychickenmobvn");
        
        DesertBiome.surfaceMobs
                .add(10, "pyramidmobvn")
        		.addLimited(3,"gustmobvn", 5, 32*(128*128));   
       
        JournalRegistry.getJournalEntry("desertsurface").addMobEntries("foxmobvn");
        JournalRegistry.getJournalEntry("desertsurface").addMobEntries("gustmobvn");
        JournalRegistry.getJournalEntry("desertsurface").addMobEntries("pyramidmobvn");
    }

}
