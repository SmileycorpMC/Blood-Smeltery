package net.smileycorp.bloodsmeltery.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.smileycorp.bloodsmeltery.common.Constants;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager.ModifierModelRegistrationEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID, bus = Bus.MOD)
public class ClientEventListener {

	@SubscribeEvent
	public static void registerModifierModels(ModifierModelRegistrationEvent event) {
		event.registerModel(Constants.loc("sentient"), SentientModifierModel.UNBAKED_INSTANCE);
	}

}
