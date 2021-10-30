package net.smileycorp.bloodsmeltery.common;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import WayofTime.bloodmagic.iface.IMultiWillTool;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWillGem;

public class TartaricFluidCapability implements IFluidHandlerItem, ICapabilityProvider {
	
	private ItemStack stack;
	EnumDemonWillType type;
	
	public TartaricFluidCapability(ItemStack stack) {
		this.stack=stack;
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		type = ((IMultiWillTool)gem).getCurrentType(stack);
	}
	
	private int getStackCapacity() {
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		return (int) Math.floor(gem.getMaxWill(type, stack)*BloodSmelteryConfig.willFluidAmount);
	}
	
	protected FluidStack getFluid() {
		IDemonWillGem gem = (IDemonWillGem) stack.getItem();
		return FluidWillUtils.getStackForSouls(type , gem.getWill(type, stack));
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { new IFluidTankProperties() {

			@Override
			public FluidStack getContents() {
				return getFluid();
			}

			@Override
			public int getCapacity() {
				return getStackCapacity();
			}

			@Override
			public boolean canFill() {
				return true;
			}

			@Override
			public boolean canDrain() {
				return true;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return FluidWillUtils.getFluidForType(type) == fluidStack.getFluid();
			}

			@Override
			public boolean canDrainFluidType(FluidStack fluidStack) {
				return FluidWillUtils.getFluidForType(type) == fluidStack.getFluid();
			}
		}};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		FluidStack contained = getFluid();
		int amount = resource.amount + contained.amount;
		if (amount <= getStackCapacity()) {
			if (contained.amount == 0) {
				type = FluidWillUtils.getTypeForFluid(resource);
				contained = getFluid();
			}
			if (contained.getFluid() == resource.getFluid()) {
				if (doFill) {
					IDemonWillGem gem = (IDemonWillGem) stack.getItem();
					gem.setWill(type, stack, amount/BloodSmelteryConfig.willFluidAmount);
				}
				return amount;
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		FluidStack contained = getFluid();
		if (contained.getFluid() == resource.getFluid()) {
			int amount = contained.amount;
			int drained = contained.amount;
			int maxDrain = resource.amount;
			if (maxDrain>contained.amount) {
				amount = 0;
			} else {
				amount -= maxDrain;
				drained = maxDrain;
			}
			if (doDrain) {
				IDemonWillGem gem = (IDemonWillGem) stack.getItem();
				gem.setWill(type, stack, amount/BloodSmelteryConfig.willFluidAmount);
			}
			return FluidWillUtils.getStackForAmount(type, drained);
		}
		return FluidWillUtils.getStackForAmount(type, 0);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		FluidStack contained = getFluid();
		int amount = contained.amount;
		int drained = contained.amount;
		if (maxDrain>contained.amount) {
			amount = 0;
		} else {
			amount -= maxDrain;
			drained = maxDrain;
		}
		if (doDrain) {
			IDemonWillGem gem = (IDemonWillGem) stack.getItem();
			gem.setWill(type, stack, amount/BloodSmelteryConfig.willFluidAmount);
		}
		return FluidWillUtils.getStackForAmount(type, drained);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return !hasCapability(capability, facing) ? null : CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
	}

	@Override
	public ItemStack getContainer() {
		return stack;
	}

}
