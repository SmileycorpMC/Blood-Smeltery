package net.smileycorp.bloodsmeltery.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.smileycorp.bloodsmeltery.client.ClientEventListener;
import net.smileycorp.bloodsmeltery.common.tcon.ModContent;

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
		MinecraftForge.EVENT_BUS.register(new ModContent());
		MinecraftForge.EVENT_BUS.register(new BloodSmelteryEvents());
		ModContent.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModContent.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModContent.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModContent.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModContent.MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
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
