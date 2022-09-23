package net.smileycorp.bloodsmeltery.integration.jei;

import java.util.List;

import com.google.common.collect.Lists;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

public class BloodSmelteryJEICreateSupport {

	public static void registerRecipes(IRecipeRegistration registration) {
		List<EmptyingRecipe> emptying_recipes = Lists.newArrayList();
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			if (type == EnumDemonWillType.DEFAULT) continue;
			emptying_recipes.add(emptyingRecipe(type, BloodMagicItems.PETTY_GEM.get()));
			emptying_recipes.add(emptyingRecipe(type, BloodMagicItems.LESSER_GEM.get()));
			emptying_recipes.add(emptyingRecipe(type, BloodMagicItems.COMMON_GEM.get()));
			emptying_recipes.add(emptyingRecipe(type, BloodMagicItems.GREATER_GEM.get()));
		}
		registration.addRecipes(emptying_recipes, Create.asResource("draining"));
	}

	private static EmptyingRecipe emptyingRecipe(EnumDemonWillType type, Item gem) {
		ItemStack stack = DemonWillUtils.createFilledGem(type, (ItemSoulGem) gem);
		ItemStack output = stack.copy();
		((ItemSoulGem) gem).drainWill(type, output, 1000d/((double)BloodSmelteryConfig.willFluidAmount.get()), true);
		return new ProcessingRecipeBuilder<>(EmptyingRecipe::new,
				ModDefinitions.getResource("empty_bloodmagic_" + gem.getRegistryName().getPath() + "_of_bloodsmeltery_" + type.toString() + "_will"))
				.withItemIngredients(Ingredient.of(stack))
				.withFluidOutputs(DemonWillUtils.getStackForAmount(type, 1000))
				.withSingleItemOutput(output)
				.build();
	}

}
