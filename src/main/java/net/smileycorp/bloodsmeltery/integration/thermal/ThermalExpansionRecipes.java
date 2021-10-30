package net.smileycorp.bloodsmeltery.integration.thermal;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.FluidWillUtils;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import cofh.thermalexpansion.util.managers.machine.TransposerManager;
import cofh.thermalfoundation.item.ItemMaterial;

public class ThermalExpansionRecipes {
	
	public static void loadRecipes() {
		for (EnumDemonWillType will : EnumDemonWillType.values()) {
			Fluid fluid = FluidWillUtils.getFluidForType(will); 	
			ItemStack bucket = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));
		 	if (BloodSmelteryConfig.cryotheumCraftWill) {
		 		ItemStack soul = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, will.ordinal());
			 	NBTTagCompound nbt = new NBTTagCompound();
			 	nbt.setFloat("souls", 1000/BloodSmelteryConfig.willFluidAmount);
			 	soul.setTagCompound(nbt);
		 		GameRegistry.addShapelessRecipe(ModDefinitions.getResource("demon_will_" + will.ordinal()), ModDefinitions.getResource("demon_will"), soul, 
		 				Ingredient.fromStacks(bucket), Ingredient.fromStacks(ItemMaterial.dustCryotheum));
		 	}
		 	if (BloodSmelteryConfig.fluidTransposerWill) {
		 		ItemStack soul = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, will.ordinal());
			 	NBTTagCompound nbt = new NBTTagCompound();
			 	nbt.setFloat("souls", BloodSmelteryConfig.fluidTransposerWillAmount/BloodSmelteryConfig.willFluidAmount);
			 	soul.setTagCompound(nbt);
		 		TransposerManager.addFillRecipe(BloodSmelteryConfig.fluidTransposerWillEnergy, ItemMaterial.dustCryotheum, soul, new FluidStack(fluid, BloodSmelteryConfig.fluidTransposerWillAmount), false);
		 	}
		 	if (BloodSmelteryConfig.magmaCrucibleCrystals) {
		 		CrucibleManager.addRecipe(BloodSmelteryConfig.magmaCrucibleCrystalEnergy, will.getStack(), new FluidStack(fluid, 
		 				BloodSmelteryConfig.crystalMeltMultiplier*BloodSmelteryConfig.willFluidAmount));
		 	}
		}
	}
}
