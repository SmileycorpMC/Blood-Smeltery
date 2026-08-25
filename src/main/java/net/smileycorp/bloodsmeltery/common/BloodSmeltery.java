package net.smileycorp.bloodsmeltery.common;

import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.smileycorp.bloodsmeltery.common.will.DemonWillUtils;
import net.smileycorp.bloodsmeltery.integration.thermal.ThermalIntegration;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BloodSmeltery {

	private static final Logger logger = Logger.getLogger(Constants.NAME);

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
	public static void loadComplete(FMLLoadCompleteEvent event) {
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
				DemonWillUtils.getFluidForType(EnumDemonWillType.DEFAULT).getType(), BloodMagicBlocks.DUNGEON_STONE.get().defaultBlockState()));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
				DemonWillUtils.getFluidForType(EnumDemonWillType.CORROSIVE).getType(), BloodMagicBlocks.CORROSIVE_DUNGEON_STONE.get().defaultBlockState()));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
				DemonWillUtils.getFluidForType(EnumDemonWillType.DESTRUCTIVE).getType(), BloodMagicBlocks.DESTRUCTIVE_DUNGEON_STONE.get().defaultBlockState()));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
				DemonWillUtils.getFluidForType(EnumDemonWillType.VENGEFUL).getType(), BloodMagicBlocks.VENGEFUL_DUNGEON_STONE.get().defaultBlockState()));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
				DemonWillUtils.getFluidForType(EnumDemonWillType.STEADFAST).getType(), BloodMagicBlocks.STEADFAST_DUNGEON_STONE.get().defaultBlockState()));
	}

	public static void logInfo(Object message) {
		logger.info(message.toString());
	}

	public static void logError(Object message, Exception e) {
		logger.log(Level.WARNING, message.toString());
		e.printStackTrace();
	}

}
