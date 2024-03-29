package net.smileycorp.bloodsmeltery.mixin;

import net.smileycorp.bloodsmeltery.common.tcon.ModContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import wayoftime.bloodmagic.client.hud.element.ElementDivinedInformation;
import wayoftime.bloodmagic.client.hud.element.ElementTileInformation;

@Mixin(value = ElementDivinedInformation.class, remap = false)
public abstract class MixinElementDivinedInformation<T extends BlockEntity> extends ElementTileInformation<T> {

	@Shadow(remap = false)
	private boolean simple;

	public MixinElementDivinedInformation(int width, int lines, Class<T> tileClass) {
		super(width, lines, tileClass);
	}

	@Inject(at=@At("HEAD"), method = "shouldRender(Lnet/minecraft/client/Minecraft;)Z", cancellable = true)
	public void shouldRender(Minecraft mc, CallbackInfoReturnable<Boolean> callback) {
		LocalPlayer player = mc.player;
		if (player == null) return;
		for (InteractionHand hand : InteractionHand.values()) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack == null |!(stack.getItem() instanceof IModifiable)) continue;
			ToolStack tool = ToolStack.from(stack);
			if (tool == null) continue;
			int divinationLevel = tool.getModifierLevel(ModContent.DIVINATION.get());
			if ((simple && divinationLevel > 0) || divinationLevel > 1 && super.shouldRender(mc)) {
				callback.setReturnValue(true);
				callback.cancel();
				return;
			}
		}
	}

}