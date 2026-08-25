package net.smileycorp.bloodsmeltery.common.will;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class DemonWillUtils {

	private static final EnumMap<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> WILL_FLUIDS = Maps.newEnumMap(EnumDemonWillType.class);
	private static final BiMap<EnumDemonWillType, TagKey<Fluid>> WILL_FLUID_TAGS = createFluidTagMap();

	private static final List<RegistryObject<Item>> TARTARIC_GEMS = Lists.newArrayList(BloodMagicItems.PETTY_GEM, BloodMagicItems.LESSER_GEM, BloodMagicItems.COMMON_GEM, BloodMagicItems.GREATER_GEM);

	private static BiMap<EnumDemonWillType, TagKey<Fluid>> createFluidTagMap() {
		BiMap<EnumDemonWillType, TagKey<Fluid>> map = HashBiMap.create();
		map.put(EnumDemonWillType.DEFAULT, WillFluidTags.DEFAULT_WILL);
		map.put(EnumDemonWillType.CORROSIVE, WillFluidTags.CORROSIVE_WILL);
		map.put(EnumDemonWillType.DESTRUCTIVE, WillFluidTags.DESTRUCTIVE_WILL);
		map.put(EnumDemonWillType.VENGEFUL, WillFluidTags.VENGEFUL_WILL);
		map.put(EnumDemonWillType.STEADFAST, WillFluidTags.STEADFAST_WILL);
		return map;
	}

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

	public static MapColor getMapColor(EnumDemonWillType type) {
		return switch (type) {
			case DEFAULT -> MapColor.DIAMOND;
			case CORROSIVE -> MapColor.EMERALD;
			case DESTRUCTIVE -> MapColor.TERRACOTTA_YELLOW;
			case VENGEFUL -> MapColor.TERRACOTTA_PINK;
			case STEADFAST -> MapColor.COLOR_PURPLE;
		};
	}

	public static EnumDemonWillType getTypeForFluid(FluidStack fluidStack) {
		return getTypeForFluid(fluidStack.getFluid());
	}

	public static EnumDemonWillType getTypeForFluid(Fluid fluid) {
		for (Entry<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> entry : WILL_FLUIDS.entrySet()) if (entry.getValue().get() == fluid) return entry.getKey();
		return null;
	}

	public static Fluid getFluidForType(EnumDemonWillType type) {
		if (!WILL_FLUIDS.containsKey(type)) return WILL_FLUIDS.get(EnumDemonWillType.DEFAULT).get();
		return WILL_FLUIDS.get(type).get();
	}

	public static FluidStack getStackForSouls(EnumDemonWillType type, double amount) {
		if (type == null) return new FluidStack(WILL_FLUIDS.get(EnumDemonWillType.DEFAULT).get(), 0);
		return getStackForAmount(type, (int) Math.round(amount * BloodSmelteryConfig.willFluidAmount.get()));
	}

	public static FluidStack getStackForAmount(EnumDemonWillType type, int amount) {
		return new FluidStack(getFluidForType(type), amount);
	}

	public static Collection<FluidObject<ForgeFlowingFluid>> getWillFluids() {
		return WILL_FLUIDS.values();
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
			case DESTRUCTIVE -> ItemSentientSword.destructiveAttackSpeed[tier];
			case VENGEFUL -> ItemSentientSword.vengefulAttackSpeed[tier];
			default -> 1;
		};
	}

	public static ItemStack createFilledGem(EnumDemonWillType type, ItemSoulGem gem, double souls) {
		ItemStack stack = new ItemStack(gem);
		gem.setWill(type, stack, souls);
		return stack;
	}

	public static TagKey<Fluid> getTagForType(EnumDemonWillType type) {
		return WILL_FLUID_TAGS.get(type);
	}

	public static EnumDemonWillType getTypeForTag(TagKey<Fluid> tag) {
		return WILL_FLUID_TAGS.inverse().get(tag);
	}

	public static String name(EnumDemonWillType type) {
		return type == EnumDemonWillType.DEFAULT ? "demon" : type.name;
	}

}
