package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.BloodstainedModifier;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.DivinationModifier;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.ExsanguinateModifier;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.SentientModifier;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;
import slimeknights.mantle.registration.object.WallBuildingBlockObject;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.recipe.casting.container.ContainerFillingRecipeSerializer;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.component.SearedBlock;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

@EventBusSubscriber(modid=Constants.MODID)
public class ModContent {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
	public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Constants.MODID);
	public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(Constants.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MODID);
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Constants.MODID);

	public static final MetalItemObject BLOODBRASS = BLOCKS.registerMetal("bloodbrass", "bloodbrass",
			Block.Properties.of(Material.HEAVY_METAL, MaterialColor.NETHER).sound(SoundType.METAL),
			(b) -> new BlockTooltipItem(b, new Item.Properties().tab(TinkerModule.TAB_GENERAL)), new Item.Properties().tab(TinkerModule.TAB_GENERAL));

	//fluids
	public static final FluidObject<ForgeFlowingFluid> BLOOD_SEARED_STONE = FLUIDS.register("blood_stone", ModelFluidAttributes.builder().luminosity(0).density(2000)
			.viscosity(8000).temperature(900).color(0x432425).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), Material.LAVA, 8);

	public static final FluidObject<ForgeFlowingFluid> MOLTEN_BLOODBRASS = FLUIDS.register("molten_bloodbrass", ModelFluidAttributes.builder().density(2000).viscosity(10000)
			.temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA).temperature(1000).color(0x910000), Material.LAVA, 12);

	public static final FluidObject<ForgeFlowingFluid> MOLTEN_HELLFORGED = FLUIDS.register("molten_hellforged", ModelFluidAttributes.builder().density(2000).viscosity(10000)
			.temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA).temperature(1000).color(0x78C9B9), Material.LAVA, 12);

	//demon will fluids
	static {
		if (BloodSmelteryConfig.enableFluidWill.get()) {
			for (EnumDemonWillType will : EnumDemonWillType.values()) {
				if (!BloodSmelteryConfig.unifiedWill.get() || will == EnumDemonWillType.DEFAULT) {
					FluidObject<ForgeFlowingFluid> fluid = FLUIDS.register(will.toString() + "_will", ModelFluidAttributes.builder().luminosity(11).density(1000)
							.viscosity(1000).temperature(500).color(DemonWillUtils.getColour(will)).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), Material.LAVA, 11);
					DemonWillUtils.registerWillFluid(will, fluid);
				}
			}
		}
	}

	//recipe serializers
	public static final RegistryObject<RecipeSerializer<MeltingRecipe>> WILL_MELTING = RECIPE_SERIALIZERS.register("will_melting", () -> new MeltingRecipe.Serializer<>(WillMeltingRecipe::new));
	public static final RegistryObject<ContainerFillingRecipeSerializer<TartaricGemFillingRecipe>> TARTARIC_GEM_FILLING = RECIPE_SERIALIZERS.register("tartaric_gem_filling", () -> new ContainerFillingRecipeSerializer<>(TartaricGemFillingRecipe::new));

	//abilities
	public static final StaticModifier<SentientModifier> SENTIENT_MODIFIER = MODIFIERS.register("sentient", () -> new SentientModifier());

	//upgrades
	public static final StaticModifier<DivinationModifier> DIVINATION = MODIFIERS.register("divination", () -> new DivinationModifier());

	//traits
	public static final StaticModifier<BloodstainedModifier> BLOODSTAINED = MODIFIERS.register("bloodstained", () -> new BloodstainedModifier());
	public static final StaticModifier<ExsanguinateModifier> EXSANGUINATE = MODIFIERS.register("exsanguinate", () -> new ExsanguinateModifier());


}
