package net.smileycorp.bloodsmeltery.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.mantle.registration.object.FluidObject;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.soul.ItemMonsterSoul;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

public class DemonWillUtils {

	private static final Map<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> WILL_FLUIDS = new HashMap<>();
	private static final List<RegistryObject<Item>> TARTARIC_GEMS = Lists.newArrayList(BloodMagicItems.PETTY_GEM, BloodMagicItems.LESSER_GEM, BloodMagicItems.COMMON_GEM, BloodMagicItems.GREATER_GEM);

	private static final double[] DESTRUCTIVE_ATTACK_SPEED_MULTIPLIERS = {0.875, 0.813, 0.075, 0.688, 0.625, 0.625, 0.625};
	private static final double[] VENGEFUL_ATTACK_SPEED_MULTIPLIERS = {1.188, 1.25, 1.375, 1.438, 1.5, 1.5, 1.563};

	public static ItemMonsterSoul getWillItem(EnumDemonWillType type) {
		switch (type) {
		case DEFAULT:
			return (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_RAW.get();
		case CORROSIVE:
			return (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_CORROSIVE.get();
		case DESTRUCTIVE:
			return (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_DESTRUCTIVE.get();
		case VENGEFUL:
			return (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_VENGEFUL.get();
		case STEADFAST:
			return (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_STEADFAST.get();
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
		if (type == null) return new FluidStack(WILL_FLUIDS.get(EnumDemonWillType.DEFAULT).get(), 0);
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

	public static int getToolTier(double will) {
		for (int i = 0; i < ItemSentientSword.soulBracket.length; i++) {
			if (will < ItemSentientSword.soulBracket[i]) {
				return i-1;
			}
		}
		return ItemSentientSword.soulBracket.length-1;
	}

	public static EnumDemonWillType getWillFromTartaric(ItemStack stack) {
		if (stack.hasTag()) {
			CompoundNBT tag = stack.getTag();
			if (tag.contains("demonWillType")) return EnumDemonWillType.getType(tag.getString("demonWillType"));
		}
		return EnumDemonWillType.DEFAULT;
	}

	public static List<RegistryObject<Item>> getTartaricGemItems() {
		return TARTARIC_GEMS ;
	}

	public static double getBonusDamage(int tier, EnumDemonWillType type) {
		if (type == EnumDemonWillType.DESTRUCTIVE) {
			return ItemSentientSword.destructiveDamageAdded[tier];
		} else if (type == EnumDemonWillType.STEADFAST) {
			return ItemSentientSword.steadfastDamageAdded[tier];
		} else if (type == EnumDemonWillType.VENGEFUL) {
			return ItemSentientSword.vengefulDamageAdded[tier];
		}
		return ItemSentientSword.defaultDamageAdded[tier];
	}

	public static double getAttackSpeedMultiplier(int tier, EnumDemonWillType type) {
		if (type == EnumDemonWillType.DESTRUCTIVE) {
			return DESTRUCTIVE_ATTACK_SPEED_MULTIPLIERS[tier];
		} else if (type == EnumDemonWillType.VENGEFUL) {
			return VENGEFUL_ATTACK_SPEED_MULTIPLIERS[tier];
		}
		return 1;
	}

	public static ItemStack createFilledGem(EnumDemonWillType type, ItemSoulGem gem) {
		ItemStack stack = new ItemStack(gem);
		gem.setCurrentType(type, stack);
		gem.setWill(type, stack, gem.getMaxWill(type, stack));
		return stack;
	}

}
