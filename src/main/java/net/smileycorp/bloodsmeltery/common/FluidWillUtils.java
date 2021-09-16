package net.smileycorp.bloodsmeltery.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.tcon.TinkersContent;
import WayofTime.bloodmagic.soul.EnumDemonWillType;

public class FluidWillUtils {
	
	private static Map<EnumDemonWillType, Fluid> WILL_FLUIDS = new HashMap<EnumDemonWillType, Fluid>();

	public static EnumDemonWillType getTypeForFluid(FluidStack fluidStack) {
		return getTypeForFluid(fluidStack.getFluid());
	}
	
	public static EnumDemonWillType getTypeForFluid(Fluid fluid) {
		for (Entry<EnumDemonWillType, Fluid> entry : WILL_FLUIDS.entrySet()) {
			if (entry.getValue() == fluid) return entry.getKey();
		}
		return EnumDemonWillType.DEFAULT;
	}
	
	public static Fluid getFluidForType(EnumDemonWillType type) {
		if (!WILL_FLUIDS.containsKey(type)) return TinkersContent.FLUID_RAW_WILL;
		return WILL_FLUIDS.get(type);
	}

	public static FluidStack getStackForSouls(EnumDemonWillType type, double amount) {
		return getStackForAmount(type, (int) Math.round(amount*BloodSmelteryConfig.willFluidAmount));
	}
	
	public static FluidStack getStackForAmount(EnumDemonWillType type, int amount) {
		return new FluidStack(getFluidForType(type), amount);
	}

	public static Collection<Fluid> getWillFluids() {
		return WILL_FLUIDS.values();
	}

	public static boolean isWillFluid(FluidStack fluidStack) {
		return isWillFluid(fluidStack.getFluid());
	}

	public static boolean isWillFluid(Fluid fluid) {
		return getWillFluids().contains(fluid);
	}

	public static void mapFluid(EnumDemonWillType type, Fluid fluid) {
		if (!WILL_FLUIDS.containsKey(type)) WILL_FLUIDS.put(type, fluid);
	}

}
