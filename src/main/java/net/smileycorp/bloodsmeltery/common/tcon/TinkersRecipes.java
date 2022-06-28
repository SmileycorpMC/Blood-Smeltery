package net.smileycorp.bloodsmeltery.common.tcon;

public class TinkersRecipes {

	//public static List<Item> sigils = new ArrayList<>();


	public static void loadRecipes() {
		/*sigils.addAll(Arrays.asList((new Item[] {BloodMagicItems.SIGIL_AIR, BloodMagicItems.SIGIL_BLOOD_LIGHT, BloodMagicItems.SIGIL_BOUNCE,
				BloodMagicItems.SIGIL_CLAW, BloodMagicItems.SIGIL_DIVINATION, BloodMagicItems.SIGIL_LAVA,
				BloodMagicItems.SIGIL_WATER, BloodMagicItems.SIGIL_VOID, BloodMagicItems.SIGIL_GREEN_GROVE,
				BloodMagicItems.SIGIL_ELEMENTAL_AFFINITY, BloodMagicItems.SIGIL_HASTE, BloodMagicItems.SIGIL_MAGNETISM,
				BloodMagicItems.SIGIL_SUPPRESSION, BloodMagicItems.SIGIL_FAST_MINER, BloodMagicItems.SIGIL_SEER,
				BloodMagicItems.SIGIL_ENDER_SEVERANCE, BloodMagicItems.SIGIL_WHIRLWIND, BloodMagicItems.SIGIL_PHANTOM_BRIDGE,
				BloodMagicItems.SIGIL_COMPRESSION, BloodMagicItems.SIGIL_HOLDING, BloodMagicItems.SIGIL_TELEPOSITION,
				BloodMagicItems.SIGIL_TRANSPOSITION })));

		if (Loader.isModLoaded("bloodarsenal")) BloodArsenalRecipes.loadRecipes();

		//alloying
		if (BloodSmelteryConfig.createLifeEssence) {
			for (Fluid will : WillFluidRegistry.getWillFluids()) {
				TinkerRegistry.registerAlloy(new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.lifeEssenceAmount),
						new FluidStack(will, BloodSmelteryConfig.lifeEssenceRatio[0]),
						new FluidStack(TinkerFluids.blood, BloodSmelteryConfig.lifeEssenceRatio[1]));
			}
		}
		if (BloodSmelteryConfig.createBloodStone) {
			TinkerRegistry.registerAlloy(new FluidStack(TinkersContent.BLOOD_INFUSED_STONE, BloodSmelteryConfig.bloodStoneAmount),
					new FluidStack(BlockLifeEssence.getLifeEssence(), BloodSmelteryConfig.bloodStoneRatio[0]),
					new FluidStack(TinkerFluids.searedStone, BloodSmelteryConfig.bloodStoneRatio[1]));
		}

		//casting
		if (BloodSmelteryConfig.castBlankSlates) {
			TinkerRegistry.registerTableCasting(new ItemStack(BloodMagicItems.SLATE), BloodSmelteryConfig.castSlatesWithPlateCast ?
					TinkerSmeltery.castPlate : ItemStack.EMPTY , TinkersContent.BLOOD_INFUSED_STONE, 250);
		}
		if (BloodSmelteryConfig.castBlankRunes) {
			TinkerRegistry.registerBasinCasting(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE), ItemStack.EMPTY, TinkersContent.BLOOD_INFUSED_STONE, 1000);
		}

		for (EnumDemonWillType will : EnumDemonWillType.values()) {
			int i = will.ordinal();
			if (BloodSmelteryConfig.castWill) {
				ItemStack soul = new ItemStack(BloodMagicItems.MONSTER_SOUL, 1, i);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setFloat("souls", 1f);
				soul.setTagCompound(nbt);

				TinkerRegistry.registerTableCasting(soul, ItemStack.EMPTY, WillFluidRegistry.getFluidForType(will), BloodSmelteryConfig.willFluidAmount);
			}

			if (BloodSmelteryConfig.castFillTartaricGems) {
				for (int j = 0; j < 5; j++) {
					ItemStack soul = new ItemStack(BloodMagicItems.SOUL_GEM, 1, j);
					((IDemonWillGem)BloodMagicItems.SOUL_GEM).setWill(will, soul, 1f);
					TinkerRegistry.registerTableCasting(new CastingTartaricRecipe(will, j));
				}
			}

			if (BloodSmelteryConfig.castSoulStone) {
				TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(RegistrarBloodMagicBlocks.DEMON_EXTRAS, 1, i),
						RecipeMatch.of("stone"), new FluidStack(WillFluidRegistry.getFluidForType(will), BloodSmelteryConfig.soulStoneCost), true, true));
			}
			if (BloodSmelteryConfig.castDemonAlloy) {
				List<ItemStack> ingredients = new ArrayList<>();
				for (Entry<ResourceLocation, Integer> entry : BloodSmelteryConfig.demonAlloyCastIngredient) {
					try {
						ingredients.add(new ItemStack(ForgeRegistries.BLOCKS.getValue(entry.getKey()), 1, entry.getValue()));
					}  catch (Exception e0) {
						try {
							ingredients.add(new ItemStack(ForgeRegistries.ITEMS.getValue(entry.getKey()), 1, entry.getValue()));
						}  catch (Exception e1) {

						}
					}
				}
				if (ingredients.isEmpty()) {
					ingredients.add(ItemStack.EMPTY);
				}
				TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(RegistrarBloodMagicBlocks.DEMON_EXTRAS, 1, i+10),
						RecipeMatch.of(ingredients), new FluidStack(WillFluidRegistry.getFluidForType(will), BloodSmelteryConfig.demonAlloyCost), true, true));
			}
		}

		if (BloodSmelteryConfig.castUpgradeOrbs) {
			int[] costs = {5000, 25000, 40000, 80000, 200000};
			for (int i = 1; i < RegistrarBloodMagic.BLOOD_ORBS.getEntries().size(); i++) {
				ItemStack output = new ItemStack(BloodMagicItems.BLOOD_ORB);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("orb", RegistrarBloodMagic.BLOOD_ORBS.getKey(RegistrarBloodMagic.BLOOD_ORBS.getValues().get(i)).toString());
				output.setTagCompound(nbt);
				TinkerRegistry.registerTableCasting(new CastingRecipe(output,
						RecipeMatchBloodOrb.of(RegistrarBloodMagic.BLOOD_ORBS.getValues().get(i-1)), new FluidStack(BlockLifeEssence.getLifeEssence(), costs[i-1]), true, true));
			}
		}
		if (BloodSmelteryConfig.castAllSlates &! BloodSmelteryConfig.castAltarItems) {
			int[] costs = {2000, 5000, 15000, 30000};
			for (int i = 1; i < 5; i++) {
				TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(BloodMagicItems.SLATE, 1, i),
						RecipeMatch.of(new ItemStack(BloodMagicItems.SLATE, 1, i-1)), new FluidStack(BlockLifeEssence.getLifeEssence(), costs[i-1]), true, true));
			}
		}

		//cast creation
		for(FluidStack fluid : TinkerSmeltery.castCreationFluids) {
			if (BloodSmelteryConfig.makeCastWithSlates) {
				for (int i = 0; i<5; i++) TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castPlate, RecipeMatch.of(new ItemStack(BloodMagicItems.SLATE, 1, i)), fluid, true, true));
			}
			if (BloodSmelteryConfig.makeCastWithSigils) {
				for (Item sigil : sigils) TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castPlate, RecipeMatch.of(sigil), fluid, true, true));
			}
		}

		//melting
		if (BloodSmelteryConfig.unifiedWill) {
			TinkerRegistry.registerMelting(new MeltingWillRecipe(EnumDemonWillType.DEFAULT));
			if (BloodSmelteryConfig.meltCrystals)TinkerRegistry.registerMelting(BloodMagicItems.ITEM_DEMON_CRYSTAL, TinkersContent.FLUID_RAW_WILL,
					BloodSmelteryConfig.crystalMeltMultiplier*BloodSmelteryConfig.willFluidAmount);
		} else {
			for (EnumDemonWillType will : EnumDemonWillType.values()) TinkerRegistry.registerMelting(new MeltingWillRecipe(will));
			if (BloodSmelteryConfig.meltCrystals) for (EnumDemonWillType will : EnumDemonWillType.values()) TinkerRegistry.registerMelting(will.getStack(), WillFluidRegistry.getFluidForType(will),
					BloodSmelteryConfig.crystalMeltMultiplier*BloodSmelteryConfig.willFluidAmount);
		}

		if (BloodSmelteryConfig.meltAllSlates) {
			for (int i = 0; i<5; i++) TinkerRegistry.registerMelting(new ItemStack(BloodMagicItems.SLATE, 1, i), TinkersContent.BLOOD_INFUSED_STONE, 250);
		} else if (BloodSmelteryConfig.meltBlankSlates) {
			TinkerRegistry.registerMelting(new ItemStack(BloodMagicItems.SLATE, 1, 0), TinkersContent.BLOOD_INFUSED_STONE, 250);
		}

		if (BloodSmelteryConfig.meltFunctionalRunes) {
			for (int i = 0; i<11; i++) TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE, 1, i), TinkersContent.BLOOD_INFUSED_STONE, 1000);
		} else if (BloodSmelteryConfig.meltBlankRunes) {
			TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE, 1, 0), TinkersContent.BLOOD_INFUSED_STONE, 1000);
		}
		if (BloodSmelteryConfig.meltRitualStones) {
			for (int i = 0; i<5; i++) TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.RITUAL_STONE, 1, i), TinkersContent.BLOOD_INFUSED_STONE, 250);
			TinkerRegistry.registerMelting(RegistrarBloodMagicBlocks.RITUAL_CONTROLLER, TinkersContent.BLOOD_INFUSED_STONE, 1000);
			TinkerRegistry.registerMelting(new ItemStack(RegistrarBloodMagicBlocks.RITUAL_CONTROLLER, 1, 2), TinkersContent.BLOOD_INFUSED_STONE, 1000);
		}
		if (BloodSmelteryConfig.meltSigils) {
			for (Item sigil : sigils) TinkerRegistry.registerMelting(sigil, TinkersContent.BLOOD_INFUSED_STONE, 250);
		}*/

	}

	public static void loadLateRecipes() {
		//if (BloodSmelteryConfig.castAltarItems) {
		/*for (RecipeBloodAltar recipe : BloodMagicAPI.INSTANCE.getRecipeRegistrar().getAltarRecipes()) {
				RecipeMatch input = RecipeMatch.of(Arrays.asList(recipe.getInput().getMatchingStacks()));
				if (!input.matches(NonNullList.<ItemStack>from(new ItemStack(Items.BUCKET))).isPresent()) {
					TinkerRegistry.registerTableCasting(new CastingRecipe(recipe.getOutput(), input,
							new FluidStack(BlockLifeEssence.getLifeEssence(), recipe.getSyphon()), true, true));
				}
			}*/
		//}
	}
}
