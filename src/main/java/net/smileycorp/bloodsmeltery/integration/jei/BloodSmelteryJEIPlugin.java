package net.smileycorp.bloodsmeltery.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;

@JeiPlugin
public class BloodSmelteryJEIPlugin implements IModPlugin {

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if (ModList.get().isLoaded("create")) BloodSmelteryJEICreateSupport.registerRecipes(registration);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ModDefinitions.getResource("main");
	}

}
