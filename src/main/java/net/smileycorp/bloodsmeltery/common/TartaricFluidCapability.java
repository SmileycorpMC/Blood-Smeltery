package net.smileycorp.bloodsmeltery.common;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.api.compat.IDemonWillGem;
import wayoftime.bloodmagic.api.compat.IMultiWillTool;

public class TartaricFluidCapability implements IFluidHandlerItem, ICapabilityProvider {

	protected ItemStack stack;
	protected EnumDemonWillType type;

	public TartaricFluidCapability(ItemStack stack) {
		this.stack=stack;
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		type = ((IMultiWillTool)gem).getCurrentType(stack);
	}

	private int getStackCapacity() {
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		return (int) Math.floor(gem.getMaxWill(type, stack)*BloodSmelteryConfig.willFluidAmount.get());
	}

	protected FluidStack getFluid() {
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		return DemonWillUtils.getStackForSouls(type , gem.getWill(type, stack));
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
		return getStackCapacity();
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return stack.getFluid() == DemonWillUtils.getFluidForType(type);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		FluidStack contained = getFluid();
		int amount = resource.getAmount() + contained.getAmount();
		if (amount <= getStackCapacity()) {
			if (contained.getAmount() == 0) {
				type = DemonWillUtils.getTypeForFluid(resource);
				contained = getFluid();
			}
			if (contained.getFluid() == resource.getFluid()) {
				if (action.execute()) {
					IDemonWillGem gem = (IDemonWillGem) stack.getItem();
					gem.setWill(type, stack, amount/BloodSmelteryConfig.willFluidAmount.get());
				}
				return amount;
			}
		}
		return 0;
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
