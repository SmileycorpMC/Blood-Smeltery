package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.recipe.casting.AbstractCastingRecipe;
import slimeknights.tconstruct.smeltery.recipe.ICastingInventory;

public class CastingTartaricRecipe extends AbstractCastingRecipe {

	public CastingTartaricRecipe(IRecipeType<?> type, ResourceLocation id, String group, Ingredient cast,
			boolean consumed, boolean switchSlots) {
		super(type, id, group, cast, consumed, switchSlots);
		// TODO Auto-generated constructor stub
	}

	/*protected final EnumDemonWillType type;
	protected final int tier;

	public CastingTartaricRecipe(EnumDemonWillType type, int tier) {
		super(setWill(type, tier), RecipeMatchTartaric.of(type, 1f, tier), DemonWillUtils.getStackForSouls(type, 1), true, true);
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
	}*/

	@Override
	public int getFluidAmount(ICastingInventory inv) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCoolingTime(ICastingInventory inv) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matches(ICastingInventory p_77569_1_, World p_77569_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack assemble(ICastingInventory p_77572_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getResultItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeType<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack func_77571_b() {
		// TODO Auto-generated method stub
		return null;
	}

}
