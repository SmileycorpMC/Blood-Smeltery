package net.smileycorp.bloodsmeltery.integration.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.integration.thermal.ThermalIntegration;

@JeiPlugin
public class BloodSmelteryJEIPlugin implements IModPlugin {

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if (ModList.get().isLoaded("create")) BloodSmelteryJEICreateSupport.registerRecipes(registration);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		ModList modlist = ModList.get();
		if (modlist.isLoaded("create")) BloodSmelteryJEICreateSupport.hideRecipes(jeiRuntime.getRecipeManager());
		if (!modlist.isLoaded("thermal")) jeiRuntime.getIngredientManager()
				.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, Lists.newArrayList(new ItemStack(ThermalIntegration.BLOODBRASS_COIN.get())));
	}

	@Override
	public ResourceLocation getPluginUid() {
		return Constants.loc("main");
	}

}
