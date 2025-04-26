package vulpesnova.helpers;

import java.util.ArrayList;

import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.inventory.recipe.Tech;
import vulpesnova.VulpesNova;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.recipe.Ingredient;

public class RecipeHelper {
	public static Tech FALLBACK_TECH = RecipeTechRegistry.WORKSTATION;	
    public static class RecipeData {
        String result;
        int quantity = 1;
        boolean hidden = false;
        Tech tech;
        ArrayList<Ingredient> ingredients;
        String showAfter;
        String showBefore;
        ItemCategory recipeCategoryOverride;
        
        public RecipeData() {
        	this.ingredients = new ArrayList<>();
        }
        
        public RecipeData result(String resultItemName) {
        	this.result = resultItemName;
        	return this;
        }
        
        public RecipeData hidden(boolean isHidden) {
        	this.hidden = isHidden;
        	return this;
        }
        
        public RecipeData quantity(int quantityProduced) {
        	this.quantity = quantityProduced;
        	return this;
        }
        
        public RecipeData tech(String techContainerID) {
        	if(RecipeTechRegistry.validStringID(techContainerID)) {
        		this.tech = RecipeTechRegistry.getTech(techContainerID);
        	}
        	else {
        		this.tech = FALLBACK_TECH;
        	}
        	return this;
        }
        
        public RecipeData tech(Tech container) {
        	this.tech = container;
        	return this;
        }
        
        public RecipeData ingredient(String ingredientStringID, int itemAmount, boolean requiredToShow) {
        	this.ingredients.add(new Ingredient(ingredientStringID, itemAmount, requiredToShow));
        	return this;
        }
        
        public RecipeData ingredient(String ingredientStringID, int itemAmount) {
        	this.ingredients.add(new Ingredient(ingredientStringID, itemAmount, false));
        	return this;
        }
        
        public RecipeData ingredient(Ingredient newIngredient) {
        	this.ingredients.add(newIngredient);
        	return this;
        }
        
        public RecipeData showAfter(String itemName) {
        	this.showAfter = itemName;
        	return this;
        }
        
        public RecipeData showBefore(String itemName) {
        	this.showBefore = itemName;
        	return this;
        }
        
        public Recipe toRecipe() {
        	if(tech!=null && result != null) {
        		Recipe retVal = new Recipe(result, quantity, tech, this.ingredients.toArray(new Ingredient[] {}),hidden);
        		if(showAfter!=null) retVal.showAfter(showAfter);
        		else if (showBefore!=null) retVal.showBefore(showBefore);
        		return retVal;
        	}
        	return null;
        }
    }

    public static class IngredientData {
        String item;
        int amount;
    }
    
    static boolean initialized = false;
    public static void initialize() {
		if(initialized) return;
		
		initialized=true;
		
		Recipes.registerModRecipe(
				new RecipeData().result("roastedvulpinevn")
				.tech(RecipeTechRegistry.ROASTING_STATION)
				.ingredient("rawvulpinevn", 1)
				.showAfter("roastedpork")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novashardvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("novafragmentvn", 16)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novaclustervn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("novashardvn", 50)
				.showAfter("novashardvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("awakeninatorvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("emerald", 1)
				.ingredient("novaclustervn", 1)
				.showAfter("novashardvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("minershavenmapvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("mapfragment", 24)
				.ingredient("demonicbar", 4)
				.ingredient("novashardvn", 8)
				.showAfter("piratemap")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("foxtokenvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("goldbar", 12)
				.ingredient("demonicbar", 3)
				.ingredient("ivybar", 3)
				.ingredient("novaclustervn", 1)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("berserkerorbvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("frenzyorb", 1)
				.ingredient("firemone", 30)
				.ingredient("ruby", 6)
				.ingredient("novaclustervn", 1)
				.ingredient("awakeninatorvn", 1)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cosmictalismanvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("nightmaretalisman", 1)
				.ingredient("cloudsvn", 20)
				.ingredient("novaclustervn", 2)
				.showAfter("nightmaretalisman")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubaltbarvn")
				.tech(RecipeTechRegistry.FORGE)
				.ingredient("cubaltoreitemvn", 3)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("bigbrainonastickvn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("brainonastick", 1)
				.ingredient("anyrawmeat", 60)
				.ingredient("novashardvn", 12)
				.showAfter("spiderstaff")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("queenspiderstaffvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("spiderstaff", 1)
				.ingredient("cavespidergland", 12)
				.ingredient("wool", 10)
				.ingredient("novashardvn", 4)
				.showAfter("spiderstaff")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novicetomevn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("anylog", 16)
				.ingredient("anysapling", 6)
				.ingredient("novafragmentvn", 24)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("intermediatetomevn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("novicetomevn", 1)
				.ingredient("demonicbar", 6)
				.ingredient("novashardvn", 8)
				.showAfter("novicetomevn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("experttomevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("intermediatetomevn", 1)
				.ingredient("ectoplasm", 12)
				.ingredient("tungstenbar", 6)
				.ingredient("novashardvn", 26)
				.showAfter("thunderingrodvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("mastertomevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("experttomevn", 1)
				.ingredient("ectoplasm", 16)
				.ingredient("amethyst", 8)
				.ingredient("novashardvn", 26)
				.showAfter("experttomevn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("amethystamuletvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("amethyst", 1)
				.ingredient("ironbar", 8)
				.ingredient("novashardvn", 26)
				.showAfter("experttomevn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("magicfordummiesvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("novicetomevn", 1)
				.ingredient("intermediatetomevn", 1)
				.ingredient("experttomevn", 1)
				.ingredient("mastertomevn", 1)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("crimsontempestvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novakatanavn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("reinforcedkatana", 1)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 4)
				.showAfter("themountainvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("nightbladevn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("demonicbar", 15)
				.ingredient("novashardvn", 8)
				.showAfter("demonicsword")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("celestialembervn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("tungstenbar", 15)
				.ingredient("ectoplasm", 20)
				.ingredient("novaclustervn", 2)
				.showAfter("iciclestaff")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("saplingswordvn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("woodsword", 1)
				.ingredient("anysapling", 20)
				.ingredient("novashardvn", 3)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("holytomevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("ectoplasm", 20)
				.ingredient("goldbar", 3)
				.ingredient("book", 1)
				.ingredient("novashardvn", 12)
				.showAfter("shadowbolt")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("holytomevn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("unholytomevn", 1)
				.ingredient("novashardvn", 4)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("unholytomevn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("holytomevn", 1)
				.ingredient("novashardvn", 4)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novabulletvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("voidbullet", 100)
				.ingredient("novashardvn", 1)
				.showAfter("voidbullet")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("windbulletvn")
				.tech(RecipeTechRegistry.NONE)
				.ingredient("cloudsvn", 5)
				.ingredient("novafragmentvn", 3)
				.showAfter("simplebullet")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("goldarrowvn")
				.tech(RecipeTechRegistry.NONE)
				.ingredient("goldbar", 1)
				.showAfter("ironarrow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("windarrowvn")
				.tech(RecipeTechRegistry.NONE)
				.ingredient("cloudsvn", 5)
				.ingredient("novafragmentvn", 3)
				.showAfter("ironarrow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novaarrowvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("goldarrowvn", 50)
				.ingredient("novashardvn", 1)
				.showAfter("poisonarrow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("lmgvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("machinegun", 1)
				.ingredient("tungstenbar", 16)
				.ingredient("novashardvn", 12)
				.showAfter("tungstenbow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("doomedbowvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("bone", 20)
				.ingredient("ectoplasm", 15)
				.ingredient("tungstenbar", 12)
				.ingredient("novashardvn", 12)
				.showAfter("tungstenbow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("repeatingcrossbowvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("tungstenbar", 14)
				.ingredient("novashardvn", 10)
				.showAfter("tungstenbow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("ironsoulglaivevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("ironbar", 12)
				.ingredient("tungstenbar", 12)
				.ingredient("ectoplasm", 8)
				.ingredient("novashardvn", 10)
				.showAfter("tungstenspear")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("codblastervn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("handgun", 1)
				.ingredient("cod", 8)
				.ingredient("novashardvn", 6)
				.showAfter("ivybow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("glassshardswordvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("glass", 50)
				.ingredient("ironbar", 15)
				.ingredient("novashardvn", 6)
				.showAfter("ivysword")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("necromancersrequiemvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("bigbrainonastickvn", 1)
				.ingredient("skeletonstaff", 1)
				.ingredient("bone", 30)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("cryostaff")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("acornlobbervn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("anylog", 30)
				.ingredient("anysapling", 16)
				.ingredient("novafragmentvn", 16)
				.showAfter("craftingguide")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("woodenbulletvn")
				.tech(RecipeTechRegistry.NONE)
				.ingredient("novafragmentvn", 3)
				.ingredient("anylog", 5)
				.ingredient("anysapling", 3)
				.showAfter("novaarrowvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("galevn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("acornlobbervn", 1)
				.ingredient("cloudsvn", 24)
				.ingredient("novashardvn", 6)
				.showAfter("acornlobbervn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("theblowingwindvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("demonicbow", 1)
				.ingredient("cloudsvn", 24)
				.ingredient("novashardvn", 10)
				.showAfter("demonicbow")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novaheartvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("lifeelixir", 1)
				.ingredient("lifequartz", 10)
				.ingredient("novaclustervn", 1)
				.showAfter("lifeelixir")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("bastionboxvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("cubaltbarvn", 6)
				.ingredient("shapeshardsvn", 20)
				.ingredient("novashardvn", 16)
				.showAfter("mysteriousportal")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("heavierhammervn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("heavyhammer", 1)
				.ingredient("ironbar", 16)
				.ingredient("novashardvn", 16)
				.showAfter("ironsword")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("heaviesthammervn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("heavierhammervn", 1)
				.ingredient("tungstenbar", 24)
				.ingredient("novashardvn", 32)
				.showAfter("tungstensword")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("thunderingrodvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("ironbar", 12)
				.ingredient("cloudsvn", 24)
				.ingredient("novashardvn", 16)
				.showAfter("woodstaff")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("eyeofthestormvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("thunderingrodvn", 1)
				.ingredient("tungstenbar", 12)
				.ingredient("cloudsvn", 32)
				.ingredient("novashardvn", 32)
				.showAfter("thunderingrodvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("crimsontempestvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("eyeofthestormvn", 1)
				.ingredient("cloudsvn", 128)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("thunderingrodvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("monsterpheromonesvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("stinkflask", 1)
				.ingredient("cloudsvn", 50)
				.ingredient("ectoplasm", 25)
				.ingredient("novaclustervn", 1)
				.showAfter("novaheartvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("themountainvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("heaviesthammervn", 1)
				.ingredient("anystone", 5000)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 1)
				.showAfter("crimsontempestvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("stormboltervn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("galevn", 1)
				.ingredient("thunderingrodvn", 1)
				.ingredient("cloudsvn", 16)
				.ingredient("novashardvn", 24)
				.showAfter("codblastervn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("stormbringervn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("hurricanevn", 1)
				.ingredient("thunderingrodvn", 1)
				.ingredient("cloudsvn", 16)
				.ingredient("novashardvn", 24)
				.showAfter("codblastervn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("arainydayvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("stormbringervn", 1)
				.ingredient("lmgvn", 1)
				.ingredient("cloudsvn", 32)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 1)
				.showAfter("novakatanavn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("tableofawakeningvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("demonicbar", 12)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novashardvn", 12)
				.showAfter("demonicworkstation")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("thecollectorsmagnetvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("itemattractor", 1)
				.ingredient("novaclustervn", 1)
				.showAfter("toolbox")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("windmedallionvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("cloudsvn", 20)
				.ingredient("goldbar", 4)
				.ingredient("novashardvn", 6)
				.showAfter("dreamcatcher")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("treemedallionvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("anysapling", 30)
				.ingredient("goldbar", 4)
				.ingredient("novashardvn", 6)
				.showAfter("dreamcatcher")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("meatmedallionvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("anyrawmeat", 20)
				.ingredient("goldbar", 4)
				.ingredient("novashardvn", 6)
				.showAfter("dreamcatcher")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("stonemedallionvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("anystone", 100)
				.ingredient("goldbar", 4)
				.ingredient("novashardvn", 6)
				.showAfter("dreamcatcher")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("medallionboardvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("treemedallionvn", 1)
				.ingredient("windmedallionvn", 1)
				.ingredient("meatmedallionvn", 1)
				.ingredient("stonemedallionvn", 1)
				.ingredient("novashardvn", 16)
				.showAfter("dreamcatcher")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("fortunecollectionvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("medallionboardvn", 1)
				.ingredient("clovertokenvn", 1)
				.ingredient("tungstenbar", 4)
				.ingredient("novashardvn", 24)
				.showAfter("medallionboardvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("demonicgauntletvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("claygauntlet", 1)
				.ingredient("demonicbar", 8)
				.ingredient("novashardvn", 12)
				.showAfter("claygauntlet")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("tungstengauntletvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("demonicgauntletvn", 1)
				.ingredient("tungstenbar", 8)
				.ingredient("novashardvn", 24)
				.showAfter("demonicgauntletvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("novagauntletvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("tungstengauntletvn", 1)
				.ingredient("cubaltbarvn", 8)
				.ingredient("shapeshardsvn", 16)
				.ingredient("novaclustervn", 1)
				.showAfter("tungstengauntletvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("calmingminersexoskeletonvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("toolbox", 1)
				.ingredient("calmingminersbouquet", 1)
				.ingredient("diggingclaw", 1)
				.ingredient("minersprosthetic", 1)
				.ingredient("ironbar", 40)
				.ingredient("tungstenbar", 20)
				.ingredient("spelunkerpotion", 10)
				.ingredient("treasurepotion", 10)
				.ingredient("greaterminingpotion", 5)
				.ingredient("awakeninatorvn", 1)
				.showAfter("frozenheavenvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("gladiatorsembracevn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("manica", 1)
				.ingredient("challengerspauldron", 1)
				.ingredient("goldbar", 8)
				.ingredient("novashardvn", 8)
				.showAfter("manica")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("galacticgraspvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("gladiatorsembracevn", 1)
				.ingredient("novagauntletvn", 1)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 1)
				.showAfter("manica")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("frostbittenpawvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("polarclaw", 1)
				.ingredient("frozenwave", 1)
				.ingredient("frostshard", 12)
				.ingredient("novashardvn", 8)
				.showAfter("manica")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("frozenpendantvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("lifependant", 1)
				.ingredient("frozensoul", 1)
				.ingredient("lifeelixir", 2)
				.ingredient("novashardvn", 12)
				.showAfter("manica")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("glacialfrostvn")
				.tech(RecipeTechRegistry.TUNGSTEN_WORKSTATION)
				.ingredient("frozenpendantvn", 1)
				.ingredient("frostbittenpawvn", 1)
				.ingredient("glacialbar", 6)
				.ingredient("novashardvn", 16)
				.showAfter("manica")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("frozenheavenvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("glacialfrostvn", 1)
				.ingredient("halovn", 1)
				.ingredient("cloudsvn", 24)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 1)
				.showAfter("manica")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("hookgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("armorgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("summongemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("dashgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("speedgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("critgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("damagegemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("bloodgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("manaregengemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("managemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("resilienceregengemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("resiliencegemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("regengemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("healthgemvn")
				.tech(RecipeTechRegistry.ALCHEMY)
				.ingredient("gemdustvn", 70)
				.ingredient("novashardvn", 6)
				.showAfter("regenpendant")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("yourgemcollectionvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("healthgemvn", 1)
				.ingredient("regengemvn", 1)
				.ingredient("resiliencegemvn", 1)
				.ingredient("resilienceregengemvn", 1)
				.ingredient("managemvn", 1)
				.ingredient("manaregengemvn", 1)
				.ingredient("bloodgemvn", 1)
				.ingredient("damagegemvn", 1)
				.ingredient("critgemvn", 1)
				.ingredient("speedgemvn", 1)
				.ingredient("dashgemvn", 1)
				.ingredient("summongemvn", 1)
				.ingredient("armorgemvn", 1)
				.ingredient("hookgemvn", 1)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novashardvn", 20)
				.showAfter("ancientjunglebootsvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("trueexplorersatchelvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("explorersatchel", 1)
				.ingredient("explorercloak", 1)
				.ingredient("spikedbatboots", 1)
				.ingredient("ancientrelics", 1)
				.ingredient("nightmareheadvn", 1)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("ancientjunglebootsvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("ruinedgolemsealvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("anystone", 100)
				.ingredient("woodshield", 1)
				.ingredient("novashardvn", 8)
				.showAfter("woodshield")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("speedstersealvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("speedpotion", 8)
				.ingredient("ironbar", 4)
				.ingredient("novashardvn", 12)
				.showAfter("woodshield")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("flowingenergysealvn")
				.tech(RecipeTechRegistry.DEMONIC_WORKSTATION)
				.ingredient("demonicbar", 8)
				.ingredient("manapotion", 4)
				.ingredient("manaregenpotion", 4)
				.ingredient("novashardvn", 12)
				.showAfter("woodshield")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("archbishopcowlvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("demonwarriorsealvn", 1)
				.ingredient("holypaladinsealvn", 1)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("ancientjunglebootsvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("luckychickenmaskvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("novashardvn", 24)
				.showAfter("ancientjunglebootsvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("luckychickencostumeshirtvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("novashardvn", 24)
				.showAfter("ancientjunglebootsvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("luckychickencostumebootsvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("novashardvn", 24)
				.showAfter("ancientjunglebootsvn")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubaltbootsvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("shapeshardsvn", 20)
				.ingredient("cubaltbarvn", 8)
				.ingredient("novashardvn", 12)
				.showAfter("demonicboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubaltchestplatevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("shapeshardsvn", 40)
				.ingredient("cubaltbarvn", 16)
				.ingredient("novashardvn", 16)
				.showAfter("demonicboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubalthelmetvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("shapeshardsvn", 30)
				.ingredient("cubaltbarvn", 10)
				.ingredient("novashardvn", 12)
				.showAfter("demonicboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubaltshovelvn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("shapeshardsvn", 20)
				.ingredient("cubaltbarvn", 10)
				.ingredient("novashardvn", 8)
				.showAfter("demonicboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubaltaxevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("shapeshardsvn", 20)
				.ingredient("cubaltbarvn", 10)
				.ingredient("novashardvn", 8)
				.showAfter("demonicboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("cubaltpickaxevn")
				.tech(RecipeTechRegistry.TUNGSTEN_ANVIL)
				.ingredient("shapeshardsvn", 20)
				.ingredient("cubaltbarvn", 10)
				.ingredient("novashardvn", 8)
				.showAfter("demonicboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("woodenbootsvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("clothboots", 1)
				.ingredient("anylog", 20)
				.ingredient("anysapling", 10)
				.ingredient("novashardvn", 8)
				.showAfter("clothboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("woodenchestplatevn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("clothrobe", 1)
				.ingredient("anylog", 40)
				.ingredient("anysapling", 15)
				.ingredient("novashardvn", 12)
				.showAfter("clothboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("woodenhelmetvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("clothhat", 1)
				.ingredient("anylog", 30)
				.ingredient("anysapling", 10)
				.ingredient("novashardvn", 6)
				.showAfter("clothboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("ancientjunglebootsvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("woodenbootsvn", 1)
				.ingredient("anysapling", 80)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("clothboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("ancientjunglechestplatevn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("woodenchestplatevn", 1)
				.ingredient("anysapling", 120)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 3)
				.showAfter("clothboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("ancientjunglehelmetvn")
				.tech(VulpesNova.TABLEOFAWAKENINGVN)
				.ingredient("woodenhelmetvn", 1)
				.ingredient("anysapling", 100)
				.ingredient("awakeninatorvn", 1)
				.ingredient("novaclustervn", 2)
				.showAfter("clothboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("windbootsvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("leatherboots", 1)
				.ingredient("cloudsvn", 24)
				.ingredient("novashardvn", 8)
				.showAfter("leatherboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("windchestplatevn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("leathershirt", 1)
				.ingredient("cloudsvn", 30)
				.ingredient("novashardvn", 12)
				.showAfter("leatherboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("windhatvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("leatherhood", 1)
				.ingredient("cloudsvn", 16)
				.ingredient("novashardvn", 6)
				.showAfter("leatherboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("meatbootsvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("spiderboots", 1)
				.ingredient("anyrawmeat", 24)
				.ingredient("novashardvn", 8)
				.showAfter("spiderboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("meatrobevn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("spiderchestplate", 1)
				.ingredient("anyrawmeat", 30)
				.ingredient("novashardvn", 12)
				.showAfter("spiderboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("meathatvn")
				.tech(RecipeTechRegistry.WORKSTATION)
				.ingredient("spiderhelmet", 1)
				.ingredient("anyrawmeat", 20)
				.ingredient("novashardvn", 6)
				.showAfter("spiderboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("stonebootsvn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("goldboots", 1)
				.ingredient("anystone", 100)
				.ingredient("novashardvn", 8)
				.showAfter("goldboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("stonechestplatevn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("goldchestplate", 1)
				.ingredient("anystone", 160)
				.ingredient("novashardvn", 12)
				.showAfter("goldboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("stonehelmetvn")
				.tech(RecipeTechRegistry.IRON_ANVIL)
				.ingredient("goldhelmet", 1)
				.ingredient("anystone", 60)
				.ingredient("novashardvn", 6)
				.showAfter("goldboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("chilledbloodplatebootsvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("frostboots", 1)
				.ingredient("bloodplateboots", 1)
				.ingredient("novashardvn", 8)
				.showAfter("bloodplateboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("chilledbloodplatechestplatevn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("frostchestplate", 1)
				.ingredient("bloodplatechestplate", 1)
				.ingredient("novashardvn", 12)
				.showAfter("bloodplateboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("chilledbloodplatehatvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("frosthat", 1)
				.ingredient("bloodplatecowl", 1)
				.ingredient("novashardvn", 6)
				.showAfter("bloodplateboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("chilledbloodplatehoodvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("frosthood", 1)
				.ingredient("bloodplatecowl", 1)
				.ingredient("novashardvn", 6)
				.showAfter("bloodplateboots")
				.toRecipe());

			Recipes.registerModRecipe(
				new RecipeData().result("chilledbloodplatehelmetvn")
				.tech(RecipeTechRegistry.DEMONIC_ANVIL)
				.ingredient("frosthelmet", 1)
				.ingredient("bloodplatecowl", 1)
				.ingredient("novashardvn", 6)
				.showAfter("bloodplateboots")
				.toRecipe());


	}
	
}
