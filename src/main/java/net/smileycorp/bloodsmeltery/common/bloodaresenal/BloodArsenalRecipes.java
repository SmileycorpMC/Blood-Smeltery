package net.smileycorp.bloodsmeltery.common.bloodaresenal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.FluidWillUtils;
import net.smileycorp.bloodsmeltery.common.tcon.TinkersRecipes;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.shared.TinkerFluids;
import WayofTime.bloodmagic.block.BlockLifeEssence;
import arcaratus.bloodarsenal.compat.tconstruct.TConstructPlugin;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;

public class BloodArsenalRecipes {

	public static void loadRecipes() {
		//Sigils
		TinkersRecipes.sigils.add(RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING);
		TinkersRecipes.sigils.add(RegistrarBloodArsenalItems.SIGIL_DIVINITY);
		TinkersRecipes.sigils.add(RegistrarBloodArsenalItems.SIGIL_ENDER);
		TinkersRecipes.sigils.add(RegistrarBloodArsenalItems.SIGIL_LIGHTNING);
		TinkersRecipes.sigils.add(RegistrarBloodArsenalItems.SIGIL_SENTIENCE);
		TinkersRecipes.sigils.add(RegistrarBloodArsenalItems.SIGIL_SWIMMING);
		
		//Alloys
		if (BloodSmelteryConfig.bloodGlassFluid) {
			TinkerRegistry.registerAlloy(new FluidStack(BloodArsenalContent.BLOOD_INFUSED_GLASS, BloodSmelteryConfig.bloodGlassAmount),
	                new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.bloodGlassRatio[0]),
	                new FluidStack(TinkerFluids.glass, BloodSmelteryConfig.bloodGlassRatio[1]));
		}
		
		//Casting
		if (BloodSmelteryConfig.castingBloodGlass && BloodSmelteryConfig.bloodGlassFluid) {
			TinkerRegistry.registerBasinCasting(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS) , ItemStack.EMPTY, BloodArsenalContent.BLOOD_INFUSED_GLASS, BloodSmelteryConfig.castingBloodGlassAmount);
		}
		if (BloodSmelteryConfig.castingBloodGlassPanes && BloodSmelteryConfig.bloodGlassFluid) {
			TinkerRegistry.registerTableCasting(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS_PANE) , ItemStack.EMPTY, BloodArsenalContent.BLOOD_INFUSED_GLASS, BloodSmelteryConfig.castingBloodGlassPanesAmount);
		}
		
		//Melting
		//Casting
		if (BloodSmelteryConfig.meltingBloodGlass && BloodSmelteryConfig.bloodGlassFluid) {
			TinkerRegistry.registerMelting(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS, BloodArsenalContent.BLOOD_INFUSED_GLASS, BloodSmelteryConfig.castingBloodGlassAmount);
		}
		if (BloodSmelteryConfig.meltingBloodPanes && BloodSmelteryConfig.bloodGlassFluid) {
			TinkerRegistry.registerMelting(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS_PANE, BloodArsenalContent.BLOOD_INFUSED_GLASS, BloodSmelteryConfig.castingBloodGlassPanesAmount);
		}
		if (Loader.isModLoaded("enderio") && BloodSmelteryConfig.enderIOAlloyBloodIron) {
			for (Fluid fluid : FluidWillUtils.getWillFluids()) {
				TinkerRegistry.registerAlloy(new FluidStack(TConstructPlugin.FLUID_MOLTEN_BLOOD_INFUSED_IRON, BloodSmelteryConfig.enderIOBloodIronAmount), 
					new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.enderIOBloodIronRatio[0]), new FluidStack(fluid, BloodSmelteryConfig.enderIOBloodIronRatio[1]), 
					new FluidStack(TinkerFluids.iron, BloodSmelteryConfig.enderIOBloodIronRatio[2]), new FluidStack(FluidRegistry.getFluid("glowstone"),
						BloodSmelteryConfig.enderIOBloodIronRatio[3]), new FluidStack(TinkerFluids.gold, BloodSmelteryConfig.enderIOBloodIronRatio[4]),
					new FluidStack(FluidRegistry.getFluid("rocket_fuel"), BloodSmelteryConfig.enderIOBloodIronRatio[5]));
			}
		}
		if (Loader.isModLoaded("mekanism") && BloodSmelteryConfig.mekAlloyBloodIron) {
			for (Fluid fluid : FluidWillUtils.getWillFluids()) {
				TinkerRegistry.registerAlloy(new FluidStack(TConstructPlugin.FLUID_MOLTEN_BLOOD_INFUSED_IRON, BloodSmelteryConfig.mekBloodIronAmount), 
					new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.mekBloodIronRatio[0]), new FluidStack(fluid, BloodSmelteryConfig.mekBloodIronRatio[1]), 
					new FluidStack(TinkerFluids.iron, BloodSmelteryConfig.mekBloodIronRatio[2]), new FluidStack(FluidRegistry.getFluid("glowstone"),
						BloodSmelteryConfig.mekBloodIronRatio[3]), new FluidStack(TinkerFluids.gold, BloodSmelteryConfig.mekBloodIronRatio[4]),
					new FluidStack(FluidRegistry.getFluid("redstone"), BloodSmelteryConfig.mekBloodIronRatio[5]), 
					new FluidStack(FluidRegistry.getFluid("liquidsulfurdioxide"), BloodSmelteryConfig.mekBloodIronRatio[6]));
			}
		}
	}

}
