package net.smileycorp.bloodsmeltery.common.thermal;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import slimeknights.tconstruct.library.fluid.FluidColored;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import net.smileycorp.bloodsmeltery.common.tcon.TinkersContent;
import cofh.thermalexpansion.util.managers.machine.TransposerManager;
import cofh.thermalfoundation.item.ItemMaterial;

public class ThermalExpansionRecipes {
	
	public static void loadRecipes() {
		for (int i = 0; i< TinkersContent.FLUID_WILLS.length; i++) {
			FluidColored fluid = TinkersContent.FLUID_WILLS[i]; 	
			ItemStack bucket = ForgeModContainer.getInstance().universalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid);
		 	if (BloodSmelteryConfig.cryotheumCraftWill) {
		 		ItemStack soul = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, i);
			 	NBTTagCompound nbt = new NBTTagCompound();
			 	nbt.setFloat("souls", 1000/BloodSmelteryConfig.willFluidAmount);
			 	soul.setTagCompound(nbt);
		 		GameRegistry.addShapelessRecipe(ModDefinitions.getResource("demon_will_"+i), ModDefinitions.getResource("demon_will"), soul, Ingredient.fromStacks(bucket), Ingredient.fromStacks(ItemMaterial.dustCryotheum));
		 	}
		 	if (BloodSmelteryConfig.fluidTransposerWill) {
		 		ItemStack soul = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, i);
			 	NBTTagCompound nbt = new NBTTagCompound();
			 	nbt.setFloat("souls", BloodSmelteryConfig.fluidTransposerWillAmount/BloodSmelteryConfig.willFluidAmount);
			 	soul.setTagCompound(nbt);
		 		TransposerManager.addFillRecipe(BloodSmelteryConfig.fluidTransposerWillEnergy, ItemMaterial.dustCryotheum, soul, new FluidStack(fluid, BloodSmelteryConfig.fluidTransposerWillAmount), false);
		 	}
		}
	}
}
