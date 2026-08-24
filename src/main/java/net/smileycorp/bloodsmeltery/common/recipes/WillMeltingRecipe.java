package net.smileycorp.bloodsmeltery.common.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;

import java.util.Collections;
import java.util.List;

public class WillMeltingRecipe extends MeltingRecipe {

    public static final RecordLoadable<WillMeltingRecipe> LOADER = RecordLoadable.create(ContextKey.ID.requiredField(), LoadableRecipeSerializer.RECIPE_GROUP, INPUT, OUTPUT, TEMPERATURE, TIME, BYPRODUCTS, WillMeltingRecipe::new);

	private final Ingredient JEI_INGREDIENT;

	public WillMeltingRecipe(ResourceLocation id, String group, Ingredient input, FluidOutput output, int temperature, int time, List<FluidOutput> byproducts) {
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
		if (!stack.hasTag()) return 0;
		CompoundTag tag = stack.getTag();
		return tag.contains("souls") ? (int)Math.floor(tag.getDouble("souls") * BloodSmelteryConfig.willMeltingTime.get()) : 0;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY, JEI_INGREDIENT);
	}

	@Override
	public FluidStack getOutput(IMeltingContainer inv) {
		ItemStack stack = inv.getStack();
		if (!stack.hasTag()) new FluidStack(getOutput().getFluid(), 0);
		CompoundTag tag = stack.getTag();
		return new FluidStack(getOutput().getFluid(), tag.contains("souls") ? (int)Math.floor(tag.getDouble("souls") * BloodSmelteryConfig.willFluidAmount.get()) : 0);
	}

	@Override
	public List<List<FluidStack>> getOutputWithByproducts() {
		return Collections.singletonList(Collections.singletonList(new FluidStack(getOutput().getFluid(), BloodSmelteryConfig.willFluidAmount.get())));
	}

}
