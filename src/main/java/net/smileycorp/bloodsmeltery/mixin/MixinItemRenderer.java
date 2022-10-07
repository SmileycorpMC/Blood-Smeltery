package net.smileycorp.bloodsmeltery.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.smileycorp.bloodsmeltery.common.tcon.TinkersContent;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import wayoftime.bloodmagic.common.item.BloodMagicItems;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

	@Inject(at=@At("HEAD"), method = "func_229111_a_(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;ZLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IILnet/minecraft/client/renderer/model/IBakedModel;)V", cancellable = true, remap=false)
	public void func_229111_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, boolean p_229111_3_, MatrixStack p_229111_4_, IRenderTypeBuffer p_229111_5_, int p_229111_6_, int p_229111_7_, IBakedModel p_229111_8_, CallbackInfo callback) {
		if (transformType == ItemCameraTransforms.TransformType.GUI) {
			if (stack != null) {
				if (stack.getItem() instanceof IModifiable) {
					ToolStack tool = ToolStack.from(stack);
					if (tool != null) {
						int divinationLevel = tool.getModifierLevel(TinkersContent.DIVINATION.get());
						if (divinationLevel > 0) {
							ItemStack sigil = new ItemStack(divinationLevel > 1 ? BloodMagicItems.SEER_SIGIL.get() : BloodMagicItems.DIVINATION_SIGIL.get());
							func_229111_a_(sigil, transformType,p_229111_3_, p_229111_4_, p_229111_5_, p_229111_6_, p_229111_7_, func_184393_a(sigil, (World)null, (LivingEntity)null));
						}
					}
				}
			}
		}
	}

	@Shadow
	public abstract void func_229111_a_(ItemStack stack, ItemCameraTransforms.TransformType p_229111_2_, boolean p_229111_3_, MatrixStack p_229111_4_, IRenderTypeBuffer p_229111_5_, int p_229111_6_, int p_229111_7_, IBakedModel p_229111_8_);

	@Shadow
	public abstract IBakedModel func_184393_a(ItemStack p_184393_1_, World p_184393_2_, LivingEntity p_184393_3_);

}