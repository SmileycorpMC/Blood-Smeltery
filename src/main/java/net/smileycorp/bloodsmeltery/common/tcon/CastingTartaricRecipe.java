package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.item.ItemStack;
import net.smileycorp.bloodsmeltery.common.FluidWillUtils;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWillGem;

public class CastingTartaricRecipe extends CastingRecipe {
	
	protected final EnumDemonWillType type;
	protected final int tier;
	
	public CastingTartaricRecipe(EnumDemonWillType type, int tier) {
		super(setWill(type, tier), RecipeMatchTartaric.of(type, 1f, tier), FluidWillUtils.getStackForSouls(type, 1), true, true);
		this.type = type;
		this.tier = tier;
	}
	
	protected CastingTartaricRecipe(CastingTartaricRecipe recipe) {
		super(recipe.output, recipe.cast, recipe.getFluid(), false, false);
		this.type = recipe.type;
		this.tier = recipe.tier;
	}

	protected static ItemStack setWill(EnumDemonWillType type, int tier) {
		ItemStack stack = new ItemStack(RegistrarBloodMagicItems.SOUL_GEM, 1, tier);
		((IDemonWillGem)RegistrarBloodMagicItems.SOUL_GEM).setWill(type, stack, 1f);
		return stack;
	}

	public CastingTartaricRecipe getJEIPassthrough() {
		return new CastingTartaricRecipe(this);
	}				

}
