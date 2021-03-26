package net.smileycorp.bloodsmeltery.common.jei;

import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.plugin.jei.casting.CastingRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.gui.GuiHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.smileycorp.bloodsmeltery.common.tcon.CastingTartaricRecipe;

public class CastingTartaricHandler implements IRecipeWrapperFactory<CastingTartaricRecipeWrapper> {
	
	@Override
	public IRecipeWrapper getRecipeWrapper(CastingTartaricRecipeWrapper wrapper) {
		return wrapper;
	}

}
