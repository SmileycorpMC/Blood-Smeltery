package net.smileycorp.bloodsmeltery.common.jei;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.gui.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.tcon.CastingTartaricRecipe;
import net.smileycorp.bloodsmeltery.common.tcon.MeltingWillRecipe;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.plugin.jei.casting.CastingRecipeWrapper;
import slimeknights.tconstruct.plugin.jei.smelting.SmeltingRecipeWrapper;

public class CastingTartaricRecipeWrapper extends CastingRecipeWrapper  {
	
	public CastingTartaricRecipeWrapper(CastingTartaricRecipe recipe, List<ItemStack> input, GuiHelper helper) {
		super(input, recipe.getJEIPassthrough(), helper.createDrawable(Util.getResource("textures/gui/jei/casting.png"), 141, 0, 16, 16));
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
		super.drawInfo(minecraft, width, height, mouseX, mouseY);
		String desc0 = I18n.translateToLocal("desc.bloodsmeltery.tartariccasting").replace("%s", String.valueOf(BloodSmelteryConfig.willFluidAmount));
		String desc1 = I18n.translateToLocal("desc.bloodsmeltery.willcompat");
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5f, 0.5f, 0.5f);
		minecraft.fontRenderer.drawString(desc0, ((2*width)-(minecraft.fontRenderer.getStringWidth(desc0))), -2, Color.DARK_GRAY.getRGB());
		minecraft.fontRenderer.drawString(desc1, (width-(minecraft.fontRenderer.getStringWidth(desc1)/2)), height*2-4, Color.DARK_GRAY.getRGB());
		GlStateManager.popMatrix();
	}

}
