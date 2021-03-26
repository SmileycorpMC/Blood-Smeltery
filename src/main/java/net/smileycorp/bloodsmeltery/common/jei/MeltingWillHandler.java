package net.smileycorp.bloodsmeltery.common.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.smileycorp.bloodsmeltery.common.tcon.MeltingWillRecipe;

public class MeltingWillHandler implements IRecipeWrapperFactory<MeltingWillRecipe> {

	@Override
	public IRecipeWrapper getRecipeWrapper(MeltingWillRecipe recipe) {
		return new MeltingWillRecipeWrapper(recipe);
	}

}
