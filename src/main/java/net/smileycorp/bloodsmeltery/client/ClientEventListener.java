package net.smileycorp.bloodsmeltery.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.smileycorp.bloodsmeltery.client.modifiers.SentienceModifierModel;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.will.DemonWillUtils;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager.ModifierModelRegistrationEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID, bus = Bus.MOD)
public class ClientEventListener {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		for (FluidObject<ForgeFlowingFluid> fluid : DemonWillUtils.getWillFluids()) {
			ItemBlockRenderTypes.setRenderLayer(fluid.get().getSource(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(fluid.get().getFlowing(), RenderType.translucent());
		}
	}

	@SubscribeEvent
	public static void registerModifierModels(ModifierModelRegistrationEvent event) {
		event.registerModel(Constants.loc("sentience"), SentienceModifierModel.UNBAKED_INSTANCE);
	}

}
