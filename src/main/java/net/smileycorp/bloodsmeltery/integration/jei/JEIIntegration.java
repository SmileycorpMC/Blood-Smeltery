package net.smileycorp.bloodsmeltery.integration.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.plugin.jei.casting.CastingRecipeCategory;
import slimeknights.tconstruct.plugin.jei.smelting.SmeltingRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.gui.GuiHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.common.Loader;

import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.tcon.CastingTartaricRecipe;
import net.smileycorp.bloodsmeltery.common.tcon.MeltingWillRecipe;
import net.smileycorp.bloodsmeltery.integration.jei.tcomplement.JEIIntegrationTComplement;

@JEIPlugin
public class JEIIntegration implements IModPlugin {
	
	public static JEIIntegrationTComplement tCompIntegration;
	public static GuiHelper guiHelper;

	@Override
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
		List<CastingTartaricRecipeWrapper> result = new ArrayList<CastingTartaricRecipeWrapper>();
		for (ICastingRecipe recipe : TinkerRegistry.getAllTableCastingRecipes()) {
			if (recipe instanceof CastingTartaricRecipe) {

				List<ItemStack> input = new ArrayList<ItemStack>();
				input.add(((CastingTartaricRecipe) recipe).getResult().copy());
				NBTTagCompound nbt = input.get(0).getTagCompound();
				nbt.setFloat("souls", 0f);
				input.get(0).setTagCompound(nbt);
				
				CastingTartaricRecipeWrapper newWrapper = new CastingTartaricRecipeWrapper((CastingTartaricRecipe) recipe, input, guiHelper);
				result.add(newWrapper);
			}
		}
		return result;
	}
	
}
