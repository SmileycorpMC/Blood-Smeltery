package net.smileycorp.bloodsmeltery.common.util;

import com.google.common.collect.Lists;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.MultiItemValue;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
	public boolean test(@Nullable ItemStack test) {
		if (test == null) return false;
		if (itemStacks == null) itemStacks = Arrays.stream(values).flatMap(items -> items.getItems().stream()).distinct().toArray(ItemStack[]::new);
		if (itemStacks.length == 0) return test.isEmpty();
		for (ItemStack itemstack : itemStacks) if (itemstack.getItem() == test.getItem()) return true;
		return false;
	}

	private static Stream<? extends Ingredient.Value> getIngredientStream(EnumDemonWillType will) {
		List<ItemStack> stacks = Lists.newArrayList();
		DemonWillUtils.getTartaricGemItems().forEach(item -> stacks.add(will == EnumDemonWillType.DEFAULT ? new ItemStack(item)
				: DemonWillUtils.createFilledGem(will, item, 0)));
		return Arrays.stream(new MultiItemValue[] {new MultiItemValue(stacks)});
	}

}
