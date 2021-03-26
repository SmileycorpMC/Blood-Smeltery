package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.List;
import java.util.Optional;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;

public class MeltingWillRecipe extends MeltingRecipe {
	
	protected final EnumDemonWillType type;
	
	public MeltingWillRecipe(EnumDemonWillType type) {
		super(RecipeMatchWill.of(type, 1f), new FluidStack(TinkersContent.FLUID_WILLS[type.ordinal()], BloodSmelteryConfig.willFluidAmount), 800);
		this.type=type;
	}
	
	public FluidStack getResult() {
	    return new FluidStack(TinkersContent.FLUID_WILLS[type.ordinal()], BloodSmelteryConfig.willFluidAmount);
	}
	
	
	
	

}
