package net.smileycorp.bloodsmeltery.integration.jei.tcomplement;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

import net.smileycorp.bloodsmeltery.common.tcon.MeltingWillRecipe;

public class MelterWillHandler implements IRecipeWrapperFactory<MeltingWillRecipe> {

	@Override
	public IRecipeWrapper getRecipeWrapper(MeltingWillRecipe recipe) {
		return new MelterWillRecipeWrapper(recipe);
	}

}
