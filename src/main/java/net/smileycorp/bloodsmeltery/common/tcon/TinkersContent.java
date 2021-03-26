package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.smileycorp.bloodsmeltery.client.BlockFluidMapper;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.fluid.FluidColored;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.tools.TinkerTools;

@EventBusSubscriber(modid=ModDefinitions.modid)
public class TinkersContent {
	
	public static FluidColored FLUID_RAW_WILL;
	public static FluidColored FLUID_CORROSIVE_WILL;
	public static FluidColored FLUID_DESTRUCTIVE_WILL;
	public static FluidColored FLUID_VENGEFUL_WILL;
	public static FluidColored FLUID_STEADFAST_WILL;
	
	public static FluidColored BLOOD_INFUSED_STONE;
	
	public static FluidColored[] FLUID_WILLS = {};
	
	public static ModifierAlive ALIVE_MODIFIER;
	
	static List<BlockFluidClassic> FLUID_BLOCKS = new ArrayList<BlockFluidClassic>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		
		if (BloodSmelteryConfig.enableFluidWill) {
			FLUID_RAW_WILL = fluid("Raw_Will", 0x4EF6FF, registry);
			FLUID_WILLS = new FluidColored[]{FLUID_RAW_WILL};
			if (!BloodSmelteryConfig.unifiedWill) {
				FLUID_CORROSIVE_WILL = fluid("Corrosive_Will", 0x60FF4F, registry);
				FLUID_DESTRUCTIVE_WILL = fluid("Destructive_Will", 0xFFCF4F, registry);
				FLUID_VENGEFUL_WILL = fluid("Vengeful_Will", 0xFF5367, registry);
				FLUID_STEADFAST_WILL = fluid("Steadfast_Will", 0xBB4FFF, registry);
			
				FLUID_WILLS = new FluidColored[]{FLUID_RAW_WILL, FLUID_CORROSIVE_WILL, FLUID_DESTRUCTIVE_WILL, FLUID_VENGEFUL_WILL, FLUID_STEADFAST_WILL};
			}
			for (FluidColored will : FLUID_WILLS) {
				will.setLuminosity(11).setViscosity(1000)
					.setTemperature(500).setDensity(1000)
					.setRarity(EnumRarity.RARE);
			}
		}
		
		BLOOD_INFUSED_STONE = fluid("Rune", 0x432425, registry);
		BLOOD_INFUSED_STONE.setLuminosity(0).setViscosity(8000)
			.setTemperature(1000).setDensity(2000)
			.setRarity(EnumRarity.UNCOMMON);
	}
	
	@SubscribeEvent
	public static void registerModifiers(Register<Item> event) {
		if (TConstruct.pulseManager.isPulseLoaded(TinkerTools.PulseId)) {
			//ALIVE_MODIFIER = new ModifierAlive();
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event) {
		for (BlockFluidClassic fluid_block : FLUID_BLOCKS) {
			ModelLoader.setCustomStateMapper(fluid_block, new BlockFluidMapper());
		}
	}
	
	static FluidColored fluid(String name, int color, IForgeRegistry<Block> registry) {
		    FluidColored fluid = new FluidColored(name.toLowerCase(), color, FluidColored.ICON_LiquidStill, FluidColored.ICON_LiquidFlowing);
		    fluid.setUnlocalizedName(ModDefinitions.getName(name));
		    FluidRegistry.registerFluid(fluid);
		    FluidRegistry.addBucketForFluid(fluid);
		    BlockFluidClassic fluid_block = new BlockFluidClassic(fluid, Material.LAVA);
		    fluid_block.setRegistryName(ModDefinitions.getResource(name));
		    fluid_block.setUnlocalizedName(ModDefinitions.getName(name));
		    registry.register(fluid_block);
		    FLUID_BLOCKS.add(fluid_block);
		    return fluid;
	  }

}
