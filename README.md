# Vulpes Nova

It's the ~~old~~ NEW code for ~~my~~ @KindEnder04 mod Vulpes Nova, for Necesse. Look forwards to it!



### **Mod Update: Version 2.02.1**  

#### **Fixes**  
  - Standardized the names of weapon projectile names. 

#### **Changes**  
- **Recipes from JSON**:  
	- Recipes are now loaded from VNRecipeRegistyu. A JSON file within the .jar, located at resources/data/recipes.json now contains a more human readable list of recipes and facilitates easier addition of new items to the recipe registry.
	- com.google.json is now bundled into the mod .jar as a dependency

### **Mod Update: Version 2.02.0**  

#### **Fixes**  
- **Fixed Crash**: Resolved an issue causing the game to crash when loading into the **Flatlands biome**.  
- **Texture Fixes**:  
  - Restored **missing** and **broken textures**.  
  - Standardized texture names for broken assets (other textures will be standardized in a future update).  

#### **Changes**  
- **Updated Mod Info**:  
  - Adjusted mod info file to reflect the latest version.  
  - Incremented **minor version**
- **Codebase Update**:  
  - Replaced deprecated `ToolItemEvent` class instances with the **newer** `ToolItemMobAbilityEvent` class.  

#### **Known Issues**  
- **Queen Spider Staff**: The textures for this item are still missing and will be addressed in a future patch.  
