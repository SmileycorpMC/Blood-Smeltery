package net.smileycorp.bloodsmeltery.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.smileycorp.bloodsmeltery.common.tcon.TinkersContent;
import net.smileycorp.bloodsmeltery.common.tcon.TinkersRecipes;
import net.smileycorp.bloodsmeltery.integration.thermal.ThermalExpansionRecipes;

@Mod(modid = ModDefinitions.modid, name=ModDefinitions.name, version = ModDefinitions.version, dependencies = ModDefinitions.dependencies)
public class BloodSmeltery {
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		 BloodSmelteryConfig.config = new Configuration(event.getSuggestedConfigurationFile());
		 BloodSmelteryConfig.syncConfig();
		 MinecraftForge.EVENT_BUS.register(new TinkersContent());
		 MinecraftForge.EVENT_BUS.register(new BloodSmelteryEvents());
	 }
	 @EventHandler
	 public void init(FMLInitializationEvent event){
		 TinkersRecipes.loadRecipes();
		 if (Loader.isModLoaded("thermalexpansion")) ThermalExpansionRecipes.loadRecipes();
	 }
	 
	 @EventHandler
	 public void postInit(FMLInitializationEvent event){
		 TinkersRecipes.loadLateRecipes();
	 }
}
