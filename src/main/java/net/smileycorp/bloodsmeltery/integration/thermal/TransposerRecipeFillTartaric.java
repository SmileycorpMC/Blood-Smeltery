package net.smileycorp.bloodsmeltery.integration.thermal;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import WayofTime.bloodmagic.iface.IMultiWillTool;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import cofh.thermalexpansion.util.managers.machine.TransposerManager.TransposerRecipe;

//unfinished, needs reworking to make it work properly, might not be possible to finish
public class TransposerRecipeFillTartaric extends TransposerRecipe {
	
	protected final EnumDemonWillType type;
	
	public TransposerRecipeFillTartaric(ItemStack stack, FluidStack fluid, int energy, int chance, EnumDemonWillType type) {
		super(stack, stack, fluid, energy, chance);
		this.type=type;
	}
	
	@Override
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
