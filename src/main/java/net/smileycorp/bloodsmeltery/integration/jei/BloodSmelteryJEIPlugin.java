package net.smileycorp.bloodsmeltery.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IAdvancedRegistration;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;

@JeiPlugin
public class BloodSmelteryJEIPlugin implements IModPlugin {

	@Override
	public void registerAdvanced(IAdvancedRegistration registration){
		//registration.addRecipeManagerPlugin();
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ModDefinitions.getResource("main");
	}

}
