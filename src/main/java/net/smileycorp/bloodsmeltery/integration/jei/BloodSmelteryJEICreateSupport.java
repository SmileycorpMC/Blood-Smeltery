package net.smileycorp.bloodsmeltery.integration.jei;

import com.google.common.collect.Lists;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

import java.util.List;

public class BloodSmelteryJEICreateSupport {

	@SuppressWarnings("removal")
	public static void registerRecipes(IRecipeRegistration registration) {
		List<EmptyingRecipe> emptyingRecipes = Lists.newArrayList();
		List<FillingRecipe> fillingRecipes = Lists.newArrayList();
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			DemonWillUtils.getTartaricGemItems().forEach(item-> {
				emptyingRecipes.add(emptyingRecipe(type, item));
				fillingRecipes.add(fillingRecipe(type, item));
			});
		}
		registration.addRecipes(new RecipeType<>(Create.asResource("draining"), EmptyingRecipe.class), emptyingRecipes);
		registration.addRecipes(new RecipeType<>(Create.asResource("spout_filling"), FillingRecipe.class), fillingRecipes);
	}

	private static EmptyingRecipe emptyingRecipe(EnumDemonWillType type, Item gem) {
		return new ProcessingRecipeBuilder<>(EmptyingRecipe::new,
				Constants.loc("empty_bloodmagic_" + ForgeRegistries.ITEMS.getKey(gem).getPath() + "_of_bloodsmeltery_" + DemonWillUtils.name(type) + "_will"))
				.withItemIngredients(Ingredient.of(DemonWillUtils.createFilledGem(type, (ItemSoulGem) gem, 1000d/((double) BloodSmelteryConfig.willFluidAmount.get()))))
				.withFluidOutputs(DemonWillUtils.getStackForAmount(type, 1000))
				.withSingleItemOutput(DemonWillUtils.createFilledGem(type, (ItemSoulGem) gem, 0))
				.build();
	}

	private static FillingRecipe fillingRecipe(EnumDemonWillType type, Item gem) {
		return new ProcessingRecipeBuilder<>(FillingRecipe::new,
				Constants.loc("fill_bloodmagic_" + ForgeRegistries.ITEMS.getKey(gem).getPath() + "_with_bloodsmeltery_" + DemonWillUtils.name(type) + "_will"))
				.withItemIngredients(Ingredient.of(DemonWillUtils.createFilledGem(type, (ItemSoulGem) gem, 0)))
				.withFluidIngredients(FluidIngredient.fromFluidStack(DemonWillUtils.getStackForAmount(type, 1000)))
				.withSingleItemOutput(DemonWillUtils.createFilledGem(type, (ItemSoulGem) gem, 1000d/((double) BloodSmelteryConfig.willFluidAmount.get())))
				.build();
	}

	public static void hideRecipes(IRecipeManager recipes) {
		RecipeManager manager = Minecraft.getInstance().getConnection().getRecipeManager();
		DemonWillUtils.getTartaricGemItems().forEach(item -> manager.byKey(Create.asResource("empty_bloodmagic_"
						+ ForgeRegistries.ITEMS.getKey(item).getPath() + "_of_bloodsmeltery_demon_will"))
				.ifPresent(recipe -> recipes.hideRecipes(new RecipeType<>(Create.asResource("draining"), EmptyingRecipe.class), Lists.newArrayList(recipe))));
	}

}
