package net.smileycorp.bloodsmeltery.common.thermal;

import WayofTime.bloodmagic.iface.IMultiWillTool;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWillGem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import cofh.thermalexpansion.util.managers.machine.TransposerManager.TransposerRecipe;

public class TransposerRecipeFillTartaric extends TransposerRecipe {
	
	protected final EnumDemonWillType type;
	
	public TransposerRecipeFillTartaric(ItemStack stack, FluidStack fluid, int energy, int chance, EnumDemonWillType type) {
		super(stack, stack, fluid, energy, chance);
		this.type=type;
	}
	
	public ItemStack getOutput() {
		ItemStack input = getInput();
		if (((IMultiWillTool)input.getItem()).getCurrentType(input) == type) {
			ItemStack output = input.copy();
			NBTTagCompound nbt = input.getTagCompound().copy();
			if (nbt == null) nbt = new NBTTagCompound();
			float added = 1000/BloodSmelteryConfig.fluidTransposerWillAmount;
			if (nbt.hasKey("souls")) {
				nbt.setFloat("souls", nbt.getFloat("souls") + added);
			} else {
				nbt.setFloat("souls", added);
			}
			output.setTagCompound(nbt);
			return output;
		}
		return ItemStack.EMPTY;
	}
	
	

}
