package net.smileycorp.bloodsmeltery.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager.ModifierModelRegistrationEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = ModDefinitions.MODID, bus = Bus.MOD)
public class ClientEventListener {

	@SubscribeEvent
	public static void registerModifierModels(ModifierModelRegistrationEvent event) {
		event.registerModel(ModDefinitions.getResource("sentient"), SentientModifierModel.UNBAKED_INSTANCE);
	}

}
