package net.smileycorp.bloodsmeltery.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.smileycorp.bloodsmeltery.client.ClientEventListener;

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
		ModContent.ITEMS.register(bus);
		ModContent.BLOCKS.register(bus);
		ModContent.FLUIDS.register(bus);
		ModContent.RECIPE_SERIALIZERS.register(bus);
		ModContent.MODIFIERS.register(bus);
		ModContent.TABS.register(bus);
		if (BloodSmelteryConfig.enableFluidWill.get()) ModContent.initWillFluids();
		ModContent.initHellforgedFluids();
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
