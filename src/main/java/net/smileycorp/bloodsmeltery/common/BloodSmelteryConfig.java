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
	public static ConfigValue<Integer> willMeltingTime;

	//molten demonite
	public static BooleanValue unifiedDemonite;

	//modifiers
	//bloodstained
	public static ConfigValue<Integer> bloodstainedLPCost;
	public static ConfigValue<Float> bloodstainedLPMultiplier;
	public static BooleanValue bloodstainedHurtsPlayers;
	public static ConfigValue<Integer> bloodstainedCooldown;

	//exsanguinate
	public static ConfigValue<Float> exsanguinateLPMultiplier;
	public static ConfigValue<Float> exsanguinateLPRate;

	//transfusion
	public static ConfigValue<Float> transfusionLPMultiplier;
	public static ConfigValue<Float> transfusionLPRate;

	//transfusion
	public static ConfigValue<Integer> ambrosiacLPThreshold;

	static {

		//fluid will
		builder.push("fluid will");
		enableFluidWill = builder.comment("Should demonic will fluids be added to the game? (Default is true)")
				.define("enableFluidWill", true);
		unifiedWill = builder.comment("Should all demonic will fluids be replaced with a single unified fluid? (Default is false)")
				.define("unifiedWill", false);
		willFluidAmount = builder.comment("How much mb per will quality does fluid will have? (Default is 100)")
				.define("willFluidAmount", 100);
		willMeltingTime = builder.comment("How many ticks does it take to melt will items per will quality? (Default is 20)")
				.define("willMeltingTime", 20);
		builder.pop();
		builder.push("molten demonite");
		unifiedDemonite = builder.comment("Should all molten demonite fluids be replaced with a single unified fluid? (Default is false)")
				.define("unifiedDemonite", false);
		builder.pop();
		builder.push("modifiers");
		builder.push("bloodstained");
		bloodstainedLPCost = builder.comment("How much life essence does it cost to repair 1 durability for bloodstained items? (Default is 100)")
				.define("bloodstainedLPCost", 100);
		bloodstainedLPMultiplier = builder.comment("What factor does the bloodstained modifier's cost reduce to each level? (Default is 0.7)")
				.define("bloodstainedLPMultiplier", 0.7f);
		bloodstainedHurtsPlayers = builder.comment("Do bloodstained items try to repair even if a player doesn't have enough lp, and damaging them if they don't have enough? (Default is false)")
				.define("bloodstainedHurtsPlayers", false);
		bloodstainedCooldown = builder.comment("How many ticks should bloodstained items wait before attempting to repair themselves? (Default is 5)")
				.define("bloodstainedCooldown", 5);
		builder.pop();
		builder.push("exsanguinate");
		exsanguinateLPMultiplier = builder.comment("How much is the lp gained from exsanguinate multiplied by per level? (Default is 1.3)")
				.define("exsanguinateLPMultiplier", 1.3f);
		exsanguinateLPRate = builder.comment("How much lp is gained per damage dealt? (Default is 10)")
				.define("exsanguinateLPRate", 10f);
		builder.push("transfusion");
		transfusionLPMultiplier = builder.comment("How much is the lp gained from transfusion multiplied by per level? (Default is 1.3)")
				.define("transfusionLPMultiplier", 1.3f);
		transfusionLPRate = builder.comment("How much lp is gained per damage blocked or reduced? (Default is 10)")
				.define("transfusionLPRate", 10f);
		builder.pop();
		builder.push("Ambrosiac");
		ambrosiacLPThreshold = builder.comment("How much life essence needs to be drained to trigger ambrosiac's repairing(Default is 100)")
				.define("ambrosiacLPThreshold", 100);
		builder.pop();
		builder.pop();
		config = builder.build();
	}
}
