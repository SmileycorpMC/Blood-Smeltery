package net.smileycorp.bloodsmeltery.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.api.compat.IDemonWillGem;
import wayoftime.bloodmagic.api.compat.IMultiWillTool;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

public class TartaricFluidCapability implements IFluidHandlerItem, ICapabilityProvider {

	protected ItemStack stack;
	protected EnumDemonWillType type;
	protected final int capacity;

	public TartaricFluidCapability(ItemStack stack) {
		this.stack=stack;
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		type = ((IMultiWillTool)gem).getCurrentType(stack);
		capacity = (int) Math.floor(gem.getMaxWill(type, stack)*BloodSmelteryConfig.willFluidAmount.get());
	}

	protected FluidStack getFluid() {
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		return DemonWillUtils.getStackForSouls(type, gem.getWill(type, stack));
	}

	private void setType(EnumDemonWillType type) {
		this.type = type;
		((ItemSoulGem)stack.getItem()).setCurrentType(type, stack);
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
		return capacity;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return stack.getFluid() == DemonWillUtils.getFluidForType(type)
				|| (getFluid().getAmount() == 0 && DemonWillUtils.isWillFluid(stack));
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		if (!isFluidValid(0, resource)) return 0;
		int amount = resource.getAmount();
		int contained = getFluid().getAmount();
		if (amount + contained > capacity) amount = capacity - contained;
		if (action.execute()) {
			EnumDemonWillType type = DemonWillUtils.getTypeForFluid(resource);
			if (this.type != type) setType(type);
			IDemonWillGem gem = (IDemonWillGem) stack.getItem();
			gem.setWill(type, stack, (amount + contained)/BloodSmelteryConfig.willFluidAmount.get());
		}
		return amount;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		FluidStack contained = getFluid();
		if (contained.getFluid() == resource.getFluid()) {
			int amount = contained.getAmount();
			int drained = contained.getAmount();
			int maxDrain = resource.getAmount();
			if (maxDrain>contained.getAmount()) {
				amount = 0;
			} else {
				amount -= maxDrain;
				drained = maxDrain;
			}
			if (action.execute()) {
				IDemonWillGem gem = (IDemonWillGem) stack.getItem();
				gem.setWill(type, stack, amount/BloodSmelteryConfig.willFluidAmount.get());
			}
			return DemonWillUtils.getStackForAmount(type, drained);
		}
		return DemonWillUtils.getStackForAmount(type, 0);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		FluidStack contained = getFluid();
		int amount = contained.getAmount();
		int drained = contained.getAmount();
		if (maxDrain>contained.getAmount()) {
			amount = 0;
		} else {
			amount -= maxDrain;
			drained = maxDrain;
		}
		if (action.execute()) {
			IDemonWillGem gem = (IDemonWillGem) stack.getItem();
			gem.setWill(type, stack, amount/BloodSmelteryConfig.willFluidAmount.get());
		}
		return DemonWillUtils.getStackForAmount(type, drained);
	}


	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
	}

	@Override
	public ItemStack getContainer() {
		return stack;
	}

}