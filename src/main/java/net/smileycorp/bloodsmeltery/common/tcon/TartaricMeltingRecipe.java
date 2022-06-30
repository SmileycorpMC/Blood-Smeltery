package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.smileycorp.bloodsmeltery.common.ReflectionUtils;
import slimeknights.tconstruct.library.recipe.melting.IMeltingInventory;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.smeltery.tileentity.controller.SmelteryTileEntity;
import slimeknights.tconstruct.smeltery.tileentity.module.MeltingModule;
import slimeknights.tconstruct.smeltery.tileentity.tank.SmelteryTank;

public class TartaricMeltingRecipe extends MeltingRecipe {

	private final IngredientDemonWill JEI_INGREDIENT;

	private final Set<ItemStack> RECIPE_STACKS = new HashSet<>();

	public TartaricMeltingRecipe(ResourceLocation id, String group, Ingredient input, FluidStack output, int temperature, int time, List<FluidStack> byproducts) {
		super(id, group, input, output, temperature, time, byproducts);
		JEI_INGREDIENT = (IngredientDemonWill) input;
	}

	@Override
	public boolean matches(IMeltingInventory inv, World world) {
		ItemStack stack = inv.getStack();
		if (JEI_INGREDIENT.test(stack)) {
			//hacky workaround to check if the recipe is starting or completing
			if (RECIPE_STACKS.contains(stack)) {
				RECIPE_STACKS.remove(stack);
				CompoundNBT tag = stack.getOrCreateTag();
				tag.putDouble("souls", tag.getDouble("souls") - 1);
				SmelteryTileEntity smeltery = ReflectionUtils.getSmelteryTile((MeltingModule) inv);
				if (smeltery != null) {
					SmelteryTank tank = smeltery.getTank();
					tank.fill(getOutput(), FluidAction.EXECUTE);
				}
				return false;
			} else {
				RECIPE_STACKS.add(stack);
				return true;
			}
		}
		return false;
	}


	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY, JEI_INGREDIENT);
	}

	@Override
	public List<List<FluidStack>> getDisplayOutput() {
		return	getOutputWithByproducts();
	}

	@Override
	public List<List<FluidStack>> getOutputWithByproducts() {
		return Collections.singletonList(Collections.singletonList(new FluidStack(getOutput().getFluid(), 100)));
	}

}
