package net.smileycorp.bloodsmeltery.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.smileycorp.bloodsmeltery.common.BloodSmeltery;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.api.compat.IDemonWillGem;
import wayoftime.bloodmagic.common.tile.TileSoulForge;

import javax.annotation.Nullable;
import java.util.Optional;

public class HellfireForgeFluidCapability implements IFluidHandler, ICapabilityProvider {

	private final TileSoulForge tile;

	public HellfireForgeFluidCapability(TileSoulForge tile) {
		this.tile = tile;
	}

	@Override
	public int getTanks() {
		IFluidHandlerItem gem = getTartaricGem();
		return gem == null ? 1 : gem.getTanks();
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		IFluidHandlerItem gem = getTartaricGem();
		return gem == null ? FluidStack.EMPTY : gem.getFluidInTank(tank);
	}

	@Override
	public int getTankCapacity(int tank) {
		IFluidHandlerItem gem = getTartaricGem();
		return gem == null ? 0 : gem.getTankCapacity(tank);
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		IFluidHandlerItem gem = getTartaricGem();
		return gem != null && gem.isFluidValid(tank, stack);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		IFluidHandlerItem gem = getTartaricGem();
		return gem == null ? 0 : gem.fill(resource, action);
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		IFluidHandlerItem gem = getTartaricGem();
		return gem == null ? FluidStack.EMPTY : gem.drain(resource, action);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		IFluidHandlerItem gem = getTartaricGem();
		return gem == null ? FluidStack.EMPTY : gem.drain(maxDrain, action);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == ForgeCapabilities.FLUID_HANDLER ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
	}

	@Nullable
	public IFluidHandlerItem getTartaricGem() {
		return tile.getItem(TileSoulForge.soulSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
	}

}