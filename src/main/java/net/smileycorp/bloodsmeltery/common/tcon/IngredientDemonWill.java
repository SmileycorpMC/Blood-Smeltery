package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.MultiItemValue;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.DemonWillUtils;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;

public class IngredientDemonWill extends Ingredient {

	private final EnumDemonWillType will;

	protected IngredientDemonWill(EnumDemonWillType will) {
		super(getIngredientStream(will));
		this.will = will;
	}

	public EnumDemonWillType getWillType() {
		return will;
	}

	@Override
	public boolean test(@Nullable ItemStack p_test_1_) {
		if (p_test_1_ == null) {
			return false;
		} else {
			if (itemStacks == null) {
				itemStacks = Arrays.stream(values).flatMap((p_209359_0_) -> {
					return p_209359_0_.getItems().stream();
				}).distinct().toArray((p_209358_0_) -> {
					return new ItemStack[p_209358_0_];
				});
			}
			if (itemStacks.length == 0) {
				return p_test_1_.isEmpty();
			} else {
				for(ItemStack itemstack : itemStacks) {
					if (itemstack.getItem() == p_test_1_.getItem()) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private static Stream<? extends MultiItemValue> getIngredientStream(EnumDemonWillType will) {
		List<ItemStack> stacks = new ArrayList<>();
		for (RegistryObject<Item> item : DemonWillUtils.getTartaricGemItems())  {
			ItemStack stack = new ItemStack(item.get());
			CompoundTag tag = stack.getOrCreateTag();
			if (will != EnumDemonWillType.DEFAULT) {
				tag.putString("demonWillType", will.toString());
			}
			stacks.add(stack);
		}
		return Arrays.stream(new MultiItemValue[] {new MultiItemValue(stacks)});
	}

}
