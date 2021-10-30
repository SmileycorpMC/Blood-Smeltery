package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.FluidWillUtils;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import WayofTime.bloodmagic.soul.EnumDemonWillType;

public class MeltingWillRecipe extends MeltingRecipe {
	
	protected final EnumDemonWillType type;
	
	public MeltingWillRecipe(EnumDemonWillType type) {
		super(RecipeMatchWill.of(type, 1f), FluidWillUtils.getStackForSouls(type, 1), 800);
		this.type=type;
	}
	
	@Override
	public FluidStack getResult() {
	    return FluidWillUtils.getStackForSouls(type, 1);
	}
	
	
	
	

}
