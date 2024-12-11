package net.smileycorp.bloodsmeltery.common.util;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.mantle.registration.object.FluidObject;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.soul.ItemMonsterSoul;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class DemonWillUtils {

	private static final Map<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> WILL_FLUIDS = new HashMap<>();
	private static final List<RegistryObject<Item>> TARTARIC_GEMS = Lists.newArrayList(BloodMagicItems.PETTY_GEM, BloodMagicItems.LESSER_GEM, BloodMagicItems.COMMON_GEM, BloodMagicItems.GREATER_GEM);

	private static final double[] DESTRUCTIVE_ATTACK_SPEED_MULTIPLIERS = {0.875, 0.813, 0.075, 0.688, 0.625, 0.625, 0.625};
	private static final double[] VENGEFUL_ATTACK_SPEED_MULTIPLIERS = {1.188, 1.25, 1.375, 1.438, 1.5, 1.5, 1.563};

	public static ItemMonsterSoul getWillItem(EnumDemonWillType type) {
		return switch (type) {
			case DEFAULT -> (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_RAW.get();
			case CORROSIVE -> (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_CORROSIVE.get();
			case DESTRUCTIVE -> (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_DESTRUCTIVE.get();
			case VENGEFUL -> (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_VENGEFUL.get();
			case STEADFAST -> (ItemMonsterSoul) BloodMagicItems.MONSTER_SOUL_STEADFAST.get();
		};
	}

	public static int getColour(EnumDemonWillType type) {
		return switch (type) {
			case DEFAULT -> 0x4EF6FF;
			case CORROSIVE -> 0x60FF4F;
			case DESTRUCTIVE -> 0xFFCF4F;
			case VENGEFUL -> 0xFF5367;
			case STEADFAST -> 0xBB4FFF;
		};
	}

	public static EnumDemonWillType getTypeForFluid(FluidStack fluidStack) {
		return getTypeForFluid(fluidStack.getFluid());
	}

	public static EnumDemonWillType getTypeForFluid(Fluid fluid) {
		for (Entry<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> entry : WILL_FLUIDS.entrySet()) if (entry.getValue().get() == fluid) return entry.getKey();
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
		List<Fluid> fluids = Lists.newArrayList();
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
		for (int i = 0; i < ItemSentientSword.soulBracket.length; i++) if (will < ItemSentientSword.soulBracket[i]) return i - 1;
		return ItemSentientSword.soulBracket.length - 1;
	}

	public static EnumDemonWillType getWillFromTartaric(ItemStack stack) {
		if (!stack.hasTag()) return EnumDemonWillType.DEFAULT;
		CompoundTag tag = stack.getTag();
		return tag.contains("demonWillType") ? EnumDemonWillType.getType(tag.getString("demonWillType")) : EnumDemonWillType.DEFAULT;
	}

	public static List<ItemSoulGem> getTartaricGemItems() {
		return TARTARIC_GEMS.stream().map(item -> (ItemSoulGem)item.get()).collect(Collectors.toList());
	}

	public static double getBonusDamage(int tier, EnumDemonWillType type) {
		return (switch (type) {
			case DESTRUCTIVE -> ItemSentientSword.destructiveDamageAdded;
			case STEADFAST -> ItemSentientSword.steadfastDamageAdded;
			case VENGEFUL -> ItemSentientSword.vengefulDamageAdded;
			default -> ItemSentientSword.defaultDamageAdded;
		}) [tier];
	}

	public static double getAttackSpeedMultiplier(int tier, EnumDemonWillType type) {
		return switch (type) {
			case DESTRUCTIVE -> DESTRUCTIVE_ATTACK_SPEED_MULTIPLIERS[tier];
			case VENGEFUL -> VENGEFUL_ATTACK_SPEED_MULTIPLIERS[tier];
			default -> 1;
		};
	}

	public static ItemStack createFilledGem(EnumDemonWillType type, ItemSoulGem gem) {
		ItemStack stack = new ItemStack(gem);
		gem.setWill(type, stack, gem.getMaxWill(type, stack));
		return stack;
	}

	public static ItemStack createFilledGem(EnumDemonWillType type, ItemSoulGem gem, double souls) {
		ItemStack stack = new ItemStack(gem);
		gem.setWill(type, stack, souls);
		return stack;
	}

}
