package net.smileycorp.bloodsmeltery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.bloodsmeltery.common.tcon.ModContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import wayoftime.bloodmagic.client.hud.element.ElementDivinedInformation;

@Mixin(value = ElementDivinedInformation.class, remap = false)
public class MixinElementDivinedInformation {

	@Inject(at= @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;iterator()Ljava/util/Iterator;"), method = "shouldRender(Lnet/minecraft/client/Minecraft;)Z")
	public void bloodsmeltery$shouldRender$iterator(Minecraft mc, CallbackInfoReturnable<Boolean> callback, @Local Player player, @Local(ordinal = 0) LocalBooleanRef hasDivination, @Local(ordinal = 1) LocalBooleanRef hasSeer) {
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			if (!(stack.getItem() instanceof IModifiable)) continue;
			int divination = ToolStack.from(stack).getModifierLevel(ModContent.DIVINATION.get());
			if (divination > 1) {
				hasSeer.set(true);
				return;
			}
			else if (divination > 0) hasDivination.set(true);
		}
	}

}