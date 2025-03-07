# Vulpes Nova 2.04.0
## Target Necesse Version: 0.32.0

This repository contains the development resources and code for Vulpes Nova, a mod authored by @KingEnder04 for Necesse.
Credits: 	
- @KingEnder04		- Original project creator :fox:
- @Logar			- Sprites
- @Meffestoffel 	- RU locale
- @FerrenF 			- Compatibility fixes/Version Update


### 03-06-25 **Mod Update: Version 2.04.0**  

#### **Changes**  
- LMG has new sound FX
- Stormbringer/Stormbolter have new sound FX.
- Jade Waraxe has been reworked to be a slashing bleeding weapon.
- Crimson Tempest has been reworked to be a special projectile weapon.  

#### **Fixes**  
- Too many fixes to list. Many compatibility changes for Necesse version 0.32.0


### 02-20-25 **Mod Update: Version 2.02.5**  

#### **Fixes**  
  - Crash on settler use of the item ```Thundering Rod```
  - Crash on settler or player use of the item ```Crimson Tempest```

### 02-09-25 **Mod Update: Version 2.02.4**  

#### **Fixes**  
  - Missing recipes: novashardvn, novaclustervn, foxtokenvn, healthgemvn, regengemvn, resiliencegemvn, resilienceregengemvn, managemvn, manaregengemvn, bloodgemvn, damagegemvn

### 02-09-25 **Mod Update: Version 2.02.3**  

#### **Fixes**  
  - Code type safety cleanliness changes.
  
#### **Changes**  
- **RU localization is now supported. Provided by @Meffestoffel on discord. Thank you!**  


### **Mod Update: Version 2.02.2**  

Minor locale updates!

#### **Fixes**  
  - Fixed old version names in EN and IT locale files.
  - Fixed some missing buff information.


### **Mod Update: Version 2.02.1**  

#### **Fixes**  
  - Standardized the names of weapon projectile names. 

#### **Changes**  
- **Recipes from JSON**:  
	- Recipes are now loaded from VNRecipeRegistyu. A JSON file within the .jar, located at resources/data/recipes.json now contains a more human readable list of recipes and facilitates easier addition of new items to the recipe registry.
	- com.google.json is now bundled into the mod .jar as a dependency
	
#### **Known Issues**  
- woodenpistol has resources and projectile but is not implemented
- slimystickvn has resources but is not implemented
- cubewoodvndoor, cubestonevndoor are not registered as tiles

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
