package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.smileycorp.atlas.api.client.FluidStateMapper;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.FluidWillUtils;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import net.smileycorp.bloodsmeltery.common.bloodaresenal.BloodArsenalContent;
import slimeknights.tconstruct.library.fluid.FluidColored;
import WayofTime.bloodmagic.soul.EnumDemonWillType;

@EventBusSubscriber(modid=ModDefinitions.modid)
public class TinkersContent {
	
	public static FluidColored FLUID_RAW_WILL;
	public static FluidColored FLUID_CORROSIVE_WILL;
	public static FluidColored FLUID_DESTRUCTIVE_WILL;
	public static FluidColored FLUID_VENGEFUL_WILL;
	public static FluidColored FLUID_STEADFAST_WILL;
	
	public static FluidColored BLOOD_INFUSED_STONE;
	
	static List<BlockFluidClassic> FLUID_BLOCKS = new ArrayList<BlockFluidClassic>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		
		if (BloodSmelteryConfig.enableFluidWill) {
			FLUID_RAW_WILL = fluid("Raw_Will", 0x4EF6FF, registry);
			FluidWillUtils.mapFluid(EnumDemonWillType.DEFAULT, FLUID_RAW_WILL);
			if (!BloodSmelteryConfig.unifiedWill) {
				FLUID_CORROSIVE_WILL = fluid("Corrosive_Will", 0x60FF4F, registry);
				FLUID_DESTRUCTIVE_WILL = fluid("Destructive_Will", 0xFFCF4F, registry);
				FLUID_VENGEFUL_WILL = fluid("Vengeful_Will", 0xFF5367, registry);
				FLUID_STEADFAST_WILL = fluid("Steadfast_Will", 0xBB4FFF, registry);
				
				FluidWillUtils.mapFluid(EnumDemonWillType.CORROSIVE, FLUID_CORROSIVE_WILL);
				FluidWillUtils.mapFluid(EnumDemonWillType.DESTRUCTIVE, FLUID_DESTRUCTIVE_WILL);
				FluidWillUtils.mapFluid(EnumDemonWillType.VENGEFUL, FLUID_VENGEFUL_WILL);
				FluidWillUtils.mapFluid(EnumDemonWillType.STEADFAST, FLUID_STEADFAST_WILL);
			}
			for (Fluid will : FluidWillUtils.getWillFluids()) {
				will.setLuminosity(11).setViscosity(1000)
					.setTemperature(500).setDensity(1000)
					.setRarity(EnumRarity.RARE);
			}
		}
		
		BLOOD_INFUSED_STONE = fluid("Rune", 0x432425, registry);
		BLOOD_INFUSED_STONE.setLuminosity(0).setViscosity(8000)
			.setTemperature(1000).setDensity(2000)
			.setRarity(EnumRarity.UNCOMMON);
		
		if (Loader.isModLoaded("bloodarsenal")) BloodArsenalContent.registerBlocks(registry);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event) {
		for (BlockFluidClassic fluid_block : FLUID_BLOCKS) {
			ModelLoader.setCustomStateMapper(fluid_block, new FluidStateMapper(fluid_block.getFluid()));
		}
	}
	
	public static FluidColored fluid(String name, int color, IForgeRegistry<Block> registry) {
		return fluid(name, color, registry, FluidColored.ICON_LiquidStill, FluidColored.ICON_LiquidFlowing);
	}
	
	public static FluidColored fluid(String name, int color, IForgeRegistry<Block> registry, ResourceLocation stillIcon, ResourceLocation flowingIcon) {
		    FluidColored fluid = new FluidColored(name.toLowerCase(), color, stillIcon, flowingIcon);
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
