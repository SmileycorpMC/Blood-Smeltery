package net.smileycorp.bloodsmeltery.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.object.FluidObject;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.BloodMagicItems;

public class DemonWillUtils {

	private static Map<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> WILL_FLUIDS = new HashMap<>();

	public static Item getWillItem(EnumDemonWillType type) {
		switch (type) {
		case DEFAULT:
			return BloodMagicItems.MONSTER_SOUL_RAW.get();
		case CORROSIVE:
			return BloodMagicItems.MONSTER_SOUL_CORROSIVE.get();
		case DESTRUCTIVE:
			return BloodMagicItems.MONSTER_SOUL_DESTRUCTIVE.get();
		case VENGEFUL:
			return BloodMagicItems.MONSTER_SOUL_VENGEFUL.get();
		case STEADFAST:
			return BloodMagicItems.MONSTER_SOUL_STEADFAST.get();
		}
		return null;
	}

	public static int getColour(EnumDemonWillType type) {
		switch (type) {
		case DEFAULT:
			return 0x4EF6FF;
		case CORROSIVE:
			return 0x60FF4F;
		case DESTRUCTIVE:
			return 0xFFCF4F;
		case VENGEFUL:
			return 0xFF5367;
		case STEADFAST:
			return 0xBB4FFF;
		}
		return 0xFFFFFF;
	}

	public static EnumDemonWillType getTypeForFluid(FluidStack fluidStack) {
		return getTypeForFluid(fluidStack.getFluid());
	}

	public static EnumDemonWillType getTypeForFluid(Fluid fluid) {
		for (Entry<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> entry : WILL_FLUIDS.entrySet()) {
			if (entry.getValue().get() == fluid) return entry.getKey();
		}
		return EnumDemonWillType.DEFAULT;
	}

	public static Fluid getFluidForType(EnumDemonWillType type) {
		if (!WILL_FLUIDS.containsKey(type)) return WILL_FLUIDS.get(EnumDemonWillType.DEFAULT).get();
		return WILL_FLUIDS.get(type).get();
	}

	public static FluidStack getStackForSouls(EnumDemonWillType type, double amount) {
		return getStackForAmount(type, (int) Math.round(amount*BloodSmelteryConfig.willFluidAmount.get()));
	}

	public static FluidStack getStackForAmount(EnumDemonWillType type, int amount) {
		return new FluidStack(getFluidForType(type), amount);
	}

	public static Collection<Fluid> getWillFluids() {
		List<Fluid> fluids = new ArrayList<>();
		for (FluidObject<ForgeFlowingFluid> fluid : WILL_FLUIDS.values()) fluids.add(fluid.get());
		return fluids;
	}

	public static boolean isWillFluid(FluidStack fluidStack) {
		return isWillFluid(fluidStack.getFluid());
	}

	public static boolean isWillFluid(Fluid fluid) {
		return getWillFluids().contains(fluid);
	}

	public static void registerWillFluid(EnumDemonWillType type, FluidObject<ForgeFlowingFluid> fluid) {
		if (!WILL_FLUIDS.containsKey(type)) WILL_FLUIDS.put(type, fluid);
	}

}
