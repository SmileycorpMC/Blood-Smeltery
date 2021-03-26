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
	public static boolean castWill = true;
	public static boolean castFillTartaricGems = true;
	public static boolean castSoulStone = true;
	public static int soulStoneCost = 50;
	public static boolean castDemonAlloy = true;
	public static int demonAlloyCost = 250;
	public static List<Entry<ResourceLocation, Integer>> demonAlloyCastIngredient = new ArrayList<Entry<ResourceLocation, Integer>>();
	
	//life essence creation
	public static boolean createLifeEssence = true;
	public static int[] lifeEssenceRatio = {1, 1};
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
	
	//mod integration
	public static boolean showJeiDescriptions = true;
	public static boolean cryotheumCraftWill = true;
	public static boolean fluidTransposerWill = true;
	public static int fluidTransposerWillAmount = 1000;
	public static int fluidTransposerWillEnergy = 400;
	
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
					new int[]{1, 1}, "Ratio of fluid demonic will to blood in the creation of life essence. (Default is 1, 1)");
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
