package net.smileycorp.bloodsmeltery.mixin;

import net.minecraft.world.entity.Entity;
import net.smileycorp.bloodsmeltery.common.tcon.ModContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import wayoftime.bloodmagic.common.item.BloodMagicItems;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

	@Inject(at=@At("HEAD"), method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V", cancellable = true)
	public void render(ItemStack stack, ItemTransforms.TransformType transformType, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_, CallbackInfo callback) {
		if (transformType == ItemTransforms.TransformType.GUI) {
			if (stack != null) {
				if (stack.getItem() instanceof IModifiable) {
					ToolStack tool = ToolStack.from(stack);
					if (tool != null) {
						int divinationLevel = tool.getModifierLevel(ModContent.DIVINATION.get());
						if (divinationLevel > 0) {
							ItemStack sigil = new ItemStack(divinationLevel > 1 ? BloodMagicItems.SEER_SIGIL.get() : BloodMagicItems.DIVINATION_SIGIL.get());
							render(sigil, transformType, p_115146_, p_115147_, p_115148_, p_115149_, p_115150_, getModel(sigil, null, null, 0));
						}
					}
				}
			}
		}
	}

	@Shadow
	public abstract void render(ItemStack p_115144_, ItemTransforms.TransformType p_115145_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_);

	@Shadow
	public abstract BakedModel getModel(ItemStack p_174265_, Level p_174266_, LivingEntity p_174267_, int p_174268_);

}