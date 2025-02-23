package vulpesnova;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import necesse.engine.modLoader.LoadedMod;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.inventory.recipe.Tech;


// VNRecipeRegistry: Loads recipes defined by a JSON file. It's prettier than the alternative.
// We are using com.google.gson, which is defined as a dependency in the gradle build script.
// If you are using Eclipse, it must be imported as a user library after you download the appropriate jar.
// Alternatively, there should already be a .jar for this included in libs/

public class VNRecipeRegistry {
    
	// Ugh, manual enumeration - gross...
	private static Tech mapTech(String techName) {
	    switch (techName.toLowerCase()) {
	        case "advanced_workstation": return RecipeTechRegistry.ADVANCED_WORKSTATION;
	        case "all": return RecipeTechRegistry.ALL;
	        case "none": return RecipeTechRegistry.NONE;
	        case "workstation": return RecipeTechRegistry.WORKSTATION;
	        case "cooking_pot": return RecipeTechRegistry.COOKING_POT;
	        case "roasting_station": return RecipeTechRegistry.ROASTING_STATION;
	        case "forge": return RecipeTechRegistry.FORGE;
	        case "carpenter": return RecipeTechRegistry.CARPENTER;
	        case "landscaping": return RecipeTechRegistry.LANDSCAPING;
	        case "iron_anvil": return RecipeTechRegistry.IRON_ANVIL;
	        case "alchemy": return RecipeTechRegistry.ALCHEMY;
	        case "demonic_workstation": return RecipeTechRegistry.DEMONIC_WORKSTATION;
	        case "cooking_station": return RecipeTechRegistry.COOKING_STATION;
	        case "demonic_anvil": return RecipeTechRegistry.DEMONIC_ANVIL;
	        case "void_alchemy": return RecipeTechRegistry.VOID_ALCHEMY;
	        case "tungsten_workstation": return RecipeTechRegistry.TUNGSTEN_WORKSTATION;
	        case "tungsten_anvil": return RecipeTechRegistry.TUNGSTEN_ANVIL;
	        case "caveglow_alchemy": return RecipeTechRegistry.CAVEGLOW_ALCHEMY;
	        case "fallen_workstation": return RecipeTechRegistry.FALLEN_WORKSTATION;
	        case "fallen_anvil": return RecipeTechRegistry.FALLEN_ANVIL;
	        case "fallen_alchemy": return RecipeTechRegistry.FALLEN_ALCHEMY;
	        case "compost_bin": return RecipeTechRegistry.COMPOST_BIN;
	        case "grain_mill": return RecipeTechRegistry.GRAIN_MILL;
	        case "cheese_press": return RecipeTechRegistry.CHEESE_PRESS;
	        case "tableofawakeningvn": return VulpesNova.TABLEOFAWAKENINGVN;
	        default:
	            System.err.println("Invalid tech type: " + techName + ". Defaulting to NONE.");
	            return RecipeTechRegistry.NONE;
	    }
	}
	
    // Inner classes for JSON mapping
    private static class RecipeData {
        String result;
        int quantity;
        String tech;
        IngredientData[] ingredients;
        String showAfter;
    }

    private static class IngredientData {
        String item;
        int amount;
    }

    public static void registerRecipes(String dir) {
        List<RecipeData> recipes = loadRecipesFromJson(dir);

        if (recipes == null) {
            System.err.println("Failed to load recipes.");
            return;
        }

        for (RecipeData data : recipes) {

        	// Convert the string into a tech from the RecipeTechRegistry.
        	Tech tech;
        	try {
        	    tech = mapTech(data.tech);
        	} catch (NoSuchElementException e) {        		
        		// If it does not exist, let's give it a placeholder and complain about it.
        	    System.err.println("Invalid tech type: " + data.tech + ". Defaulting to NONE.");
        	    tech = RecipeTechRegistry.NONE; 
        	}
        	
        	// Build a list of Ingredient classes from the recipe's ingredient list.
            Ingredient[] ingredients = new Ingredient[data.ingredients.length];
            for (int i = 0; i < data.ingredients.length; i++) {
                ingredients[i] = new Ingredient(data.ingredients[i].item, data.ingredients[i].amount);
            }

            // Finally, register the recipe.
            Recipes.registerModRecipe(new Recipe(
                data.result, data.quantity, tech, ingredients
            ).showAfter(data.showAfter));
        }
    }

    private static List<RecipeData> loadRecipesFromJson(String filePath) {
        try {
        	
            // Get the currently running mod.
            LoadedMod runningMod = LoadedMod.getRunningMod();
            JarFile modJar = runningMod.jarFile;  // Get the mod's JAR file

            // Find the file inside the JAR
            JarEntry entry = modJar.getJarEntry(filePath);
            if (entry == null) {
                System.err.println("Resource not found in JAR: " + filePath);
                return null;
            }

            // Open an InputStream to read the file
            try (InputStream inputStream = modJar.getInputStream(entry);
                 InputStreamReader reader = new InputStreamReader(inputStream)) {
                
                // Deserialize JSON into RecipeData objects
                Type listType = new TypeToken<List<RecipeData>>() {}.getType();
                return new Gson().fromJson(reader, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }    
  
}