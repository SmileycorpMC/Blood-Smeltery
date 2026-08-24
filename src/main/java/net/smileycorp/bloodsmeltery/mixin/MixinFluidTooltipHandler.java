package net.smileycorp.bloodsmeltery.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.util.FluidUnitListDemonWill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.mantle.fluid.tooltip.FluidTooltipHandler;
import slimeknights.mantle.fluid.tooltip.FluidUnitList;

import java.util.Map;

@Mixin(value = FluidTooltipHandler.class, remap = false)
public class MixinFluidTooltipHandler {

	@Shadow private Map<ResourceLocation, FluidUnitList> unitLists;

	@Inject(at= @At(value = "INVOKE", target = "Ljava/util/Map;copyOf(Ljava/util/Map;)Ljava/util/Map;"), method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V")
	public void bloodsmeltery$apply(Map<ResourceLocation, JsonElement> splashList, ResourceManager manager, ProfilerFiller profiler, CallbackInfo ci, @Local(ordinal = 1) Map<ResourceLocation,FluidUnitList> builder ) {
		builder.put(Constants.loc("will"), new FluidUnitListDemonWill());
	}

}