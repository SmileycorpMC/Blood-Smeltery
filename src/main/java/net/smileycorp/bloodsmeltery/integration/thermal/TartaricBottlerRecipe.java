package net.smileycorp.bloodsmeltery.integration.thermal;

import java.util.List;

import cofh.lib.fluid.FluidIngredient;
import cofh.thermal.core.util.recipes.machine.BottlerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class TartaricBottlerRecipe extends BottlerRecipe {

	public TartaricBottlerRecipe(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems,
			List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances,
			List<FluidStack> outputFluids) {
		super(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getResultItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeType<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
