package net.smileycorp.bloodsmeltery.common.tcon;

public class TartaricGemFillingRecipe /*extends ContainerFillingRecipe*/ {

	/*private List<DisplayCastingRecipe> displayRecipes;

	public TartaricGemFillingRecipe(ResourceLocation idIn, String groupIn, int fluidAmount, Item containerIn) {
		super(idIn, groupIn, fluidAmount, containerIn);
	}

	@Override
	public List<DisplayCastingRecipe> getRecipes() {
		if (displayRecipes == null) {
			ItemSoulGem gem = (ItemSoulGem) container;
			displayRecipes = Lists.newArrayList();
			for (EnumDemonWillType type : EnumDemonWillType.values()) displayRecipes.add(new DisplayCastingRecipe(getType(),
					Lists.newArrayList(DemonWillUtils.createFilledGem(type, gem, 0)), Lists.newArrayList(DemonWillUtils.getStackForAmount(type, 100)),
					DemonWillUtils.createFilledGem(type, gem, 1), 0, true));
		}
		return displayRecipes;
	}

	@Override
	public int getCoolingTime(ICastingContainer inv) {
		return 0;
	}*/

}
