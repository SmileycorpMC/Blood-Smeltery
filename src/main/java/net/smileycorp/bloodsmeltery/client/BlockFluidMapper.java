package net.smileycorp.bloodsmeltery.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;

public class BlockFluidMapper extends StateMapperBase {

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		String name = state.getBlock().getRegistryName().getResourcePath();
        return new ModelResourceLocation(ModDefinitions.getResource("fluid_block"), name);
    }

}
