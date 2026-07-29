package net.smileycorp.bloodsmeltery.integration.jei;

public class BloodSmelteryJEICreateSupport {

	/*@SuppressWarnings("removal")
	public static void registerRecipes(IRecipeRegistration registration) {
		List<EmptyingRecipe> emptying_recipes = Lists.newArrayList();
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			if (type == EnumDemonWillType.DEFAULT) return;
			DemonWillUtils.getTartaricGemItems().forEach(item-> emptying_recipes.add(emptyingRecipe(type, item)));
		}
		registration.addRecipes(emptying_recipes, Create.asResource("draining"));
	}

	private static EmptyingRecipe emptyingRecipe(EnumDemonWillType type, Item gem) {
		ItemStack stack = DemonWillUtils.createFilledGem(type, (ItemSoulGem) gem);
		ItemStack output = stack.copy();
		((ItemSoulGem) gem).drainWill(type, output, 1000d/((double)BloodSmelteryConfig.willFluidAmount.get()), true);
		return new ProcessingRecipeBuilder<>(EmptyingRecipe::new,
				Constants.loc("empty_bloodmagic_" + gem.getRegistryName().getPath() + "_of_bloodsmeltery_" + type.toString() + "_will"))
				.withItemIngredients(Ingredient.of(stack))
				.withFluidOutputs(DemonWillUtils.getStackForAmount(type, 1000))
				.withSingleItemOutput(output)
				.build();
	}*/

}
