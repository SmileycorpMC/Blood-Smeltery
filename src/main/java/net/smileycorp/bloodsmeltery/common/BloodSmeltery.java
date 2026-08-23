package net.smileycorp.bloodsmeltery.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.smileycorp.bloodsmeltery.client.ClientEventListener;
import net.smileycorp.bloodsmeltery.integration.thermal.ThermalIntegration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BloodSmeltery {

	private static Logger logger = Logger.getLogger(Constants.NAME);

	public BloodSmeltery() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BloodSmelteryConfig.config);
	}

	@SubscribeEvent
	public static void constructMod(FMLConstructModEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(new BloodSmelteryEvents());
		BloodSmelteryContent.ITEMS.register(bus);
		if (ModList.get().isLoaded("thermal")) ThermalIntegration.ITEMS.register(bus);
		BloodSmelteryContent.BLOCKS.register(bus);
		BloodSmelteryContent.FLUIDS.register(bus);
		BloodSmelteryContent.RECIPE_SERIALIZERS.register(bus);
		BloodSmelteryContent.MODIFIERS.register(bus);
		BloodSmelteryContent.TABS.register(bus);
		if (BloodSmelteryConfig.enableFluidWill.get()) BloodSmelteryContent.initWillFluids();
		BloodSmelteryContent.initHellforgedFluids();
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event){
		MinecraftForge.EVENT_BUS.register(new ClientEventListener());
	}

	public static void logInfo(Object message) {
		logger.info(message.toString());
	}

	public static void logError(Object message, Exception e) {
		logger.log(Level.WARNING, message.toString());
		e.printStackTrace();
	}

}
