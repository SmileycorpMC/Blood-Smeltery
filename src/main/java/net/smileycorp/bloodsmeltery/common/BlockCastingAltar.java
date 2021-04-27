package net.smileycorp.bloodsmeltery.common;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.IFaucetDepth;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCastingAltar extends Block implements IFaucetDepth {

	protected BlockCastingAltar() {
		super(Material.ROCK);
	    setHardness(3F);
	    setResistance(20F);
	    setCreativeTab(TinkerRegistry.tabSmeltery);
	}
	
	@Override
	  public boolean hasTileEntity(IBlockState state) {
	    return true;
	  }

	@Override
	public float getFlowDepth(World world, BlockPos pos, IBlockState state) {
		return 0.125f;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCastingAltar();
	}

}
