package net.smileycorp.bloodsmeltery.integration.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class CastingTartaricHandler implements IRecipeWrapperFactory<CastingTartaricRecipeWrapper> {
	
	@Override
	public IRecipeWrapper getRecipeWrapper(CastingTartaricRecipeWrapper wrapper) {
		return wrapper;
	}

}
