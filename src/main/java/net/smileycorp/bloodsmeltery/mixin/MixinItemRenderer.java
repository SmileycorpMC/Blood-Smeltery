package net.smileycorp.bloodsmeltery.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smileycorp.bloodsmeltery.common.tcon.ModContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import wayoftime.bloodmagic.common.item.BloodMagicItems;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
	
	@Shadow
	public abstract BakedModel getModel(ItemStack p_174265_, Level p_174266_, LivingEntity p_174267_, int p_174268_);

	@Shadow public abstract void render(ItemStack p_115144_, ItemDisplayContext p_270188_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_);

	@Inject(at=@At("HEAD"), method = "render")
	public void bloodsmeltery$render(ItemStack stack, ItemDisplayContext ctx, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_, CallbackInfo callback) {
		if (ctx != ItemDisplayContext.GUI || stack == null) return;
		if (!(stack.getItem() instanceof IModifiable)) return;
		ToolStack tool = ToolStack.from(stack);
		if (tool == null) return;
		int divinationLevel = tool.getModifierLevel(ModContent.DIVINATION.get());
		if (divinationLevel < 0) return;
		ItemStack sigil = new ItemStack(divinationLevel > 1 ? BloodMagicItems.SEER_SIGIL.get() : BloodMagicItems.DIVINATION_SIGIL.get());
		render(sigil, ctx, p_115146_, p_115147_, p_115148_, p_115149_, p_115150_, getModel(sigil, null, null, 0));;
	}

}