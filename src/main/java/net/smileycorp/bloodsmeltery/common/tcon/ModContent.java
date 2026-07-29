package net.smileycorp.bloodsmeltery.common.tcon;

import com.google.common.collect.Maps;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.BloodstainedModifier;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.DivinationModifier;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.ExsanguinateModifier;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.SentientModifier;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;

import java.util.Map;

@EventBusSubscriber(modid= Constants.MODID)
public class ModContent {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
	public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Constants.MODID);
	public static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(Constants.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MODID);
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Constants.MODID);

	public static final MetalItemObject BLOODBRASS = BLOCKS.registerMetal("bloodbrass", "bloodbrass",
			Block.Properties.of().mapColor(MapColor.NETHER).sound(SoundType.METAL),
			b -> new BlockTooltipItem(b, new Item.Properties()), new Item.Properties());

	//fluids
	public static final FluidObject<ForgeFlowingFluid> BLOOD_SEARED_STONE = FLUIDS.registerStone("blood_stone")
			.type(hotFluid("blood_stone", 900, 2000, 10000, 6)).burningBlock(MapColor.WARPED_HYPHAE, 6, 7, 2).bucket().flowing();

	public static final FluidObject<ForgeFlowingFluid> MOLTEN_BLOODBRASS = FLUIDS.registerMetal("molten_bloodbrass")
			.type(hotFluid("molten_bloodbrass", 1000, 2000, 8000, 10)).burningBlock(MapColor.NETHER, 10, 10, 6).bucket().flowing();

	private static final Map<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> HELLFORGED_FLUIDS = Maps.newEnumMap(EnumDemonWillType.class);

	public static ForgeFlowingFluid getHellforged(EnumDemonWillType type) {
		return HELLFORGED_FLUIDS.get(BloodSmelteryConfig.unifiedWill.get() ? EnumDemonWillType.DEFAULT : type).get();
	}

	//demon will fluids
	public static void initWillFluids() {
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			String name = type == EnumDemonWillType.DEFAULT ? "" : type.name + "_";
			MapColor colour = DemonWillUtils.getMapColor(type);
			HELLFORGED_FLUIDS.put(type, FLUIDS.registerMetal(name + "molten_hellforged")
					.type(hotFluid(name + "molten_hellforged", 1000, 2000, 8000, 12))
					.burningBlock(colour, 10, 10, 6).bucket().flowing());
			if (BloodSmelteryConfig.enableFluidWill.get())
				DemonWillUtils.registerWillFluid(EnumDemonWillType.DEFAULT, FLUIDS.register(name + "_will")
					.type(hotFluid(name + "_will", 500, 1000, 10000, 11))
					.burningBlock(colour, 11, 7, 10).bucket().flowing());
			if (type == EnumDemonWillType.DEFAULT && BloodSmelteryConfig.unifiedWill.get()) return;
		}
	}

	public static FluidType.Properties hotFluid(String name, int temperature, int density, int viscosity, int lightLevel) {
		return FluidType.Properties.create()
				.temperature(temperature).density(density).viscosity(viscosity).lightLevel(lightLevel).descriptionId(Constants.name("fluid", name))
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
				.motionScale(0.0023333333333333335D).canSwim(false).canDrown(false)
				.pathType(BlockPathTypes.LAVA).adjacentPathType(null);
	}

	//recipe serializers
	/*public static final RegistryObject<RecipeSerializer<MeltingRecipe>> WILL_MELTING = RECIPE_SERIALIZERS.register("will_melting", () -> new MeltingRecipe.Serializer<>(WillMeltingRecipe::new));
	public static final RegistryObject<ContainerFillingRecipeSerializer<TartaricGemFillingRecipe>> TARTARIC_GEM_FILLING = RECIPE_SERIALIZERS.register("tartaric_gem_filling", () -> new ContainerFillingRecipeSerializer<>(TartaricGemFillingRecipe::new));*/

	//abilities
	public static final StaticModifier<SentientModifier> SENTIENT_MODIFIER = MODIFIERS.register("sentient", SentientModifier::new);

	//upgrades
	public static final StaticModifier<DivinationModifier> DIVINATION = MODIFIERS.register("divination", DivinationModifier::new);

	//traits
	public static final StaticModifier<BloodstainedModifier> BLOODSTAINED = MODIFIERS.register("bloodstained", BloodstainedModifier::new);
	public static final StaticModifier<ExsanguinateModifier> EXSANGUINATE = MODIFIERS.register("exsanguinate", ExsanguinateModifier::new);

}
