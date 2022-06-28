package net.smileycorp.bloodsmeltery.integration.jei;

import net.smileycorp.bloodsmeltery.common.tcon.MeltingWillRecipe;

public class MeltingWillRecipeWrapper /*extends SmeltingRecipeWrapper*/  {

	public MeltingWillRecipeWrapper(MeltingWillRecipe recipe) {
		//super(recipe);
	}

	/*@Override
	public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
		super.drawInfo(minecraft, width, height, mouseX, mouseY);
		String desc0 = I18n.translateToLocal("desc.bloodsmeltery.willmelting").replace("%s", String.valueOf(BloodSmelteryConfig.willFluidAmount));
		String desc1 = I18n.translateToLocal("desc.bloodsmeltery.willcompat");
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5f, 0.5f, 0.5f);
		minecraft.fontRenderer.drawString(desc0, (width-(minecraft.fontRenderer.getStringWidth(desc0)/2)), 0, Color.DARK_GRAY.getRGB());
		minecraft.fontRenderer.drawString(desc1, (width-(minecraft.fontRenderer.getStringWidth(desc1)/2)), height*2-6, Color.DARK_GRAY.getRGB());
		GlStateManager.popMatrix();
	}*/

}
