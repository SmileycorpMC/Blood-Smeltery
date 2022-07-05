package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.Collections;
import java.util.List;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;

public class WillMeltingRecipe extends MeltingRecipe {

	private final Ingredient JEI_INGREDIENT;

	public WillMeltingRecipe(ResourceLocation id, String group, Ingredient input, FluidStack output, int temperature, int time, List<FluidStack> byproducts) {
		super(id, group, input, output, temperature, time, byproducts);
		ItemStack stack = input.getItems()[0];
		CompoundTag tag = new CompoundTag();
		tag.putDouble("souls", 1);
		stack.setTag(tag);
		JEI_INGREDIENT = Ingredient.of(stack);
	}

	@Override
	public int getTime(IMeltingContainer inv) {
		ItemStack stack = inv.getStack();
		int amount = 0;
		if (stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if(tag.contains("souls")) {
				amount = (int)Math.floor(tag.getDouble("souls") * BloodSmelteryConfig.willMeltingTime.get());
			}
		}
		return amount;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY, JEI_INGREDIENT);
	}

	@Override
	public FluidStack getOutput(IMeltingContainer inv) {
		ItemStack stack = inv.getStack();
		int amount = 0;
		if (stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if(tag.contains("souls")) {
				amount = (int)Math.floor(tag.getDouble("souls") * BloodSmelteryConfig.willFluidAmount.get());
			}
		}
		return new FluidStack(getOutput().getFluid(), amount);
	}

	@Override
	public List<List<FluidStack>> getOutputWithByproducts() {
		return Collections.singletonList(Collections.singletonList(new FluidStack(getOutput().getFluid(), 100)));
	}

}
