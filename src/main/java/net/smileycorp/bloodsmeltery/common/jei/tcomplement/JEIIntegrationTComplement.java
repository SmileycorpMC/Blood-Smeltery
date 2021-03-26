package net.smileycorp.bloodsmeltery.common.jei.tcomplement;

import javax.annotation.Nonnull;

import net.smileycorp.bloodsmeltery.common.tcon.MeltingWillRecipe;
import knightminer.tcomplement.common.Config;
import knightminer.tcomplement.common.PulseBase;
import knightminer.tcomplement.plugin.jei.highoven.melting.HighOvenMeltingCategory;
import knightminer.tcomplement.plugin.jei.melter.MeltingRecipeCategory;
import mezz.jei.api.IModRegistry;

public class JEIIntegrationTComplement {

	public void register(@Nonnull IModRegistry registry) {
		if(PulseBase.isMelterLoaded() && Config.jei.separateMelterTab) {
			registry.handleRecipes(MeltingWillRecipe.class, new MelterWillHandler(), MeltingRecipeCategory.CATEGORY);
		}
		if(PulseBase.isSteelworksLoaded() && Config.jei.separateHighOvenTab) {
			registry.handleRecipes(MeltingWillRecipe.class, new HighOvenWillHandler(), HighOvenMeltingCategory.CATEGORY);
		}		
	}
	
}
