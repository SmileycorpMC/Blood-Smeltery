package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import slimeknights.tconstruct.library.recipe.casting.DisplayCastingRecipe;
import slimeknights.tconstruct.library.recipe.casting.container.ContainerFillingRecipe;
import slimeknights.tconstruct.smeltery.recipe.ICastingInventory;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

public class TartaricGemFillingRecipe extends ContainerFillingRecipe.Table {

	private List<DisplayCastingRecipe> displayRecipes;

	public TartaricGemFillingRecipe(ResourceLocation idIn, String groupIn, int fluidAmount, Item containerIn) {
		super(idIn, groupIn, fluidAmount, containerIn);
	}

	@Override
	public List<DisplayCastingRecipe> getRecipes() {
		if (displayRecipes == null) {
			ItemSoulGem gem = (ItemSoulGem) container;
			displayRecipes = Lists.newArrayList();
			for (EnumDemonWillType type : EnumDemonWillType.values()) {
				displayRecipes.add(new DisplayCastingRecipe(getType(),
						Lists.newArrayList(DemonWillUtils.createFilledGem(type, gem, 0d)),
						Lists.newArrayList(DemonWillUtils.getStackForAmount(type, 100)),
						DemonWillUtils.createFilledGem(type, gem, 1d), 0, true));
			}
		}
		return displayRecipes;
	}

	@Override
	public int getCoolingTime(ICastingInventory inv) {
		return 0;
	}

}
