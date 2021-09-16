package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.FluidWillUtils;
import net.smileycorp.bloodsmeltery.common.bloodaresenal.BloodArsenalRecipes;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.api.impl.recipe.RecipeBloodAltar;
import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.RegistrarBloodMagic;
import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWillGem;

public class TinkersRecipes {

	public static List<Item> sigils = new ArrayList<Item>();
	 
	
	public static void loadRecipes() {
		sigils.addAll(Arrays.asList((new Item[] {RegistrarBloodMagicItems.SIGIL_AIR, RegistrarBloodMagicItems.SIGIL_BLOOD_LIGHT, RegistrarBloodMagicItems.SIGIL_BOUNCE, 
			RegistrarBloodMagicItems.SIGIL_CLAW, RegistrarBloodMagicItems.SIGIL_DIVINATION, RegistrarBloodMagicItems.SIGIL_LAVA, 
			RegistrarBloodMagicItems.SIGIL_WATER, RegistrarBloodMagicItems.SIGIL_VOID, RegistrarBloodMagicItems.SIGIL_GREEN_GROVE, 
			RegistrarBloodMagicItems.SIGIL_ELEMENTAL_AFFINITY, RegistrarBloodMagicItems.SIGIL_HASTE, RegistrarBloodMagicItems.SIGIL_MAGNETISM, 
			RegistrarBloodMagicItems.SIGIL_SUPPRESSION, RegistrarBloodMagicItems.SIGIL_FAST_MINER, RegistrarBloodMagicItems.SIGIL_SEER, 
			RegistrarBloodMagicItems.SIGIL_ENDER_SEVERANCE, RegistrarBloodMagicItems.SIGIL_WHIRLWIND, RegistrarBloodMagicItems.SIGIL_PHANTOM_BRIDGE, 
			RegistrarBloodMagicItems.SIGIL_COMPRESSION, RegistrarBloodMagicItems.SIGIL_HOLDING, RegistrarBloodMagicItems.SIGIL_TELEPOSITION, 
			RegistrarBloodMagicItems.SIGIL_TRANSPOSITION })));
		
		if (Loader.isModLoaded("bloodarsenal")) BloodArsenalRecipes.loadRecipes();
		
		//alloying
		if (BloodSmelteryConfig.createLifeEssence) {
			for (Fluid will : FluidWillUtils.getWillFluids()) {
				TinkerRegistry.registerAlloy(new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.lifeEssenceAmount),
		                new FluidStack(will, BloodSmelteryConfig.lifeEssenceRatio[0]),
		                new FluidStack(TinkerFluids.blood, BloodSmelteryConfig.lifeEssenceRatio[1]));
			}
		}
		if (BloodSmelteryConfig.createBloodStone) {
			TinkerRegistry.registerAlloy(new FluidStack(TinkersContent.BLOOD_INFUSED_STONE, BloodSmelteryConfig.bloodStoneAmount),
	                new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.bloodStoneRatio[0]),
	                new FluidStack(TinkerFluids.searedStone, BloodSmelteryConfig.bloodStoneRatio[1]));
		}
		
		//casting
		if (BloodSmelteryConfig.castBlankSlates) {
			TinkerRegistry.registerTableCasting(new ItemStack(RegistrarBloodMagicItems.SLATE), BloodSmelteryConfig.castSlatesWithPlateCast ? 
					TinkerSmeltery.castPlate : ItemStack.EMPTY , TinkersContent.BLOOD_INFUSED_STONE, 250);
		}
		if (BloodSmelteryConfig.castBlankRunes) {
			TinkerRegistry.registerBasinCasting(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE), ItemStack.EMPTY, TinkersContent.BLOOD_INFUSED_STONE, 1000);
		}
	 	
	 	for (EnumDemonWillType will : EnumDemonWillType.values()) {
	 		int i = will.ordinal();
	 		if (BloodSmelteryConfig.castWill) {
			 	ItemStack soul = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, i);
			 	NBTTagCompound nbt = new NBTTagCompound();
			 	nbt.setFloat("souls", 1f);
			 	soul.setTagCompound(nbt);
			 	
			 	TinkerRegistry.registerTableCasting(soul, ItemStack.EMPTY, FluidWillUtils.getFluidForType(will), BloodSmelteryConfig.willFluidAmount);
	 		}
	 		
	 		if (BloodSmelteryConfig.castFillTartaricGems) {
	 			for (int j = 0; j < 5; j++) {
	 				ItemStack soul = new ItemStack(RegistrarBloodMagicItems.SOUL_GEM, 1, j);
	 				((IDemonWillGem)RegistrarBloodMagicItems.SOUL_GEM).setWill(will, soul, 1f);
	 				TinkerRegistry.registerTableCasting(new CastingTartaricRecipe(will, j));
	 			}
	 		}
			 	
		 	if (BloodSmelteryConfig.castSoulStone) {
		 		TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(RegistrarBloodMagicBlocks.DEMON_EXTRAS, 1, i), 
		 				RecipeMatch.of("stone"), new FluidStack(FluidWillUtils.getFluidForType(will), BloodSmelteryConfig.soulStoneCost), true, true));
		 	}
		 	if (BloodSmelteryConfig.castDemonAlloy) {
		 		List<ItemStack> ingredients = new ArrayList<ItemStack>();
		 		for (Entry<ResourceLocation, Integer> entry : BloodSmelteryConfig.demonAlloyCastIngredient) {
			 		try {
			 			ingredients.add(new ItemStack(ForgeRegistries.BLOCKS.getValue(entry.getKey()), 1, entry.getValue()));
			 		}  catch (Exception e0) {
			 			try {
				 			ingredients.add(new ItemStack(ForgeRegistries.ITEMS.getValue(entry.getKey()), 1, entry.getValue()));
				 		}  catch (Exception e1) {
				 			
				 		}
			 		}
		 		}
		 		if (ingredients.isEmpty()) {
		 			ingredients.add(ItemStack.EMPTY);
		 		}
		 		TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(RegistrarBloodMagicBlocks.DEMON_EXTRAS, 1, i+10), 
		 				RecipeMatch.of(ingredients), new FluidStack(FluidWillUtils.getFluidForType(will), BloodSmelteryConfig.demonAlloyCost), true, true));
		 	}
	 	}
	 	
	 	if (BloodSmelteryConfig.castUpgradeOrbs) {
	 		int[] costs = {5000, 25000, 40000, 80000, 200000};
	 		for (int i = 1; i < RegistrarBloodMagic.BLOOD_ORBS.getEntries().size(); i++) {
	 			ItemStack output = new ItemStack(RegistrarBloodMagicItems.BLOOD_ORB);
	 			NBTTagCompound nbt = new NBTTagCompound();
	 			nbt.setString("orb", RegistrarBloodMagic.BLOOD_ORBS.getKey(RegistrarBloodMagic.BLOOD_ORBS.getValues().get(i)).toString());
			 	output.setTagCompound(nbt);
	 			TinkerRegistry.registerTableCasting(new CastingRecipe(output, 
	 				RecipeMatchBloodOrb.of(RegistrarBloodMagic.BLOOD_ORBS.getValues().get(i-1)), new FluidStack(BlockLifeEssence.getLifeEssence(), costs[i-1]), true, true));
	 		}
	 	}
	 	if (BloodSmelteryConfig.castAllSlates &! BloodSmelteryConfig.castAltarItems) {
	 		int[] costs = {2000, 5000, 15000, 30000};
	 		for (int i = 1; i < 5; i++) {
	 			TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(RegistrarBloodMagicItems.SLATE, 1, i), 
	 				RecipeMatch.of(new ItemStack(RegistrarBloodMagicItems.SLATE, 1, i-1)), new FluidStack(BlockLifeEssence.getLifeEssence(), costs[i-1]), true, true));
	 		}
	 	}
	 	
	 	//cast creation 
	 	for(FluidStack fluid : TinkerSmeltery.castCreationFluids) {
	 		if (BloodSmelteryConfig.makeCastWithSlates) {
	 			for (int i = 0; i<5; i++) TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castPlate, RecipeMatch.of(new ItemStack(RegistrarBloodMagicItems.SLATE, 1, i)), fluid, true, true));
	 		}
	 		if (BloodSmelteryConfig.makeCastWithSigils) {
	 			for (Item sigil : sigils) TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castPlate, RecipeMatch.of(sigil), fluid, true, true));
	 		}
	 	}
	 	
	 	//melting
	 	if (BloodSmelteryConfig.unifiedWill) {
	 		TinkerRegistry.registerMelting(new MeltingWillRecipe(EnumDemonWillType.DEFAULT));
	 		if (BloodSmelteryConfig.meltCrystals)TinkerRegistry.registerMelting(RegistrarBloodMagicItems.ITEM_DEMON_CRYSTAL, TinkersContent.FLUID_RAW_WILL, 
	 				BloodSmelteryConfig.crystalMeltMultiplier*BloodSmelteryConfig.willFluidAmount);
	 	} else {
	 		for (EnumDemonWillType will : EnumDemonWillType.values()) TinkerRegistry.registerMelting(new MeltingWillRecipe(will));
	 		if (BloodSmelteryConfig.meltCrystals) for (EnumDemonWillType will : EnumDemonWillType.values()) TinkerRegistry.registerMelting(will.getStack(), FluidWillUtils.getFluidForType(will), 
	 				BloodSmelteryConfig.crystalMeltMultiplier*BloodSmelteryConfig.willFluidAmount);
	 	}
	 	
	 	if (BloodSmelteryConfig.meltAllSlates) {
	 		for (int i = 0; i<5; i++) TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicItems.SLATE, 1, i), TinkersContent.BLOOD_INFUSED_STONE, 250);
	 	} else if (BloodSmelteryConfig.meltBlankSlates) {
	 		TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 0), TinkersContent.BLOOD_INFUSED_STONE, 250);
	 	}
	 	
	 	if (BloodSmelteryConfig.meltFunctionalRunes) {
	 		for (int i = 0; i<11; i++) TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE, 1, i), TinkersContent.BLOOD_INFUSED_STONE, 1000);
	 	} else if (BloodSmelteryConfig.meltBlankRunes) {
	 		TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE, 1, 0), TinkersContent.BLOOD_INFUSED_STONE, 1000);
	 	}
	 	if (BloodSmelteryConfig.meltRitualStones) {
		 	for (int i = 0; i<5; i++) TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.RITUAL_STONE, 1, i), TinkersContent.BLOOD_INFUSED_STONE, 250);
		 	TinkerRegistry.registerMelting(RegistrarBloodMagicBlocks.RITUAL_CONTROLLER, TinkersContent.BLOOD_INFUSED_STONE, 1000);
		 	TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.RITUAL_CONTROLLER, 1, 2), TinkersContent.BLOOD_INFUSED_STONE, 1000);
	 	}
	 	if (BloodSmelteryConfig.meltSigils) {
	 		for (Item sigil : sigils) TinkerRegistry.registerMelting(sigil, TinkersContent.BLOOD_INFUSED_STONE, 250);
	 	}
	 	
	}
	
	public static void loadLateRecipes() {
		if (BloodSmelteryConfig.castAltarItems) {
			for (RecipeBloodAltar recipe : BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAltarRecipes()) {
				RecipeMatch input = RecipeMatch.of(Arrays.asList(recipe.getInput().getMatchingStacks()));
				if (!input.matches(NonNullList.<ItemStack>from(new ItemStack(Items.BUCKET))).isPresent()) {
					TinkerRegistry.registerTableCasting(new CastingRecipe(recipe.getOutput(), input, 
							new FluidStack(BlockLifeEssence.getLifeEssence(), recipe.getSyphon()), true, true));
				}
			}
		}
	}
}
