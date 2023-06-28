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

@Mod(Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BloodSmeltery {

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


}
