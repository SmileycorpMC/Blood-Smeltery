package net.smileycorp.bloodsmeltery.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.gui.GuiHelper;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;

//@JeiPlugin
public class JEIIntegration implements IModPlugin {

	public static GuiHelper guiHelper;

	/*@Override
	public void register(@Nonnull IModRegistry registry) {
		guiHelper = (GuiHelper) registry.getJeiHelpers().getGuiHelper();
		registry.addRecipes(parseTartaricRecipes(), CastingRecipeCategory.CATEGORY);

		if (BloodSmelteryConfig.showJeiDescriptions) {
			registry.handleRecipes(MeltingWillRecipe.class, new MeltingWillHandler(), SmeltingRecipeCategory.CATEGORY);
			registry.handleRecipes(CastingTartaricRecipeWrapper.class, new CastingTartaricHandler(), CastingRecipeCategory.CATEGORY);

			if (Loader.isModLoaded("tcomplement")) {
				tCompIntegration = new JEIIntegrationTComplement();
				tCompIntegration.register(registry);
			}
		}
	}

	private List<CastingTartaricRecipeWrapper> parseTartaricRecipes() {
		List<CastingTartaricRecipeWrapper> result = new ArrayList<>();
		for (ICastingRecipe recipe : TinkerRegistry.getAllTableCastingRecipes()) {
			if (recipe instanceof CastingTartaricRecipe) {

				List<ItemStack> input = new ArrayList<>();
				input.add(((CastingTartaricRecipe) recipe).getResult().copy());
				NBTTagCompound nbt = input.get(0).getTagCompound();
				nbt.setFloat("souls", 0f);
				input.get(0).setTagCompound(nbt);

				CastingTartaricRecipeWrapper newWrapper = new CastingTartaricRecipeWrapper((CastingTartaricRecipe) recipe, input, guiHelper);
				result.add(newWrapper);
			}
		}
		return result;
	}*/

	@Override
	public ResourceLocation getPluginUid() {
		return ModDefinitions.getResource("jei_support");
	}

}
