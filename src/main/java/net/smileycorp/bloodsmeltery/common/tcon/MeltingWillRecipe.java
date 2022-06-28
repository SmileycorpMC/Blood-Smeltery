package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.List;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;

public class MeltingWillRecipe extends MeltingRecipe {

	public MeltingWillRecipe(ResourceLocation id, String group, Ingredient input, FluidStack output, int temperature,
			int time, List<FluidStack> byproducts) {
		super(id, group, input, output, temperature, time, byproducts);
		// TODO Auto-generated constructor stub
	}

	/*protected final EnumDemonWillType type;

	public MeltingWillRecipe(EnumDemonWillType type) {
		super(RecipeMatchWill.of(type, 1f), DemonWillUtils.getStackForSouls(type, 1), 800);
		this.type=type;
	}

	@Override
	public FluidStack getResult() {
		return DemonWillUtils.getStackForSouls(type, 1);
	}*/





}
