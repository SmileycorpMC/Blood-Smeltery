package net.smileycorp.bloodsmeltery.common.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.smileycorp.bloodsmeltery.common.BloodSmeltery;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.tile.TileSoulForge;

public class HellfireForgeFluidCapability implements IFluidHandler, ICapabilityProvider {

	private final TileSoulForge tile;

	public HellfireForgeFluidCapability(TileSoulForge tile) {
		this.tile = tile;
	}

	protected FluidStack getFluid() {
		for (int i = 0; i < 5; i++) {
			BloodSmeltery.logInfo(((TileSoulForge) tile).getItem(i));
		}
		return FluidStack.EMPTY;
	}

	private void setType(EnumDemonWillType type) {

	}

	@Override
	public int getTanks() {
		return 1;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		return 1000;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		for (int i = 0; i < 5; i++) {
			BloodSmeltery.logInfo(tile.getItem(i));
		}
		return false;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		return FluidStack.EMPTY;
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return FluidStack.EMPTY;
	}


	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
	}

}