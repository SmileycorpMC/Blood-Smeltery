package net.smileycorp.bloodsmeltery.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.smileycorp.bloodsmeltery.common.Constants;

@JeiPlugin
public class BloodSmelteryJEIPlugin implements IModPlugin {

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if (ModList.get().isLoaded("create")) BloodSmelteryJEICreateSupport.registerRecipes(registration);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return Constants.loc("main");
	}

}
