package net.smileycorp.bloodsmeltery.common;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BloodSmelteryConfig {
	
	public static Configuration config;
	
	//fluid will
	public static boolean enableFluidWill = true;
	public static boolean unifiedWill = false;
	public static int willFluidAmount = 100;
	public static boolean meltCrystals = true;
	public static int crystalMeltMultiplier = 50;
	public static boolean castWill = true;
	public static boolean castFillTartaricGems = true;
	public static boolean castSoulStone = true;
	public static int soulStoneCost = 50;
	public static boolean castDemonAlloy = true;
	public static int demonAlloyCost = 250;
	public static List<Entry<ResourceLocation, Integer>> demonAlloyCastIngredient = new ArrayList<Entry<ResourceLocation, Integer>>();
	
	//life essence creation
	public static boolean createLifeEssence = true;
	public static int[] lifeEssenceRatio = {5, 1};
	public static int lifeEssenceAmount = 1;
	
	//blood-infused stone creation
	public static boolean createBloodStone = true;
	public static int[] bloodStoneRatio = {3, 1};
	public static int bloodStoneAmount = 1;
	public static boolean meltBlankSlates = true;
	public static boolean meltAllSlates = true;
	public static boolean meltBlankRunes = true;
	public static boolean meltFunctionalRunes = true;
	public static boolean meltRitualStones = true;
	public static boolean meltSigils = true;
	
	//blood-infused stone casting
	public static boolean castBlankSlates = true;
	public static boolean castSlatesWithPlateCast = true;
	public static boolean castBlankRunes = true;
	public static boolean makeCastWithSlates = true;
	public static boolean makeCastWithSigils = true;
	
	//Blood Arsenal
	public static boolean bloodGlassFluid = true;
	public static int[] bloodGlassRatio = {1, 4};
	public static int bloodGlassAmount = 4;
	public static boolean castingBloodGlass = true;
	public static int castingBloodGlassAmount = 1000;
	public static boolean castingBloodGlassPanes = true;
	public static int castingBloodGlassPanesAmount = 375;
	public static boolean meltingBloodGlass = true;
	public static boolean meltingBloodPanes = true;
	
	//mod integration
	public static boolean showJeiDescriptions = true;
	public static boolean cryotheumCraftWill = true;
	public static boolean fluidTransposerWill = true;
	public static int fluidTransposerWillAmount = 1000;
	public static int fluidTransposerWillEnergy = 400;
	public static boolean magmaCrucibleCrystals = true;
	public static int magmaCrucibleCrystalEnergy = 20000;
	public static boolean enderIOAlloyBloodIron = true;
	public static int[] enderIOBloodIronRatio = {2125, 800, 36, 125, 4, 250};
	public static int enderIOBloodIronAmount = 36;
	public static boolean mekAlloyBloodIron = true;
	public static int[] mekBloodIronRatio = {4250, 1600, 72, 250, 8, 125, 50};
	public static int mekBloodIronAmount = 72;
	
	//progression skipping
	public static boolean castUpgradeOrbs = false;
	public static boolean castAllSlates = false;
	public static boolean castAltarItems = false;
	
	public static void syncConfig(){
		try{
			config.load();
			
			//fluid will
			enableFluidWill = config.get("fluid will", "enableFluidWill",
					true, "Should demonic will fluids be added to the game? (Default is true)").getBoolean();
			unifiedWill = config.get("fluid will", "unifiedWill",
					false, "Should all demonic will fluids be replaced with a single unified fluid? (Default is false)").getBoolean();
			willFluidAmount = config.get("fluid will", "willFluidAmount",
					100, "How much mb per will quantity does fluid will have? (Default is 100)").getInt();
			meltCrystals = config.get("fluid will", "meltCrystals",
					true, "Can demon will crystals be melted to make demonic will fluid? (Default is true)").getBoolean();
			crystalMeltMultiplier = config.get("fluid will", "crystalMeltMultiplier",
					50, "What multiplier to willFuidAmount do crystals produce when melted? (Default is 50)").getInt();
			castWill = config.get("fluid will", "castWill",
					true, "Should fluid demonic will be able to be cast into it's item variant? (Default is true)").getBoolean();
			castFillTartaricGems = config.get("fluid will", "castFillTartaricGems",
					true, "Should fluid demonic will be able to be cast into tartaric gems and similar items to fill them? (Default is true)").getBoolean();
			castSoulStone = config.get("fluid will", "castSoulStone",
					true, "Should you be able to cast demonic will fluids on stone to make the decoration blocks? (Default is true)").getBoolean();
			soulStoneCost = config.get("fluid will", "soulStoneCost",
					50, "How much demonic will is used to cast decorational blocks? (Default is 50)").getInt();
			castDemonAlloy = config.get("fluid will", "castDemonAlloy",
					true, "Should you be able to cast demon alloy blocks? (Default is true)").getBoolean();
			demonAlloyCost = config.get("fluid will", "demonAlloyCost",
					250, "How much demonic will is used to cast demonAlloyBlocks? (Default is 250)").getInt();
			String[] demonAlloyCastIngredientString = config.get("fluid will", "demonAlloyCastIngredients",
					new String[] {"minecraft:quartz_block"}, "Which items/blocks should be able to be consumed to make demon alloy? (Default is minecraft:quartz_block:0)").getStringList();
			for (String str : demonAlloyCastIngredientString) {
				ResourceLocation loc = demonAlloyCastIngredientString.length > 1 ? 
						new ResourceLocation(demonAlloyCastIngredientString[0], demonAlloyCastIngredientString[1]) : new ResourceLocation(demonAlloyCastIngredientString[0]);
				int meta = demonAlloyCastIngredientString.length > 2 ? (Integer.valueOf(demonAlloyCastIngredientString[2]) != null ? Integer.valueOf(demonAlloyCastIngredientString[3]) : 0) : 0;
				demonAlloyCastIngredient.add(new SimpleEntry<ResourceLocation, Integer>(loc, meta));
			}
			
			//life essence creation
			createLifeEssence = config.get("life essence creation", "createLifeEssence",
					true, "Should you be able to alloy fluid demonic will and blood into life essence? (Default is true)").getBoolean();
			Property lifeEssenceRatioProp = config.get("life essence creation", "lifeEssenceRatio",
					new int[]{5, 1}, "Ratio of fluid demonic will to blood in the creation of life essence. (Default is 5, 1)");
			lifeEssenceRatio = lifeEssenceRatioProp.getIntList().length>1 ? lifeEssenceRatioProp.getIntList() : lifeEssenceRatio;
			lifeEssenceAmount = config.get("life essence creation", "lifeEssenceAmount",
					1, "How much mb of life essence made per the minimum ratio of demonic will and blood? (Default is 1)").getInt();
			
			//blood-infused stone creation
			createBloodStone = config.get("blood-infused stone creation", "createBloodStone",
					true, "Should you be able to alloy life essence and seared stone into blood-infused stone? (Default is true)").getBoolean();
			Property bloodStoneRatioProp = config.get("blood-infused stone creation", "bloodStoneRatio",
					new int[]{3, 1}, "Ratio of life essence to seared stone in the creation of blood infused stone. (Default is 3, 1)");
			bloodStoneRatio = bloodStoneRatioProp.getIntList().length>1 ? bloodStoneRatioProp.getIntList() : bloodStoneRatio;
			bloodStoneAmount = config.get("blood-infused stone creation", "bloodStoneAmount",
					1, "How much mb of blood-infused stone made per the minimum ratio of life essence and seared stone? (Default is 1)").getInt();
			meltBlankSlates = config.get("blood-infused stone creation", "meltBlankSlates",
					true, "Should you be able to melt blank slates into blood-infused stone? (Default is true)").getBoolean();
			meltAllSlates = config.get("blood-infused stone creation", "meltAllSlates",
					true, "Should you be able to melt all slates into blood-infused stone? (Default is true)").getBoolean();
			meltBlankRunes = config.get("blood-infused stone creation", "meltBlankRune",
					true, "Should you be able to melt blank runes into blood-infused stone? (Default is true)").getBoolean();
			meltFunctionalRunes = config.get("blood-infused stone creation", "meltFunctionalRunes",
					true, "Should you be able to melt functional runes into blood-infused stone? (Default is true)").getBoolean();
			meltRitualStones = config.get("blood-infused stone creation", "meltRitualStones",
					true, "Should you be able to melt ritualStones into blood-infused stone? (Default is true)").getBoolean();
			meltSigils = config.get("blood-infused stone creation", "meltSigils",
					true, "Should you be able to melt sigils into blood-infused stone? (Default is true)").getBoolean();
			
			//blood-infused stone casting
			castBlankSlates = config.get("blood-infused stone casting", "castBlankSlates",
					true, "Should you be able to cast blood-infused stone into blank slates? (Default is true)").getBoolean();
			castSlatesWithPlateCast = config.get("blood-infused stone casting", "castSlatesWithPlateCast",
					true, "Should you be able to cast blood-infused stone into blank slates? (Default is true)").getBoolean();
			castBlankRunes = config.get("blood-infused stone casting", "castBlankRunes",
					true, "Should you be able to cast blood-infused stone into blank runes? (Default is true)").getBoolean();
			makeCastWithSlates = config.get("blood-infused stone casting", "makeCastWithSlates",
					true, "Should you be able to use slates to make plate casts? (Default is true)").getBoolean();
			makeCastWithSigils = config.get("blood-infused stone casting", "makeCastWithSigils",
					true, "Should you be able to use sigils to make plate casts? (Default is true)").getBoolean();
			
			//blood arsenal
			bloodGlassFluid = config.get("blood arsenal", "bloodGlassFluid",
					true, "Should you be able to alloy life essence and molten glass into molten blood glass? (Default is true)").getBoolean();
			Property bloodGlassRatioProp = config.get("blood arsenal", "bloodGlassRatio",
					new int[]{1, 8}, "Ratio of life essence to molten glass in the creation of blood stained glass. (Default is 1, 8)");
			bloodGlassRatio = bloodGlassRatioProp.getIntList().length>1 ? bloodGlassRatioProp.getIntList() : bloodGlassRatio;
			bloodGlassAmount = config.get("blood arsenal", "bloodGlassAmount",
					8, "How much mb of life essence made per the minimum ratio of life essence and molten glass? (Default is 8)").getInt();
			castingBloodGlass = config.get("blood arsenal", "castingBloodGlass",
					true, "Should you be cast molten blood infused glass to blood stained glass? (Default is true)").getBoolean();
			castingBloodGlassAmount = config.get("blood arsenal", "castingBloodGlassAmount ",
					1000, "How many blood stained glass does blood infused glass equal? (Default is 1000)").getInt();
			castingBloodGlassPanes = config.get("blood arsenal", "castingBloodGlassPanes",
					true, "Should you be cast molten blood infused glass to blood stained glass panes? (Default is true)").getBoolean();
			castingBloodGlassPanesAmount = config.get("blood arsenal", "castingBloodGlassPanesAmount ",
					375, "How many blood stained glass panes does blood infused glass equal? (Default is 375)").getInt();
			meltingBloodGlass = config.get("blood arsenal", "castingBloodGlass",
					true, "Should you be melt blood infused glass to blood infused glass? (Default is true)").getBoolean();
			meltingBloodPanes = config.get("blood arsenal", "castingBloodGlass",
					true, "Should you be melt blood infused glass panes to blood infused glass? (Default is true)").getBoolean();
			
			//mod integration
			showJeiDescriptions = config.get("mod integration", "showJeiDescriptions",
					true, "Should the additional information be showed over recipes in JEI? (Default is true)").getBoolean();
			
			cryotheumCraftWill = config.get("mod integration", "cryotheumCraftWill",
					true, "Should cryotheum dust be craftable with soul buckets to make demonic will? (Default is true)").getBoolean();
			fluidTransposerWill = config.get("mod integration", "fluidTransposerWill",
					true, "Should cryotheum dust be usable in the fluid transposer to make demonic will? (Default is true)").getBoolean();
			fluidTransposerWillAmount = config.get("mod integration", "fluidTransposerWillAmount",
					1000, "How many mb of will should the fluid transposer move by default? (Default is 1000)").getInt();
			fluidTransposerWillEnergy = config.get("mod integration", "fluidTransposerWillEnergy",
					400, "How much rf does creating will in the fluid transposer cost? (Default is 400)").getInt();
			magmaCrucibleCrystals = config.get("mod integration", "magmaCrucibleCrystals",
					true, "Can the magma crucible be used to melt demon will crystals)? (Default is true)").getBoolean();
			magmaCrucibleCrystalEnergy = config.get("mod integration", "magmaCrucibleCrystalEnergy",
					20000, "How much rf does metling demonic will crystals cost? (Default is 20000)").getInt();
			
			enderIOAlloyBloodIron = config.get("mod integration", "enderIOAlloyBloodIron",
					true, "Should you be able to alloy life essence, fluid demonic will, iron, glowstone, gold and rocket fuel into blood infused iron ? (Default is true)").getBoolean();
			Property enderIOBloodIronRatioProp = config.get("mod integration", "enderIOBloodIronRatio",
					new int[]{2125, 800, 36, 125, 4, 250}, "Ratio of ingredients: life essence, fluid demonic will, molten iron, glowstone, gold and rocket fuel, used to make blood infused iron (Default is 2125, 800, 36, 125, 4, 250)");
			enderIOBloodIronRatio = enderIOBloodIronRatioProp.getIntList().length>5 ? enderIOBloodIronRatioProp.getIntList() : enderIOBloodIronRatio;
			enderIOBloodIronAmount = config.get("mod integration", "enderIOBloodIronAmount",
					36, "How much mb of blood infused iron made per the enderIOBloodIronRatio? (Default is 36)").getInt();
			
			mekAlloyBloodIron = config.get("mod integration", "mekAlloyBloodIron",
					true, "Should you be able to alloy life essence, fluid demonic will, iron, glowstone, gold, redstone and sulfur dioxide into blood infused iron ? (Default is true)").getBoolean();
			Property mekBloodIronRatioProp = config.get("mod integration", "mekBloodIronRatio",
					new int[]{4250, 1600, 72, 250, 8, 125, 50}, "Ratio of ingredients: life essence, fluid demonic will, molten iron, glowstone, gold, redstone and sulfur dioxide, used to make blood infused iron  (Default is 4250, 1600, 72, 250, 8, 125, 50)");
			mekBloodIronRatio = mekBloodIronRatioProp.getIntList().length>6 ? mekBloodIronRatioProp.getIntList() : mekBloodIronRatio;
			mekBloodIronAmount = config.get("mod integration", "mekBloodIronAmount",
					72, "How much mb of blood infused iron made per the enderIOBloodIronRatio? (Default is 72)").getInt();
			
			//progression skipping
			castUpgradeOrbs = config.get("progression skipping", "castUpgradeOrbs",
					false, "Should you be able to pour life essence onto blood orbs to upgrade them? (Default is false)").getBoolean();
			castAllSlates = config.get("progression-skipping", "castAllSlates",
					false, "Should you be able to pour more life essence onto slates to upgrade them? (Default is false)").getBoolean();
			castAltarItems = config.get("progression-skipping", "castAltarItems",
					false, "Should any recipe made in the blood altar be able to be made in the casting table? (Default is false)").getBoolean();
		} catch (Exception e) {
		} finally {
    	if (config.hasChanged()) config.save();
		}
	}
}
