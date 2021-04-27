package net.smileycorp.bloodsmeltery.common.bloodaresenal;

import slimeknights.tconstruct.library.fluid.FluidColored;
import slimeknights.tconstruct.library.fluid.FluidMolten;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;

import net.minecraftforge.registries.IForgeRegistry;

import net.smileycorp.bloodsmeltery.common.tcon.TinkersContent;

public class BloodArsenalContent {
	public static FluidColored BLOOD_INFUSED_GLASS;

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		BLOOD_INFUSED_GLASS = TinkersContent.fluid("Blood_Glass", 0x943936, registry, FluidMolten.ICON_MetalStill, FluidMolten.ICON_MetalFlowing);
		BLOOD_INFUSED_GLASS.setLuminosity(0).setViscosity(4000)
		.setTemperature(1000).setDensity(2000)
		.setRarity(EnumRarity.UNCOMMON);
	}
	
	
}
