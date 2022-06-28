package net.smileycorp.bloodsmeltery.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class BloodSmelteryConfig {

	public static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec config;

	//fluid will
	public static BooleanValue enableFluidWill;
	public static BooleanValue unifiedWill;
	public static ConfigValue<Integer> willFluidAmount;
	public static BooleanValue meltCrystals;
	public static ConfigValue<Integer> crystalMeltMultiplier;
	public static BooleanValue castWill;
	public static BooleanValue castFillTartaricGems;
	public static BooleanValue castSoulStone;
	public static ConfigValue<Integer> soulStoneCost;
	public static BooleanValue castDemonAlloy;
	public static ConfigValue<Integer> demonAlloyCost;

	//life essence creation
	public static BooleanValue createLifeEssence;
	public static ConfigValue<Integer>[] lifeEssenceRatio;
	public static ConfigValue<Integer> lifeEssenceAmount;

	//blood-infused stone creation
	public static BooleanValue createBloodStone;
	public static ConfigValue<Integer>[] bloodStoneRatio;
	public static ConfigValue<Integer> bloodStoneAmount;
	public static BooleanValue meltBlankSlates;
	public static BooleanValue meltAllSlates;
	public static BooleanValue meltBlankRunes;
	public static BooleanValue meltFunctionalRunes;
	public static BooleanValue meltRitualStones;
	public static BooleanValue meltSigils;

	//blood-infused stone casting
	public static BooleanValue castBlankSlates;
	public static BooleanValue castSlatesWithPlateCast;
	public static BooleanValue castBlankRunes;
	public static BooleanValue makeCastWithSlates;
	public static BooleanValue makeCastWithSigils;

	//mod IntValueegration
	public static BooleanValue showJeiDescriptions;
	public static BooleanValue fluidEncapsulatorWill;
	public static ConfigValue<Integer> fluidEncapsulatorWillAmount;
	public static ConfigValue<Integer> fluidEncapsulatorWillEnergy;
	public static BooleanValue magmaCrucibleCrystals;
	public static ConfigValue<Integer> magmaCrucibleCrystalEnergy;

	//progression skipping
	public static BooleanValue castUpgradeOrbs;
	public static BooleanValue castAllSlates;
	public static BooleanValue castAltarItems;

	static {

		//fluid will
		builder.push("fluid will");
		enableFluidWill = builder.comment("Should demonic will fluids be added to the game? (Default is true)")
				.define("enableFluidWill", true);
		unifiedWill = builder.comment("Should all demonic will fluids be replaced with a single unified fluid? (Default is false)")
				.define("unifiedWill", false);
		/*willFluidAmount = builder.comment("How much mb per will quantity does fluid will have? (Default is 100)")
				.define("willFluidAmount", 100);
		meltCrystals = builder.comment("Can demon will crystals be melted to make demonic will fluid? (Default is true)")
				.define("meltCrystals", true);
		crystalMeltMultiplier = builder.comment("What multiplier to willFuidAmount do crystals produce when melted? (Default is 50)")
				.define("crystalMeltMultiplier", 50);
		castWill = builder.comment("Should fluid demonic will be able to be cast IntValueo it's item variant? (Default is true)")
				.define("castWill", true);
		castFillTartaricGems = builder.comment("Should fluid demonic will be able to be cast IntValueo tartaric gems and similar items to fill them? (Default is true)")
				.define("castFillTartaricGems", true);
		castSoulStone = builder.comment("Should you be able to cast demonic will fluids on stone to make the decoration blocks? (Default is true)")
				.define("castSoulStone", true);
		soulStoneCost = builder.comment("How much demonic will is used to cast decorational blocks? (Default is 50)")
				.define("soulStoneCost", 50);
		castDemonAlloy = builder.comment("Should you be able to cast demon alloy blocks? (Default is true)")
				.define("castDemonAlloy", true);
		demonAlloyCost = builder.comment("How much demonic will is used to cast demonAlloyBlocks? (Default is 250)")
				.define("demonAlloyCost", 250);

		//life essence creation
		builder.push("life essence creation");
		createLifeEssence = builder.comment("Should you be able to alloy fluid demonic will and blood IntValueo life essence? (Default is true)")
				.define("createLifeEssence", true);
		lifeEssenceAmount = builder.comment("How much mb of life essence made per the minimum ratio of demonic will and blood? (Default is 1)")
				.define("lifeEssenceAmount",1);

		//blood-infused stone creation
		builder.push("blood-infused stone creation");
		createBloodStone = builder.comment("Should you be able to alloy life essence and seared stone IntValueo blood-infused stone? (Default is true)")
				.define("createBloodStone", true);
		bloodStoneAmount = builder.comment("How much mb of blood-infused stone made per the minimum ratio of life essence and seared stone? (Default is 1)")
				.define("bloodStoneAmount", 1);
		meltBlankSlates = builder.comment("Should you be able to melt blank slates IntValueo blood-infused stone? (Default is true)")
				.define("meltBlankSlates", true);
		meltAllSlates = builder.comment("Should you be able to melt all slates IntValueo blood-infused stone? (Default is true)")
				.define("meltAllSlates", true);
		meltBlankRunes = builder.comment("Should you be able to melt blank runes IntValueo blood-infused stone? (Default is true)")
				.define("meltBlankRune", true);
		meltFunctionalRunes = builder.comment("Should you be able to melt functional runes IntValueo blood-infused stone? (Default is true)")
				.define("meltFunctionalRunes", true);
		meltRitualStones = builder.comment("Should you be able to melt ritualStones IntValueo blood-infused stone? (Default is true)")
				.define("meltRitualStones", true);
		meltSigils = builder.comment("Should you be able to melt sigils IntValueo blood-infused stone? (Default is true)")
				.define("meltSigils", true);

		builder.push("blood-infused stone casting");
		//blood-infused stone casting
		castBlankSlates = builder.comment("Should you be able to cast blood-infused stone IntValueo blank slates? (Default is true)")
				.define("castBlankSlates", true);
		castSlatesWithPlateCast = builder.comment("Should you be able to cast blood-infused stone IntValueo blank slates? (Default is true)")
				.define("castSlatesWithPlateCast", true);
		castBlankRunes = builder.comment("Should you be able to cast blood-infused stone IntValueo blank runes? (Default is true)")
				.define("castBlankRunes", true);
		makeCastWithSlates = builder.comment("Should you be able to use slates to make plate casts? (Default is true)")
				.define("makeCastWithSlates", true);
		makeCastWithSigils = builder.comment("Should you be able to use sigils to make plate casts? (Default is true)")
				.define("makeCastWithSigils", true);

		//mod integration
		builder.push("mod integration");
		showJeiDescriptions = builder.comment("Should the additional information be showed over recipes in JEI? (Default is true)")
				.define("showJeiDescriptions", true);
		fluidEncapsulatorWillAmount = builder.comment("How many mb of will should the Fluid Encapsulator move by default? (Default is 1000)")
				.define("fluidEncapsulatorWillAmount", 1000);
		fluidEncapsulatorWillEnergy = builder.comment("How much rf does creating will in the Fluid Encapsulator cost? (Default is 400)")
				.define("fluidEncapsulatorWillEnergy",400);
		magmaCrucibleCrystals = builder.comment("Can the Magma Crucible be used to melt demon will crystals)? (Default is true)")
				.define("magmaCrucibleCrystals", true);
		magmaCrucibleCrystalEnergy = builder.comment( "How much rf does metling demonic will crystals cost? (Default is 20000)")
				.define("magmaCrucibleCrystalEnergy", 20000);

		//progression skipping
		builder.push("progression skipping");
		castUpgradeOrbs = builder.comment("Should you be able to pour life essence onto blood orbs to upgrade them? (Default is false)")
				.define("castUpgradeOrbs",false);
		castAllSlates = builder.comment("Should you be able to pour more life essence onto slates to upgrade them? (Default is false)")
				.define("castAllSlates", false);
		castAltarItems = builder.comment("Should any recipe made in the blood altar be able to be made in the casting table? (Default is false)")
				.define("castAltarItems", false);*/

		builder.pop();
		config = builder.build();
	}
}
