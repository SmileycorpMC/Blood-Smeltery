package net.smileycorp.bloodsmeltery.common.tcon;

import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import WayofTime.bloodmagic.soul.EnumDemonWillType;

import net.minecraftforge.fluids.FluidStack;

import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;

public class MeltingWillRecipe extends MeltingRecipe {
	
	protected final EnumDemonWillType type;
	
	public MeltingWillRecipe(EnumDemonWillType type) {
		super(RecipeMatchWill.of(type, 1f), new FluidStack(TinkersContent.FLUID_WILLS[type.ordinal()], BloodSmelteryConfig.willFluidAmount), 800);
		this.type=type;
	}
	
	@Override
	public FluidStack getResult() {
	    return new FluidStack(TinkersContent.FLUID_WILLS[type.ordinal()], BloodSmelteryConfig.willFluidAmount);
	}
	
	
	
	

}
