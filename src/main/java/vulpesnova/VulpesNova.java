package vulpesnova;

import java.awt.*;
import java.util.Random;

import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.events.loot.MobLootTableDropsEvent;
import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.sound.GameMusic;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SimpleSetBonusBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.SimpleTrinketBuff;
import necesse.entity.mobs.gameDamageType.DamageType;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.item.armorItem.HelmetArmorItem;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.placeableItem.ImportedAnimalSpawnItem;
import necesse.inventory.item.placeableItem.StonePlaceableItem;
import necesse.inventory.item.placeableItem.consumableItem.food.FoodConsumableItem;
import necesse.inventory.item.placeableItem.followerSummonItem.petFollowerPlaceableItem.PetFollowerPlaceableItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.item.toolItem.axeToolItem.CustomAxeToolItem;
import necesse.inventory.item.toolItem.pickaxeToolItem.CustomPickaxeToolItem;
import necesse.inventory.item.toolItem.shovelToolItem.CustomShovelToolItem;
import necesse.inventory.item.trinketItem.CombinedTrinketItem;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.presets.*;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.*;
import necesse.level.gameTile.SimpleFloorTile;
import necesse.level.maps.biomes.desert.DesertBiome;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.snow.SnowBiome;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
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
import vulpesnova.VNContent.VNBiomes.FlatlandsBiomeVN;
import vulpesnova.VNContent.VNBiomes.FlatlandsCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.FlatlandsDeepCaveLevelVN;
import vulpesnova.VNContent.VNBiomes.FlatlandsSurfaceLevelVN;
import vulpesnova.VNContent.VNBuffs.BleedingBuff;
import vulpesnova.VNContent.VNBuffs.CosmicFireVNBuff;
import vulpesnova.VNContent.VNBuffs.FoxTokenVNBuff;
import vulpesnova.VNContent.VNBuffs.VNArmorBuffs.*;
import vulpesnova.VNContent.VNBuffs.MonsterPheromonesBuff;
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

import static necesse.engine.registries.BiomeRegistry.registerBiome;
import static necesse.engine.registries.BuffRegistry.registerBuff;
import static necesse.engine.registries.ItemRegistry.registerItem;
import static necesse.engine.registries.MobRegistry.registerMob;
import static necesse.engine.registries.MusicRegistry.registerMusic;
import static necesse.engine.registries.ObjectRegistry.getObject;
import static necesse.engine.registries.ObjectRegistry.registerObject;
import static necesse.engine.registries.ProjectileRegistry.registerProjectile;
import static necesse.engine.registries.RecipeTechRegistry.registerTech;
import static necesse.engine.registries.TileRegistry.registerTile;

@ModEntry
public class VulpesNova {

    public static Tech TABLEOFAWAKENINGVN;

    public static GameMusic HUBMUSICVN;

    public static final int CUBALT_VN_TOOL_DPS = 150;

    public static int cubeSandVNID;
    public static int cubeMainLandVNID;

    public static int cubeStoneFloorVNID;

    public static int cubeStoneTiledFloorVNID;

    public static int cubeDeepStoneFloorVNID;

    public static int cubeDeepStoneTiledFloorVNID;
    public static int cubeWoodFloorVNID;



    public static Buff COSMIC_FIRE_VN;

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
    public static GameTexture GEARSPHEREbody;
    public static GameTexture GEARSPHEREhead;
    public static GameTexture GEARSPHEREleg;

    public static Biome FLATLANDS;


    public void preInit() {

    }

// lol i have no idea what im doing

    public void init() {
        System.out.println("Hello and Welcome to Vulpes Nova!");

        // Register our tiles


        cubeSandVNID = registerTile("cubesandtilevn", new SimpleFloorTile("cubesandtilevn", new Color(44, 50, 91)), 2.0f, true);
        cubeMainLandVNID = registerTile("cubemainlandtilevn", new CubeMainLandTileVN(), 2.0f, true);
        cubeStoneFloorVNID = registerTile("cubestonefloorvn", new SimpleFloorTile("cubestonefloorvn", new Color(161, 89, 238)), 2.0F, true);
        cubeDeepStoneFloorVNID = registerTile("cubedeepstonefloorvn", new SimpleFloorTile("cubedeepstonefloorvn", new Color(126, 53, 204)), 2.0F, true);
        cubeWoodFloorVNID = registerTile("cubewoodfloorvn", new SimpleFloorTile("cubewoodfloorvn", new Color(117, 126, 197)), 4.0F, true);
        cubeStoneTiledFloorVNID = registerTile("cubestonetiledfloorvn", new SimpleFloorTile("cubestonetiledfloorvn", new Color(94, 53, 177)), 2.0F, true);
        cubeDeepStoneTiledFloorVNID = registerTile("cubedeepstonetiledfloorvn", new SimpleFloorTile("cubedeepstonetiledfloorvn", new Color(76, 37, 154)), 2.0F, true);

        // Register our objects
        TableOfAwakeningVN.registerTableOfAwakeningVN();
        //ObjectRegistry.registerObject("exampleobject", new ExampleObject(), 1, true);
        // make sure you make these actually do stuff for the update
        registerObject("cubetreevn", new TreeObject("cubetreevn", "cubelogvn", "cubesaplingvn", new Color(86, 69, 40), 45, 60, 110, "fruitpalmleaves"), 0.0F, false);
        registerObject("cubesaplingvn", new TreeSaplingObject("cubesaplingvn", "cubetreevn", 1800, 2700, false, new String[]{"cubemainlandtilevn","sandtile", "grasstile", "dirttile", "farmland", "snowtile"}), 50.0F, true);
        registerObject("blockberrybushvn", (new FruitBushObject("blockberrybushvn", "blockberrysaplingvn", 900.0F, 1800.0F, "blockberryvn", 1.0F, 2, new Color(60, 29, 95))).setDebrisColor(new Color(50, 115, 44)), 0.0F, false);
        registerObject("blockberrysaplingvn", new BlockberrySaplingObjectVN("blockberrysaplingvn", "blockberrybushvn", 1200, 2100, false, new String[]{"cubemainlandtilevn","sandtile", "grasstile", "dirttile", "farmland", "snowtile"}), 30.0F, true);
        int[] cubeWoodWallVNIDs = WallObject.registerWallObjects("cubewoodvn", "cubewoodwallvn", 0, new Color(74, 84, 166), ToolType.ALL, 2.0F, 6.0F);
        WallObject cubeWoodWall = (WallObject)getObject(cubeWoodWallVNIDs[0]);
        registerObject("cubewoodcolumnvn", new ColumnObject("cubewoodcolumnvn", cubeWoodWall.mapColor, ToolType.ALL), 10.0F, true);
        int[] cubeStoneWallVNIDs = WallObject.registerWallObjects("cubestonevn", "cubestonewallvn", 0, new Color(81, 45, 168), 0.5F, 1.0F);
        WallObject cubeStoneWallVN = (WallObject)getObject(cubeStoneWallVNIDs[0]);
        registerObject("cubestonecolumnvn", new ColumnObject("cubestonecolumnvn", cubeStoneWallVN.mapColor, ToolType.ALL), 10.0F, true);
        RockObject cubeRockVN;
        registerObject("cuberockvn", cubeRockVN = new RockObject("cuberockvn", new Color(109, 35, 241), "cubestonevn"), 0.0F, false);
        cubeRockVN.toolTier = 1;
        SingleRockObject.registerSurfaceRock(cubeRockVN, "cubesurfacerockvn", new Color(127, 127, 127), -1.0F, true);
        registerObject("cubesurfacerocksmallvn", new SingleRockSmall(cubeRockVN, "cubesurfacerocksmallvn", new Color(109, 35, 241)), 0.0F, false);
        registerObject("cubaltorecuberockvn", new RockOreObject(cubeRockVN, "oremask", "cubaltorevn", new Color(150, 115, 65), "cubaltoreitemvn"), 0.0F, false);
        registerObject("ironorecuberock", new RockOreObject(cubeRockVN, "oremask", "ironore", new Color(150, 115, 65), "ironore"), 0.0F, false);
        registerObject("copperorecuberock", new RockOreObject(cubeRockVN, "oremask", "copperore", new Color(110, 52, 29), "copperore"), 0.0F, false);
        registerObject("goldorecuberock", new RockOreObject(cubeRockVN, "oremask", "goldore", new Color(255, 195, 50), "goldore"), 0.0F, false);
        RockObject cubeDeepRockVN;
        registerObject("cubedeeprockvn", cubeDeepRockVN = new RockObject("cubedeeprockvn", new Color(57, 11, 141), "cubedeepstonevn"), 0.0F, false);
        cubeDeepRockVN.toolTier = 3;
        int[] cubeDeepStoneWallVNIDs = WallObject.registerWallObjects("cubedeepstonevn", "cubedeepstonewallvn", 0, new Color(81, 45, 168), 0.5F, 1.0F);
        WallObject cubeDeepStoneWallVN = (WallObject)getObject(cubeDeepStoneWallVNIDs[0]);
        SingleRockObject.registerSurfaceRock(cubeDeepRockVN, "cubedeepgroundrockvn", new Color(127, 127, 127), -1.0F, true);
        registerObject("cubedeepgroundrocksmallvn", new SingleRockSmall(cubeDeepRockVN, "cubedeepgroundrocksmallvn", new Color(109, 35, 241)), 0.0F, false);
        registerObject("cubedeepstonecolumnvn", new ColumnObject("cubedeepstonecolumnvn", cubeDeepStoneWallVN.mapColor, ToolType.ALL), 10.0F, true);
        //registerObject("ultrabrighttorchvn", new UltrabrightTorchVNObject("torch", new Color(47, 231, 187), 50.0F, 0.2F), 0.1F, true);
        registerObject("gearcontactbeaconvn", new GEARContactBeaconVNObject(),0,false);



        // Register our biomes
        FLATLANDS = registerBiome("flatlandsvn", new FlatlandsBiomeVN(), 100, "flatlandsvn");


        // Register our tech

        // Register our items

        //Armor Sets

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

        // Wooden (Mage) Set

        registerItem("cubalthelmetvn", new CubaltHelmetVN(), 110.0F, true);
        registerItem("cubaltchestplatevn", new CubaltChestplateVN(), 160.0F, true);
        registerItem("cubaltbootsvn", new CubaltBootsVN(), 80.0F, true);

        //Vanity :3 Set
        registerItem("luckychickenmaskvn", (new HelmetArmorItem(0, (DamageType)null, 0, Item.Rarity.COMMON, "luckychickenmaskvn")).drawBodyPart(false), 50.0F, true);
        registerItem("luckychickencostumeshirtvn", new ChestArmorItem(0, 0, Item.Rarity.COMMON, "luckychickencostumeshirtvn", "luckychickencostumearmsvn"), 50.0F, true);
        registerItem("luckychickencostumebootsvn", new BootsArmorItem(0, 0, Item.Rarity.COMMON, "luckychickencostumebootsvn"), 50.0F, true);
        // Trickets

        ItemRegistry.registerItem("foxtokenvn", new SimpleTrinketItem(Item.Rarity.UNIQUE, "foxtokenvnbuff", 200), 300.0F, true);
        ItemRegistry.registerItem("thecollectorsmagnetvn", new SimpleTrinketItem(Item.Rarity.LEGENDARY, "collectorsmagnetvnbuff", 200).addDisables(new String[]{"itemattractor"}), 800.0F, true);
        ItemRegistry.registerItem("nightmareheadvn", new SimpleTrinketItem(Item.Rarity.LEGENDARY, "nightmareheadvnbuff", 400), 400.0F, true);
        ItemRegistry.registerItem("trueexplorersatchelvn", new CombinedTrinketItem(Item.Rarity.UNIQUE, 1500, new String[]{"explorersatchel", "explorercloak", "spikedbatboots", "ancientrelics", "nightmareheadvn"}), 200.0F, true);
        ItemRegistry.registerItem("foxtailtrinketvn", new SimpleTrinketItem(Item.Rarity.NORMAL, "foxtailvnbuff", 100), 400.0F, true);
        ItemRegistry.registerItem("yourgemcollectionvn", new SimpleTrinketItem(Item.Rarity.UNIQUE, "yourgemcollectionvnbuff", 1000), 5000.0F, true);
        ItemRegistry.registerItem("clovertokenvn", new SimpleTrinketItem(Item.Rarity.RARE, "clovertokenbuff", 200), 500.0F, true);
        ItemRegistry.registerItem("windmedallionvn", new SimpleTrinketItem(Item.Rarity.RARE, "windmedallionvnbuff", 100), 200.0F, true);
        ItemRegistry.registerItem("treemedallionvn", new SimpleTrinketItem(Item.Rarity.RARE, "treemedallionvnbuff", 100), 200.0F, true);
        ItemRegistry.registerItem("meatmedallionvn", new SimpleTrinketItem(Item.Rarity.RARE, "meatmedallionvnbuff", 100), 200.0F, true);
        ItemRegistry.registerItem("stonemedallionvn", new SimpleTrinketItem(Item.Rarity.RARE, "stonemedallionvnbuff", 100), 200.0F, true);
        ItemRegistry.registerItem("medallionboardvn", new SimpleTrinketItem(Item.Rarity.UNIQUE, "medallionboardvnbuff", 300), 800.0F, true);
        ItemRegistry.registerItem("thebygonecrestvn", new SimpleTrinketItem(Item.Rarity.UNIQUE, "thebygonecrestvnbuff", 1000), 800.0F, true);
        ItemRegistry.registerItem("halovn", new SimpleTrinketItem(Item.Rarity.RARE, "halovnbuff", 300), 800.0F, true);
        ItemRegistry.registerItem("cubaltshieldvn", new CubaltShieldVNToolItem(Item.Rarity.UNCOMMON, 1200), 2000, true);
        ItemRegistry.registerItem("protectorsealvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "protectorsealvntrinketbuff", 120), 500.0F, true);
        ItemRegistry.registerItem("ruinedgolemsealvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "ruinedgolemsealvntrinketbuff", 120), 500.0F, true);
        ItemRegistry.registerItem("flowingenergysealvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "flowingenergysealvntrinketbuff", 120), 500.0F, true);
        ItemRegistry.registerItem("speedstersealvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "speedstersealvntrinketbuff", 120), 500.0F, true);
        ItemRegistry.registerItem("demonwarriorsealvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "demonwarriorsealvntrinketbuff", 120), 500.0F, true);
        ItemRegistry.registerItem("holypaladinsealvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "holypaladinsealvntrinketbuff", 120), 500.0F, true);
        ItemRegistry.registerItem("archbishopcowlvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "archbishopcowlvntrinketbuff", 120), 500.0F, true);


        //Just The Gems
        ItemRegistry.registerItem("healthgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "healthgemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("regengemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "regengemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("resiliencegemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "resiliencegemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("resilienceregengemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "resilienceregengemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("managemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "managemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("manaregengemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "manaregengemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("bloodgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "bloodgemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("damagegemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "damagegemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("critgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "critgemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("speedgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "speedgemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("dashgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "dashgemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("summongemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "summongemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("armorgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "armorgemvnbuff", 200), 100.0F, true);
        ItemRegistry.registerItem("hookgemvn", new SimpleTrinketItem(Item.Rarity.UNCOMMON, "hookgemvnbuff", 200), 100.0F, true);

        // Items
        ItemRegistry.registerItem("acrumplednotevn", new ACrumpledNoteVN(), 1000, true);
        ItemRegistry.registerItem("novafragmentvn", new NovaFragmentMaterialItem(), 2, true);
        ItemRegistry.registerItem("novashardvn", new NovaShardMaterialItem(), 10, true);
        ItemRegistry.registerItem("novaclustervn", new NovaClusterMaterialItem(), 100, true);
        ItemRegistry.registerItem("shapeshardsvn", new ShapeShardsMaterialItem(), 10, true);
        registerItem("cubaltpickaxevn", new CustomPickaxeToolItem(400, 150, 2, 20, 50, 50, 800, Item.Rarity.UNCOMMON) {
            public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
                ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
                tooltips.add(Localization.translate("itemtooltip", "tungstentooltip"), 350);
                return tooltips;
            }
        }, 160.0F, true);
        registerItem("cubaltaxevn", new CustomAxeToolItem(400, 150, 2, 20, 50, 50, 800, Item.Rarity.UNCOMMON), 160.0F, true);
        registerItem("cubaltshovelvn", new CustomShovelToolItem(400, 150, 2, 20, 50, 50, 800, Item.Rarity.UNCOMMON), 160.0F, true);
        ItemRegistry.registerItem("cubaltoreitemvn", (new MatItem(250, Item.Rarity.UNCOMMON, new String[0])).setItemCategory(new String[]{"materials", "ore"}), 1.0F, true);
        ItemRegistry.registerItem("cubaltbarvn", (new MatItem(100, Item.Rarity.UNCOMMON, new String[0])).setItemCategory(new String[]{"materials", "bars"}), 4.0F, true);
        ItemRegistry.registerItem("cubelogvn", (new MatItem(250, Item.Rarity.UNCOMMON, new String[]{"anylog"})).setItemCategory(new String[]{"materials", "logs"}), 2.0F, true);
        registerItem("cubestonevn", new StonePlaceableItem(500), 0.1F, true);
        registerItem("cubedeepstonevn", new StonePlaceableItem(500), 0.1F, true);
        registerItem("blockberryvn", (new FoodConsumableItem(100, Item.Rarity.UNCOMMON, Settler.FOOD_SIMPLE, 15, 300, new ModifierValue[]{new ModifierValue(BuffModifiers.ARMOR_FLAT, 5)})).spoilDuration(960).addGlobalIngredient(new String[]{"anycompostable", "anyfruit"}).setItemCategory(new String[]{"consumable", "rawfood"}), 4.0F, true);
        ItemRegistry.registerItem("nightbladevn", new NightbladeSwordVN(), 300, true);
        ItemRegistry.registerItem("celestialembervn", new CelestEmberStaff(), 800, true);
        ItemRegistry.registerItem("saplingswordvn", new SaplingSword(), 60, true);
        ItemRegistry.registerItem("holytomevn", new HolyTome(), 400, true);
        ItemRegistry.registerItem("unholytomevn", new UnholyTome(), 400, true);
        ItemRegistry.registerItem("novabulletvn", new NovaBulletItem(), 10, true);
        ItemRegistry.registerItem("lmgvn", new LMGVN(), 600, true);
        ItemRegistry.registerItem("bigbrainonastickvn", new BigBrainOnAStickVN(), 100, true);
        ItemRegistry.registerItem("verdantstaffvn", new VerdantStaff(), 60, true);
        ItemRegistry.registerItem("doomedbowvn", new DoomedBow(), 500, true);
        ItemRegistry.registerItem("novaarrowvn", new NovaArrowItem(), 10, true);
        ItemRegistry.registerItem("goldarrowvn", new GoldArrowItem(), 10, true);
        ItemRegistry.registerItem("codblastervn", new Codblaster(), 200, true);
        ItemRegistry.registerItem("glassshardswordvn", new GlassShardSword(), 150, true);
        ItemRegistry.registerItem("repeatingcrossbowvn", new RepeatingCrossbow(), 500, true);
        ItemRegistry.registerItem("ironsoulglaivevn", new IronsoulGlaiveVN(), 500, true);
        ItemRegistry.registerItem("queenspiderstaffvn", new QueenSpiderStaff(), 100, true);
        ItemRegistry.registerItem("necromancersrequiemvn", new NecromancersRequiemVN(), 1000, true);
        ItemRegistry.registerItem("acornlobbervn", new AcornLobberVN(), 60, true);
        ItemRegistry.registerItem("woodenbulletvn", new WoodenBulletVNItem(), 5, true);
        ItemRegistry.registerItem("cloudsvn", new CloudMaterialItem(), 5, true);
        ItemRegistry.registerItem("windarrowvn", new WindArrowItem(), 10, true);
        ItemRegistry.registerItem("theblowingwindvn", new TheBlowingWind(), 300, true);
        ItemRegistry.registerItem("awakeninatorvn", new AwakeninatorMaterialItem(), 400, true);
        ItemRegistry.registerItem("galevn", new GaleVN(), 200, true);
        ItemRegistry.registerItem("hurricanevn", new HurricaneVN(), 100, true);
        ItemRegistry.registerItem("windbulletvn", new WindRoundItem(), 5, true);
        ItemRegistry.registerItem("novaheartvn", new NovaHeartItem(), 200, true);
        ItemRegistry.registerItem("sentientcrownvn", new PetFollowerPlaceableItem("petsentientcrownvn", Item.Rarity.LEGENDARY), 1000.0F, true);
        ItemRegistry.registerItem("thejadewaraxevn", new TheJadeWaraxe(), 600, true);
        ItemRegistry.registerItem("heavierhammervn", new HeavierHammerVN(), 300, true);
        ItemRegistry.registerItem("heaviesthammervn", new HeaviestHammerVN(), 800, true);
        ItemRegistry.registerItem("themountainvn", new TheMountainVN(), 800, true);
        ItemRegistry.registerItem("thunderingrodvn", new ThunderingRodVN(), 100, true);
        ItemRegistry.registerItem("eyeofthestormvn", new EyeOfTheStormVN(), 500, true);
        ItemRegistry.registerItem("crimsontempestvn", new CrimsonTempestVN(), 800, true);
        ItemRegistry.registerItem("monsterpheromonesvn", new MonsterPheromonesItem(), 600, true);
        ItemRegistry.registerItem("arachnoremotevn", new ArachnoRemoteVN(), 600, true);
        ItemRegistry.registerItem("novicetomevn", new NoviceTome(), 200, true);
        ItemRegistry.registerItem("intermediatetomevn", new IntermediateTome(), 200, true);
        ItemRegistry.registerItem("experttomevn", new ExpertTome(), 700, true);
        ItemRegistry.registerItem("mastertomevn", new MasterTome(), 1000, true);
        ItemRegistry.registerItem("magicfordummiesvn", new MagicForDummiesVN(), 2000, true);
        ItemRegistry.registerItem("stormboltervn", new StormbolterVN(), 300, true);
        ItemRegistry.registerItem("stormbringervn", new StormbringerVN(), 300, true);
        ItemRegistry.registerItem("arainydayvn", new ARainyDayVN(), 300, true);
        ItemRegistry.registerItem("dustygemsackvn", new DustyGemSackVN(), 50, true);
        ItemRegistry.registerItem("gemdustvn", new GemDustMaterialItem(), 4, true);
        ItemRegistry.registerItem("novakatanavn", new NovaKatanaVN(), 2000, true);
        registerItem("titanbustergreatswordvn", new TitanBusterGreatswordVN(), 200.0F, true);
        ItemRegistry.registerItem("cubecallervn", new CubeCallerVN(), 800, true);
        ItemRegistry.registerItem("eyebeamvn", new EyebeamVN(), 800, true);
        ItemRegistry.registerItem("spherecererhatvn", new SpherecererHatVN(), 800, true);
        ItemRegistry.registerItem("bastionboxvn", new BastionBoxItemVN(), 200, true);
        ItemRegistry.registerItem("gearresiliencematrixvn", new GEARResilienceMatrixItemVN(), 200, true);
        registerItem("cavedemolishervn", new CaveDemolisherVNToolItem(), 60.0F, true);
        registerItem("grandphoenixgreatbowvn", new GrandPhoenixGreatbowVN(), 1000.0F, true);



        ItemRegistry.registerItem("portablegearcontactbeaconvn", new GEARSphereSpawnItemVN(), 10,true);
        registerItem("importedcubevn", new ImportedAnimalSpawnItem(12, true, "cubemobvn"), 200.0F, true);
        registerItem("importedpyramidvn", new ImportedAnimalSpawnItem(12, true, "pyramidmobvn"), 200.0F, true);
        registerItem("importednightmarevn", new ImportedAnimalSpawnItem(12, true, "nightmaremobvn"), 200.0F, true);
        registerItem("importedfoxvn", new ImportedAnimalSpawnItem(1, true, "foxmobvn"), 200.0F, true);
        registerItem("importedluckychickenvn", new ImportedAnimalSpawnItem(1, true, "luckychickenmobvn"), 200.0F, true);
        registerItem("importedcavespidervn", new ImportedAnimalSpawnItem(12, true, "giantcavespider"), 200.0F, true);
        registerItem("importedgustvn", new ImportedAnimalSpawnItem(12, true, "gustmobvn"), 200.0F, true);



        // Register our mob
        registerMob("nightmaremobvn", NightmareMobVN.class, true);
        registerMob("snowynightmaremobvn", SnowyNightmareMobVN.class, true);
        registerMob("foxmobvn", FoxMobVN.class, true);
        registerMob("gustmobvn", GustMobVN.class, true);
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

        // Register our projectile
        registerProjectile("celestialemberprojectile", CelestEmberProjectile.class, "celestialemberprojectile", "celestialemberprojectile_shadow");
        registerProjectile("nightbladevnproj", NightbladeVNProjectile.class, "nightbladevnproj", "chain");
        registerProjectile("leafshot", SaplingSwordProjectile.class, "leafshot", "leafshot");
        registerProjectile("holyshot", HolyTomeProjectile.class, "holyshot", "holyshot_shadow");
        registerProjectile("unholyshot", UnholyTomeProjectile.class, "unholyshot", "unholyshot_shadow");
        registerProjectile("verdantflowershotvn", VerdantStaffFlowerProjectile.class, "verdantflowershotvn", "swampdwellerstaffflower_shadow");
        registerProjectile("verdantflowerpetalvn", VerdantStaffPetalProjectile.class, "verdantflowerpetalvn", "swampdwellerstaffpetal_shadow");
        registerProjectile("doomedarrow", DoomedBowProjectile.class, "doomedarrow", "chain");
        registerProjectile("novaarrow", NovaArrowProjectile.class, "novaarrow", "arrow_shadow");
        registerProjectile("goldarrowvn", GoldArrowProjectile.class, "goldarrowvn", "arrow_shadow");
        registerProjectile("chain", NovaBulletProjectile.class, "chain", "chain");
        registerProjectile("woodbulletvn", WoodenBulletVNProjectile.class, "chain", "chain");
        registerProjectile("windarrowvn", WindArrowProjectile.class, "windarrowvnprojectile", "arrow_shadow");
        registerProjectile("windround", WindRoundProjectile.class, "chain", "chain");
        registerProjectile("jadeshot", JadeShotProjectile.class, "jadeshot", "jadeshot_shadow");
        registerProjectile("heavierhammershotvn", HeavierHammerShotVNProjectile.class, "heavierhammershotvn", "stone_shadow");
        registerProjectile("heaviesthammershotvn", HeaviestHammerShotVNProjectile.class, "heaviesthammershotvn", "stone_shadow");
        registerProjectile("themountainshotvn", TheMountainShotVNProjectile.class, "themountainshotvn", "stone_shadow");
        registerProjectile("thunderboltvn", ThunderboltVNProjectile.class, "thunderboltprojvn", "thunderboltvnproj_shadow");
        registerProjectile("thunderboltbluevn", ThunderboltBlueVNProjectile.class, "thunderboltblueprojvn", "thunderboltvnproj_shadow");
        registerProjectile("thunderboltredvn", ThunderboltRedVNProjectile.class, "thunderboltredprojvn", "thunderboltvnproj_shadow");
        registerProjectile("novicetomeproj", NoviceTomeProjectile.class, "novicetomeshot", "novicetomeproj_shadow");
        registerProjectile("intermediatetomeproj", IntermediateTomeProjectile.class, "intermediatetomeshot", "novicetomeproj_shadow");
        registerProjectile("experttomeproj", ExpertTomeProjectile.class, "experttomeshot", "novicetomeproj_shadow");
        registerProjectile("mastertomeproj", MasterTomeProjectile.class, "mastertomeshot", "novicetomeproj_shadow");
        registerProjectile("magicfordummiesproj", MagicForDummiesVNProjectile.class, "magicfordummiesshot", "novicetomeproj_shadow");
        registerProjectile("spherecerershotvn", SpherecererShotVNProjectile.class, "spherecerershotvn", "novicetomeproj_shadow");
        registerProjectile("cavedemolisherprojvn", CaveDemolisherVNProjectile.class, "cavedemolisherprojvn", "cavedemolisherprojvn_shadow");
        registerProjectile("gearsphereminionpodvn", GEARSphereMinionPodVN.class, "gearsphereminionpodvn", "queenspideregg_shadow");
        registerProjectile("acornprojectilevn", AcornProjectile.class, "acornprojectilevn", "acornprojectilevn_shadow");


        // Register buffs

        // Armors
        registerBuff("woodensetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MAGIC_DAMAGE, 0.15f), new ModifierValue(BuffModifiers.MAGIC_CRIT_CHANCE, 0.05f), new ModifierValue(BuffModifiers.MAX_MANA_FLAT, 30),}));
        registerBuff("ancientjunglesetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.COMBAT_REGEN, 0.05F), new ModifierValue(BuffModifiers.SPEED, 0.05F), new ModifierValue(BuffModifiers.SWIM_SPEED, 0.10F), new ModifierValue(BuffModifiers.CRIT_CHANCE, 0.05F)}));
        registerBuff("windsetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ATTACK_SPEED, 0.25F), new ModifierValue(BuffModifiers.CRIT_CHANCE, 0.05F), new ModifierValue(BuffModifiers.SPEED, 0.10F)}));
        registerBuff("meatsetvnbonusbuff", new MeatSetBonusBuff());
        registerBuff("cubaltsetvnbonusbuff", new CubaltSetVNBonusBuff());
        registerBuff("stonesetvnbonusbuff", new SimpleSetBonusBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ATTACK_SPEED, 0.10f), new ModifierValue(BuffModifiers.MAX_RESILIENCE_FLAT, 10), new ModifierValue(BuffModifiers.ARMOR_FLAT, 3)}));
        registerBuff("chilledbloodplatehatsetvnbonusbuff", new ChilledBloodplateHatSetBonusBuff());
        registerBuff("chilledbloodplatehoodsetvnbonusbuff", new ChilledBloodplateHoodSetBonusBuff());
        registerBuff("chilledbloodplatehelmetsetvnbonusbuff", new ChilledBloodplateHelmetSetBonusBuff());

        // Trinkets
        registerBuff("collectorsmagnetvnbuff", new CollectorsMagnetBuff());
        registerBuff("nightmareheadvnbuff", new NightmareHeadVNBuff());
        registerBuff("foxtokenvnbuff", new FoxTokenVNBuff());
        registerBuff("foxtailvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.DASH_STACKS, 1), new ModifierValue(BuffModifiers.DASH_COOLDOWN, -0.10F)}));
        registerBuff("yourgemcollectionvnbuff", new YourGemCollectionVNBuff());
        registerBuff("clovertokenvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE, 0.25F), new ModifierValue(BuffModifiers.ALL_DAMAGE, -0.15F)}));
        registerBuff("windmedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.RANGED_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.RANGED_CRIT_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.RANGED_CRIT_CHANCE, 0.05F)}));
        registerBuff("stonemedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MELEE_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.MELEE_CRIT_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.MELEE_CRIT_CHANCE, 0.05F)}));
        registerBuff("meatmedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.SUMMON_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, 0.05F)}));
        registerBuff("treemedallionvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MAGIC_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.MAGIC_CRIT_CHANCE, 0.05F)}));
        registerBuff("medallionboardvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ALL_DAMAGE, 0.1F), new ModifierValue(BuffModifiers.CRIT_DAMAGE, 0.1F), new ModifierValue(BuffModifiers.CRIT_CHANCE, 0.1F)}));
        registerBuff("thebygonecrestvnbuff", new TheBygoneCrestVNBuff());
        registerBuff("halovnbuff", new HaloVNBuff());
        registerBuff("protectorsealvntrinketbuff", new ProtectorSealVNTrinketBuff());
        PROTECTOR_SEAL_VN_ACTIVE = registerBuff("protectorsealvnactivebuff", new ProtectorSealVNActiveBuff());
        registerBuff("ruinedgolemsealvntrinketbuff", new RuinedGolemSealVNTrinketBuff());
        RUINED_GOLEM_VN_ACTIVE = registerBuff("ruinedgolemsealvnactivebuff", new ProtectorSealVNActiveBuff());
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

        // Gem Trinkets
        registerBuff("healthgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MAX_HEALTH_FLAT, 50)}));
        registerBuff("regengemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 1.00F)}));
        registerBuff("resiliencegemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MAX_RESILIENCE_FLAT, 50)}));
        registerBuff("resilienceregengemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.RESILIENCE_REGEN_FLAT, 0.50F)}));
        registerBuff("managemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MAX_MANA_FLAT, 100)}));
        registerBuff("manaregengemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.COMBAT_MANA_REGEN_FLAT, 1.00F)}));
        registerBuff("bloodgemvnbuff", new BloodGemBuff());
        registerBuff("damagegemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ALL_DAMAGE, 0.10F)}));
        registerBuff("critgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE, 0.10F)}));
        registerBuff("speedgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, 0.25F)}));
        registerBuff("dashgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.DASH_STACKS, 1)}));
        registerBuff("summongemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.MAX_SUMMONS, 1)}));
        registerBuff("armorgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ARMOR_FLAT, 10)}));
        registerBuff("hookgemvnbuff", new SimpleTrinketBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.FISHING_LINES, 1)}));

        // Buffs/Debuffs
        COSMIC_FIRE_VN = registerBuff("cosmicfirevn", new CosmicFireVNBuff());
        registerBuff("bleedingvn", new BleedingBuff());
        registerBuff("monsterpheromonesvn", new MonsterPheromonesBuff());
        PROTECTOR_SEAL_VN_COOLDOWN = registerBuff("protectorsealvncooldown", new ShownCooldownBuff());
        RUINED_GOLEM_VN_COOLDOWN = registerBuff("ruinedgolemsealvncooldown", new ShownCooldownBuff());
        FLOWING_ENERGY_VN_COOLDOWN = registerBuff("flowingenergysealvncooldown", new ShownCooldownBuff());
        SPEEDSTER_VN_COOLDOWN = registerBuff("speedstersealvncooldown", new ShownCooldownBuff());
        DEMON_WARRIOR_VN_COOLDOWN = registerBuff("demonwarriorsealvncooldown", new ShownCooldownBuff());
        HOLY_PALADIN_VN_COOLDOWN = registerBuff("holypaladinsealvncooldown", new ShownCooldownBuff());
        ARCHBISHOP_COWL_VN_COOLDOWN = registerBuff("archbishopcowlvncooldown", new ShownCooldownBuff());


        // Register our levels
        LevelRegistry.registerLevel("flatlandssurfacevn", FlatlandsSurfaceLevelVN.class);
        LevelRegistry.registerLevel("flatlandscavevn", FlatlandsCaveLevelVN.class);
        LevelRegistry.registerLevel("flatlandsdeepcavevn", FlatlandsDeepCaveLevelVN.class);

        //Register Music

        //float oldMusicVolumeModifier = 0.6F;
        //HUBMUSICVN = registerMusic("hubmusic", "music/hubmusic", (GameMessage)null, new StaticMessage("Hubmusic"), new Color(125, 164, 45), new Color(47, 105, 12)).setVolumeModifier(oldMusicVolumeModifier);

        TABLEOFAWAKENINGVN = registerTech("tableofawakeningvn", "tableofawakeningvn");
        //PacketRegistry.registerPacket(ExamplePacket.class);

        // Makes every enemy/mob in the game drop a Nova Fragment when it dies
        GameEvents.addListener(MobLootTableDropsEvent.class, new GameEventListener<MobLootTableDropsEvent>() {
            @Override
            public void onEvent(MobLootTableDropsEvent event) {

                Random rand = new Random();

                // Basically 1000 is 100%, 900 is 90%, 800 is 80%, etc.
                int n = rand.nextInt(1000);

                // Makes it 1 - 1000 instead of 0 - 999
                n += 1;

                if (event.mob.isHostile)  {

                    event.drops.add(new InventoryItem("novafragmentvn"));

                    // This gives mobs a 2% chance of dropping a Nova Shard
                    if(n <= 20) {
                        event.drops.add(new InventoryItem("novashardvn"));
                    }
                    // This gives mobs a 0.1% chance of dropping a sentient crown
                    if(n <= 1) {
                        event.drops.add(new InventoryItem("sentientcrownvn"));
                    }

                }

                if (event.mob.isBoss())  {

                    event.drops.add(new InventoryItem("novashardvn", 2));

                    // This gives bosses a 8% chance of dropping an Awakeninator
                    if(n <= 80) {
                        event.drops.add(new InventoryItem("awakeninatorvn"));
                    }

                    // This gives bosses a 0.5% chance of dropping a sentient crown
                    if(n <= 5) {
                        event.drops.add(new InventoryItem("sentientcrownvn"));
                    }

                }

            }
        });

    }

    public void initResources() {
        NightmareMobVN.texture = GameTexture.fromFile("mobs/nightmaremobvn");
        SnowyNightmareMobVN.texture = GameTexture.fromFile("mobs/snowynightmaremobvn");
        FoxMobVN.texture = GameTexture.fromFile("mobs/foxmobvn");
        GustMobVN.texture = GameTexture.fromFile("mobs/gustmobvn");
        PetSentientCrownVN.texture = GameTexture.fromFile("mobs/petsentientcrown");
        LuckyChickenMobVN.texture = GameTexture.fromFile("mobs/luckychickenmobvn");
        PlanewalkerMobVN.texture = GameTexture.fromFile("mobs/planewalkermobvn");
        AllSeeingCubeMobVN.texture = GameTexture.fromFile("mobs/allseeingcubemobvn");
        BabyCubeMobVN.texture = GameTexture.fromFile("mobs/babycubemobvn");
        BabyTitanCubeMobVN.texture = GameTexture.fromFile("mobs/babytitancubemobvn");
        CubeMobVN.texture = GameTexture.fromFile("mobs/cubemobvn");
        NightmareCubeMobVN.texture = GameTexture.fromFile("mobs/nightmarecubemobvn");
        IceCubeMobVN.texture = GameTexture.fromFile("mobs/icecubemobvn");
        TitanCubeMobVN.texture = GameTexture.fromFile("mobs/titancubemobvn");
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
        //sounds
        //electronicactivatevn = GameSound.fromFile("sound/soundeffects/electronicactivatevn");

    }

    public void postInit() {
        // Add recipes

        // Example item recipe, crafted in inventory for 2 iron bars
        Recipes.registerModRecipe(new Recipe(
                "novashardvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("novafragmentvn", 16)
                }
        ).showAfter("craftingguide")); // Show recipe after crafting guide recipe

        Recipes.registerModRecipe(new Recipe(
                "novaclustervn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("novashardvn", 50)
                }
        ).showAfter("novashardvn")); // Show recipe after crafting guide recipe

        Recipes.registerModRecipe(new Recipe(
                "foxtokenvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("goldbar", 12),
                        new Ingredient("demonicbar", 3),
                        new Ingredient("ivybar", 3),
                        new Ingredient("novaclustervn", 1)
                }
        ).showAfter("craftingguide")); // Show recipe after crafting guide recipe

        Recipes.registerModRecipe(new Recipe(
                "cubaltbarvn",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("cubaltoreitemvn", 3),
                }
        ).showAfter("craftingguide")); // Show recipe after crafting guide recipe

        Recipes.registerModRecipe(new Recipe(
                "bigbrainonastickvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("brainonastick", 1),
                        new Ingredient("anyrawmeat", 60),
                        new Ingredient("novashardvn", 12)
                }
        ).showAfter("spiderstaff")); // Show recipe after wood boat recipe

        Recipes.registerModRecipe(new Recipe(
                "queenspiderstaffvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("spiderstaff", 1),
                        new Ingredient("cavespidergland", 12),
                        new Ingredient("wool", 10),
                        new Ingredient("novashardvn", 4)
                }
        ).showAfter("spiderstaff")); // Show recipe after wood boat recipe

        Recipes.registerModRecipe(new Recipe(
                "novicetomevn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anylog", 16),
                        new Ingredient("anysapling", 6),
                        new Ingredient("novafragmentvn", 24)
                }
        ).showAfter("craftingguide")); // Show recipe after wood boat recipe

        Recipes.registerModRecipe(new Recipe(
                "intermediatetomevn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("novicetomevn", 1),
                        new Ingredient("demonicbar", 6),
                        new Ingredient("novashardvn", 8)
                }
        ).showAfter("novicetomevn")); // Show recipe after wood boat recipe

        Recipes.registerModRecipe(new Recipe(
                "novakatanavn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("reinforcedkatana", 1),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 4),
                }
        ).showAfter("themountainvn")); // Show the recipe after tungsten sword recipe
        // Nightblade Recipe
        Recipes.registerModRecipe(new Recipe(
                "nightbladevn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("demonicbar", 15),
                        new Ingredient("novashardvn", 8)
                }
        ).showAfter("demonicsword")); // Show the recipe after tungsten sword recipe
        // Celestial Ember Staff
        Recipes.registerModRecipe(new Recipe(
                "celestialembervn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("tungstenbar", 15),
                        new Ingredient("ectoplasm", 20),
                        new Ingredient("novaclustervn", 2)
                }
        ).showAfter("iciclestaff")); // Show the recipe after icicle staff recipe
        Recipes.registerModRecipe(new Recipe(
                "saplingswordvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("woodsword", 1),
                        new Ingredient("anysapling", 20),
                        new Ingredient("novashardvn", 3)
                }
        ).showAfter("craftingguide")); // Show recipe after wood boat recipe

        Recipes.registerModRecipe(new Recipe(
                "holytomevn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ectoplasm", 20),
                        new Ingredient("goldbar", 3),
                        new Ingredient("book", 1),
                        new Ingredient("novashardvn", 12)
                }
        ).showAfter("shadowbolt")); // Show recipe after Shadow Bolt
        Recipes.registerModRecipe(new Recipe(
                "holytomevn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("unholytomevn", 1),
                        new Ingredient("novashardvn", 4)
                }
        ).showAfter("regenpendant")); // Show recipe after Shadow Bolt
        Recipes.registerModRecipe(new Recipe(
                "unholytomevn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("holytomevn", 1),
                        new Ingredient("novashardvn", 4)
                }
        ).showAfter("regenpendant")); // Show recipe after Regen Pendant
        Recipes.registerModRecipe(new Recipe(
                "novabulletvn",
                100,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("voidbullet", 100),
                        new Ingredient("novashardvn", 1)
                }
        ).showAfter("voidbullet")); // Show recipe after void bullets

        Recipes.registerModRecipe(new Recipe(
                "windbulletvn",
                50,
                RecipeTechRegistry.NONE,
                new Ingredient[]{
                        new Ingredient("cloudsvn", 5),
                        new Ingredient("novafragmentvn", 3),
                }
        ).showAfter("simplebullet")); // Show recipe after void bullets

        Recipes.registerModRecipe(new Recipe(
                "goldarrowvn",
                5,
                RecipeTechRegistry.NONE,
                new Ingredient[]{
                        new Ingredient("goldbar", 1),
                }
        ).showAfter("ironarrow")); // Show recipe after void bullets

        Recipes.registerModRecipe(new Recipe(
                "windarrowvn",
                50,
                RecipeTechRegistry.NONE,
                new Ingredient[]{
                        new Ingredient("cloudsvn", 5),
                        new Ingredient("novafragmentvn", 3),
                }
        ).showAfter("ironarrow")); // Show recipe after void bullets

        Recipes.registerModRecipe(new Recipe(
                "novaarrowvn",
                50,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("goldarrowvn", 50),
                        new Ingredient("novashardvn", 1)
                }
        ).showAfter("poisonarrow")); // Show recipe after void bullets

        Recipes.registerModRecipe(new Recipe(
                "lmgvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("machinegun", 1),
                        new Ingredient("tungstenbar", 16),
                        new Ingredient("novashardvn", 12)
                }
        ).showAfter("tungstenbow")); // Show recipe after void bullets

        Recipes.registerModRecipe(new Recipe(
                "doomedbowvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("bone", 20),
                        new Ingredient("ectoplasm", 15),
                        new Ingredient("tungstenbar", 12),
                        new Ingredient("novashardvn", 12)
                }
        ).showAfter("tungstenbow")); // Show recipe after Tungsten Bow

        Recipes.registerModRecipe(new Recipe(
                "repeatingcrossbowvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("tungstenbar", 14),
                        new Ingredient("novashardvn", 10)
                }
        ).showAfter("tungstenbow")); // Show recipe after Tungsten Bow

        Recipes.registerModRecipe(new Recipe(
                "ironsoulglaivevn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", 12),
                        new Ingredient("tungstenbar", 12),
                        new Ingredient("ectoplasm", 8),
                        new Ingredient("novashardvn", 10)
                }
        ).showAfter("tungstenspear")); // Show recipe after Tungsten Bow

        Recipes.registerModRecipe(new Recipe(
                "codblastervn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("handgun", 1),
                        new Ingredient("cod", 8),
                        new Ingredient("novashardvn", 6)
                }
        ).showAfter("ivybow")); // Show recipe after Ivy Bow

        Recipes.registerModRecipe(new Recipe(
                "glassshardswordvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("sandtile", 50),
                        new Ingredient("ironbar", 15),
                        new Ingredient("novashardvn", 6)
                }
        ).showAfter("ivysword")); // Show recipe after Ivy Bow

        Recipes.registerModRecipe(new Recipe(
                "necromancersrequiemvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("bigbrainonastickvn", 1),
                        new Ingredient("skeletonstaff", 1),
                        new Ingredient("bone", 30),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("cryostaff")); // Show recipe after cryo staff

        Recipes.registerModRecipe(new Recipe(
                "acornlobbervn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anylog", 30),
                        new Ingredient("anysapling", 16),
                        new Ingredient("novafragmentvn", 16),
                }
        ).showAfter("craftingguide"));

        Recipes.registerModRecipe(new Recipe(
                "woodenbulletvn",
                50,
                RecipeTechRegistry.NONE,
                new Ingredient[]{
                        new Ingredient("novafragmentvn", 3),
                        new Ingredient("anylog", 5),
                        new Ingredient("anysapling", 3),
                }
        ).showAfter("novaarrowvn"));

        Recipes.registerModRecipe(new Recipe(
                "galevn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("acornlobbervn", 1),
                        new Ingredient("cloudsvn", 24),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("acornlobbervn"));

        Recipes.registerModRecipe(new Recipe(
                "theblowingwindvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("demonicbow", 1),
                        new Ingredient("cloudsvn", 24),
                        new Ingredient("novashardvn", 10),
                }
        ).showAfter("demonicbow"));

        Recipes.registerModRecipe(new Recipe(
                "novaheartvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("lifeelixir", 1),
                        new Ingredient("lifequartz", 10),
                        new Ingredient("novaclustervn", 1),
                }
        ).showAfter("lifeelixir"));

        Recipes.registerModRecipe(new Recipe(
                "bastionboxvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("cubaltbarvn", 6),
                        new Ingredient("shapeshardsvn", 20),
                        new Ingredient("novashardvn", 16),
                }
        ).showAfter("mysteriousportal"));

        Recipes.registerModRecipe(new Recipe(
                "heavierhammervn",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("heavyhammer", 1),
                        new Ingredient("ironbar", 16),
                        new Ingredient("novashardvn", 16),
                }
        ).showAfter("ironsword"));

        Recipes.registerModRecipe(new Recipe(
                "heaviesthammervn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("heavierhammervn", 1),
                        new Ingredient("tungstenbar", 24),
                        new Ingredient("novashardvn", 32),
                }
        ).showAfter("tungstensword"));

        Recipes.registerModRecipe(new Recipe(
                "thunderingrodvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", 12),
                        new Ingredient("cloudsvn", 24),
                        new Ingredient("novashardvn", 16),
                }
        ).showAfter("woodstaff"));

        Recipes.registerModRecipe(new Recipe(
                "eyeofthestormvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("thunderingrodvn", 1),
                        new Ingredient("tungstenbar", 12),
                        new Ingredient("cloudsvn", 32),
                        new Ingredient("novashardvn", 32),
                }
        ).showAfter("thunderingrodvn"));

        Recipes.registerModRecipe(new Recipe(
                "experttomevn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("intermediatetomevn", 1),
                        new Ingredient("ectoplasm", 12),
                        new Ingredient("tungstenbar", 6),
                        new Ingredient("novashardvn", 26),
                }
        ).showAfter("thunderingrodvn"));

        Recipes.registerModRecipe(new Recipe(
                "mastertomevn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("experttomevn", 1),
                        new Ingredient("ectoplasm", 16),
                        new Ingredient("amethyst", 8),
                        new Ingredient("novashardvn", 26),
                }
        ).showAfter("experttomevn"));

        Recipes.registerModRecipe(new Recipe(
                "magicfordummiesvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("novicetomevn", 1),
                        new Ingredient("intermediatetomevn", 1),
                        new Ingredient("experttomevn", 1),
                        new Ingredient("mastertomevn", 1),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("necromancersrequiemvn"));

        Recipes.registerModRecipe(new Recipe(
                "crimsontempestvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("eyeofthestormvn", 1),
                        new Ingredient("cloudsvn", 128),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("thunderingrodvn"));

        Recipes.registerModRecipe(new Recipe(
                "monsterpheromonesvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("stinkflask", 1),
                        new Ingredient("cloudsvn", 50),
                        new Ingredient("ectoplasm", 25),
                        new Ingredient("novaclustervn", 1),
                }
        ).showAfter("novaheartvn"));

        Recipes.registerModRecipe(new Recipe(
                "themountainvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("heaviesthammervn", 1),
                        new Ingredient("anystone", 5000),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 1),
                }
        ).showAfter("crimsontempestvn"));

        Recipes.registerModRecipe(new Recipe(
                "stormboltervn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("galevn", 1),
                        new Ingredient("thunderingrodvn", 1),
                        new Ingredient("cloudsvn", 16),
                        new Ingredient("novashardvn", 24),
                }
        ).showAfter("codblastervn"));

        Recipes.registerModRecipe(new Recipe(
                "stormbringervn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("hurricanevn", 1),
                        new Ingredient("thunderingrodvn", 1),
                        new Ingredient("cloudsvn", 16),
                        new Ingredient("novashardvn", 24),
                }
        ).showAfter("codblastervn"));

        Recipes.registerModRecipe(new Recipe(
                "arainydayvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("stormbringervn", 1),
                        new Ingredient("lmgvn", 1),
                        new Ingredient("cloudsvn", 32),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 1),
                }
        ).showAfter("novakatanavn"));
        // Objects

        Recipes.registerModRecipe(new Recipe(
                "tableofawakeningvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("demonicbar", 12),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("demonicworkstation"));

        // Trinkets

        Recipes.registerModRecipe(new Recipe(
                "thecollectorsmagnetvn",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("itemattractor", 1),
                        new Ingredient("novaclustervn", 1),
                }
        ).showAfter("toolbox"));

        Recipes.registerModRecipe(new Recipe(
                "windmedallionvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("cloudsvn", 20),
                        new Ingredient("goldbar", 4),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("dreamcatcher"));

        Recipes.registerModRecipe(new Recipe(
                "treemedallionvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anysapling", 30),
                        new Ingredient("goldbar", 4),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("dreamcatcher"));

        Recipes.registerModRecipe(new Recipe(
                "meatmedallionvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anyrawmeat", 20),
                        new Ingredient("goldbar", 4),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("dreamcatcher"));

        Recipes.registerModRecipe(new Recipe(
                "stonemedallionvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anystone", 100),
                        new Ingredient("goldbar", 4),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("dreamcatcher"));

        Recipes.registerModRecipe(new Recipe(
                "medallionboardvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("treemedallionvn", 1),
                        new Ingredient("windmedallionvn", 1),
                        new Ingredient("meatmedallionvn", 1),
                        new Ingredient("stonemedallionvn", 1),
                        new Ingredient("novashardvn", 16),
                }
        ).showAfter("dreamcatcher"));

        // Literally just the Gems

        Recipes.registerModRecipe(new Recipe(
                "hookgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "armorgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "summongemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "dashgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "speedgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "critgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "damagegemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "bloodgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "manaregengemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "managemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "resilienceregengemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "resiliencegemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "regengemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "healthgemvn",
                1,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("gemdustvn", 80),
                        new Ingredient("novashardvn", 6),

                }
        ).showAfter("regenpendant"));

        Recipes.registerModRecipe(new Recipe(
                "yourgemcollectionvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("healthgemvn", 1),
                        new Ingredient("regengemvn", 1),
                        new Ingredient("resiliencegemvn", 1),
                        new Ingredient("resilienceregengemvn", 1),
                        new Ingredient("managemvn", 1),
                        new Ingredient("manaregengemvn", 1),
                        new Ingredient("bloodgemvn", 1),
                        new Ingredient("damagegemvn", 1),
                        new Ingredient("critgemvn", 1),
                        new Ingredient("speedgemvn", 1),
                        new Ingredient("dashgemvn", 1),
                        new Ingredient("summongemvn", 1),
                        new Ingredient("armorgemvn", 1),
                        new Ingredient("hookgemvn", 1),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novashardvn", 20),
                }
        ).showAfter("ancientjunglebootsvn"));

        Recipes.registerModRecipe(new Recipe(
                "trueexplorersatchelvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("explorersatchel", 1),
                        new Ingredient("explorercloak", 1),
                        new Ingredient("spikedbatboots", 1),
                        new Ingredient("ancientrelics", 1),
                        new Ingredient("nightmareheadvn", 1),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("ancientjunglebootsvn"));

        Recipes.registerModRecipe(new Recipe(
                "ruinedgolemsealvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anystone", 100),
                        new Ingredient("woodshield", 1),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("woodshield"));

        Recipes.registerModRecipe(new Recipe(
                "speedstersealvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("speedpotion", 8),
                        new Ingredient("ironbar", 4),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("woodshield"));

        Recipes.registerModRecipe(new Recipe(
                "protectorsealvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("demonicbar", 8),
                        new Ingredient("woodshield", 1),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("woodshield"));

        Recipes.registerModRecipe(new Recipe(
                "flowingenergysealvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("demonicbar", 8),
                        new Ingredient("manapotion", 4),
                        new Ingredient("manaregenpotion", 4),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("woodshield"));

        Recipes.registerModRecipe(new Recipe(
                "archbishopcowlvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("demonwarriorsealvn", 1),
                        new Ingredient("holypaladinsealvn", 1),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("ancientjunglebootsvn"));

        // Vanity

        Recipes.registerModRecipe(new Recipe(
                "luckychickenmaskvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("novashardvn", 24),
                }
        ).showAfter("ancientjunglebootsvn"));

        Recipes.registerModRecipe(new Recipe(
                "luckychickencostumeshirtvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("novashardvn", 24),
                }
        ).showAfter("ancientjunglebootsvn"));

        Recipes.registerModRecipe(new Recipe(
                "luckychickencostumebootsvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("novashardvn", 24),
                }
        ).showAfter("ancientjunglebootsvn"));
        // Armors

        // Cubalt (needs replaced) Set

        Recipes.registerModRecipe(new Recipe(
                "cubaltbootsvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("shapeshardsvn", 20),
                        new Ingredient("cubaltbarvn", 8),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("demonicboots"));

        Recipes.registerModRecipe(new Recipe(
                "cubaltchestplatevn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("shapeshardsvn", 40),
                        new Ingredient("cubaltbarvn", 16),
                        new Ingredient("novashardvn", 16),
                }
        ).showAfter("demonicboots"));

        Recipes.registerModRecipe(new Recipe(
                "cubalthelmetvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("shapeshardsvn", 30),
                        new Ingredient("cubaltbarvn", 10),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("demonicboots"));

        Recipes.registerModRecipe(new Recipe(
                "cubaltshovelvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("shapeshardsvn", 20),
                        new Ingredient("cubaltbarvn", 10),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("demonicboots"));

        Recipes.registerModRecipe(new Recipe(
                "cubaltaxevn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("shapeshardsvn", 20),
                        new Ingredient("cubaltbarvn", 10),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("demonicboots"));

        Recipes.registerModRecipe(new Recipe(
                "cubaltpickaxevn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("shapeshardsvn", 20),
                        new Ingredient("cubaltbarvn", 10),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("demonicboots"));

        // Wooden (Mage) Set

        Recipes.registerModRecipe(new Recipe(
                "woodenbootsvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("clothboots", 1),
                        new Ingredient("anylog", 20),
                        new Ingredient("anysapling", 10),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("clothboots"));

        Recipes.registerModRecipe(new Recipe(
                "woodenchestplatevn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("clothrobe", 1),
                        new Ingredient("anylog", 40),
                        new Ingredient("anysapling", 15),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("clothboots"));

        Recipes.registerModRecipe(new Recipe(
                "woodenhelmetvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("clothhat", 1),
                        new Ingredient("anylog", 30),
                        new Ingredient("anysapling", 10),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("clothboots"));

        // Ancient Jungle Set

        Recipes.registerModRecipe(new Recipe(
                "ancientjunglebootsvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("woodenbootsvn", 1),
                        new Ingredient("anysapling", 80),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("clothboots"));

        Recipes.registerModRecipe(new Recipe(
                "ancientjunglechestplatevn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("woodenchestplatevn", 1),
                        new Ingredient("anysapling", 120),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 3),
                }
        ).showAfter("clothboots"));

        Recipes.registerModRecipe(new Recipe(
                "ancientjunglehelmetvn",
                1,
                VulpesNova.TABLEOFAWAKENINGVN,
                new Ingredient[]{
                        new Ingredient("woodenhelmetvn", 1),
                        new Ingredient("anysapling", 100),
                        new Ingredient("awakeninatorvn", 1),
                        new Ingredient("novaclustervn", 2),
                }
        ).showAfter("clothboots"));

        // Wind (Range) Set

        Recipes.registerModRecipe(new Recipe(
                "windbootsvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("leatherboots", 1),
                        new Ingredient("cloudsvn", 24),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("leatherboots"));

        Recipes.registerModRecipe(new Recipe(
                "windchestplatevn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("leathershirt", 1),
                        new Ingredient("cloudsvn", 30),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("leatherboots"));

        Recipes.registerModRecipe(new Recipe(
                "windhatvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("leatherhood", 1),
                        new Ingredient("cloudsvn", 16),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("leatherboots"));

        // Meat (Summoner) Set

        Recipes.registerModRecipe(new Recipe(
                "meatbootsvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("spiderboots", 1),
                        new Ingredient("anyrawmeat", 24),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("spiderboots"));

        Recipes.registerModRecipe(new Recipe(
                "meatrobevn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("spiderchestplate", 1),
                        new Ingredient("anyrawmeat", 30),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("spiderboots"));

        Recipes.registerModRecipe(new Recipe(
                "meathatvn",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("spiderhelmet", 1),
                        new Ingredient("anyrawmeat", 20),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("spiderboots"));

        // Stone (Melee) Set

        Recipes.registerModRecipe(new Recipe(
                "stonebootsvn",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("goldboots", 1),
                        new Ingredient("anystone", 100),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("goldboots"));

        Recipes.registerModRecipe(new Recipe(
                "stonechestplatevn",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("goldchestplate", 1),
                        new Ingredient("anystone", 160),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("goldboots"));

        Recipes.registerModRecipe(new Recipe(
                "stonehelmetvn",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("goldhelmet", 1),
                        new Ingredient("anystone", 60),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("goldboots"));

        // Chilled Bloodplate Set

        Recipes.registerModRecipe(new Recipe(
                "chilledbloodplatebootsvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("frostboots", 1),
                        new Ingredient("bloodplateboots", 1),
                        new Ingredient("novashardvn", 8),
                }
        ).showAfter("bloodplateboots"));

        Recipes.registerModRecipe(new Recipe(
                "chilledbloodplatechestplatevn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("frostchestplate", 1),
                        new Ingredient("bloodplatechestplate", 1),
                        new Ingredient("novashardvn", 12),
                }
        ).showAfter("bloodplateboots"));

        Recipes.registerModRecipe(new Recipe(
                "chilledbloodplatehatvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("frosthat", 1),
                        new Ingredient("bloodplatecowl", 1),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("bloodplateboots"));

        Recipes.registerModRecipe(new Recipe(
                "chilledbloodplatehoodvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("frosthood", 1),
                        new Ingredient("bloodplatecowl", 1),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("bloodplateboots"));

        Recipes.registerModRecipe(new Recipe(
                "chilledbloodplatehelmetvn",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("frosthelmet", 1),
                        new Ingredient("bloodplatecowl", 1),
                        new Ingredient("novashardvn", 6),
                }
        ).showAfter("bloodplateboots"));

        // Chest Loot


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

        // Mobs no way
        // Spawn tables use a ticket/weight system. In general, common mobs have about 100 tickets.

        Biome.defaultDeepCaveMobs
                .add(40, "nightmaremobvn")
                .add(10, "deadmahmobvn");

        //ForestBiome.defaultSurfaceMobs
                        //.add(5,"allseeingcubemobvn");

        //ForestBiome.defaultCaveMobs
        //.add(5,"allseeingcubemobvn");

        //ForestBiome.defaultDeepCaveMobs
        //.add(5,"allseeingcubemobvn");

        ForestBiome.caveCritters
                .add(90,"gemstonecavelingvn");

        ForestBiome.deepCaveCritters
                .add(90,"deepgemstonecavelingvn");

        Biome.defaultCaveMobs
                .add(250,"gustmobvn");

        Biome.defaultSurfaceCritters
                .add(30,"foxmobvn");

        SnowBiome.caveMobs
                .add(15,"snowynightmaremobvn")
                .add(20, "gustmobvn")
                .add(10,"icecubemobvn");
                //.add(5,"allseeingcubemobvn");

        DesertBiome.caveMobs
                .add(20, "gustmobvn");
        //.add(5,"allseeingcubemobvn");

        SnowBiome.surfaceMobs
                .add(10,"icecubemobvn");

        SnowBiome.surfaceCritters
                .add(8,"luckychickenmobvn");

        SnowBiome.caveCritters
                .add(5,"luckychickenmobvn");

        DesertBiome.surfaceMobs
                .add(10, "pyramidmobvn");

    }

}
