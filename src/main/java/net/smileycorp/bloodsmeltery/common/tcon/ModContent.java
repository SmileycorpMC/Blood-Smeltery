package net.smileycorp.bloodsmeltery.common.tcon;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.RunicSmelteryContent;
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
import wayoftime.bloodmagic.common.registries.BloodMagicCreativeTabs;

import java.util.EnumMap;

@EventBusSubscriber(modid= Constants.MODID)
public class ModContent {

	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
	public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Constants.MODID);
	public static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(Constants.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MODID);
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Constants.MODID);

	public static final MetalItemObject BLOODBRASS = BLOCKS.registerMetal("bloodbrass", "bloodbrass",
			Block.Properties.of().mapColor(MapColor.NETHER).sound(SoundType.METAL),
			b -> new BlockTooltipItem(b, new Item.Properties()), new Item.Properties());

	//fluids

	public static final FluidObject<ForgeFlowingFluid> BLOOD_SEARED_STONE = FLUIDS.registerStone("blood_seared_stone")
			.type(hotFluid("blood_seared_stone", 900, 2000, 10000, 6)).burningBlock(MapColor.WARPED_HYPHAE, 6, 7, 2).bucket().flowing();

	public static final FluidObject<ForgeFlowingFluid> MOLTEN_BLOODBRASS = FLUIDS.registerMetal("molten_bloodbrass")
			.type(hotFluid("molten_bloodbrass", 1000, 2000, 8000, 10)).burningBlock(MapColor.NETHER, 10, 10, 6).bucket().flowing();

	private static final EnumMap<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> HELLFORGED_FLUIDS = Maps.newEnumMap(EnumDemonWillType.class);

	public static ForgeFlowingFluid getHellforged(EnumDemonWillType type) {
		return HELLFORGED_FLUIDS.get(BloodSmelteryConfig.unifiedWill.get() ? EnumDemonWillType.DEFAULT : type).get();
	}

	public static final RegistryObject<CreativeModeTab> TAB = TABS.register("materials", () -> CreativeModeTab.builder()
					.title(Component.translatable("creativetab." + Constants.MODID))
					.withTabsBefore(BloodMagicCreativeTabs.BLOODMAGIC_DECORATIVE.getId()).icon(() -> new ItemStack(BLOODBRASS.getIngot())).displayItems(ModContent::fillTab).build());

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

	//demon will fluids
	public static void initWillFluids() {
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			String name = (type == EnumDemonWillType.DEFAULT ? "demon" : type.name) + "_";
				DemonWillUtils.registerWillFluid(type, FLUIDS.register(name + "will")
						.type(hotFluid(name + "will", 500, 1000, 10000, 11))
						.burningBlock(DemonWillUtils.getMapColor(type), 11, 7, 10).bucket().flowing());
			if (type == EnumDemonWillType.DEFAULT && BloodSmelteryConfig.unifiedWill.get()) return;
		}
	}

	//demon will fluids
	public static void initHellforgedFluids() {
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			String name = type == EnumDemonWillType.DEFAULT ? "" : type.name + "_";
			HELLFORGED_FLUIDS.put(type, FLUIDS.registerMetal(name + "molten_hellforged")
					.type(hotFluid(name + "molten_hellforged", 1000, 2000, 8000, 12))
					.burningBlock(DemonWillUtils.getMapColor(type), 10, 10, 6).bucket().flowing());
			if (type == EnumDemonWillType.DEFAULT && BloodSmelteryConfig.unifiedDemonite.get()) return;
		}
	}

	public static FluidType.Properties hotFluid(String name, int temperature, int density, int viscosity, int lightLevel) {
		return FluidType.Properties.create()
				.temperature(temperature).density(density).viscosity(viscosity).lightLevel(lightLevel).descriptionId(Constants.name("fluid", name))
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
				.motionScale(0.0023333333333333335D).canSwim(false).canDrown(false)
				.pathType(BlockPathTypes.LAVA).adjacentPathType(null);
	}

	public static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
		//blocks
		output.accept(BLOODBRASS.get());
		output.accept(RunicSmelteryContent.BLOOD_SEARED_BRICKS.get());
		output.accept(RunicSmelteryContent.BLOOD_SEARED_BRICKS.getSlab());
		output.accept(RunicSmelteryContent.BLOOD_SEARED_BRICKS.getStairs());
		output.accept(RunicSmelteryContent.BLOOD_SEARED_BRICKS.getWall());
		output.accept(RunicSmelteryContent.CRACKED_BLOOD_SEARED_BRICKS);
		output.accept(RunicSmelteryContent.FANCY_BLOOD_SEARED_BRICKS);
		output.accept(RunicSmelteryContent.TRIANGLE_BLOOD_SEARED_BRICKS);
		output.accept(RunicSmelteryContent.BLOOD_SEARED_LADDER);
		output.accept(RunicSmelteryContent.BLOOD_SEARED_GLASS);
		output.accept(RunicSmelteryContent.BLOOD_SEARED_GLASS_PANE);
		//items
		output.accept(BLOODBRASS.getIngot());
		output.accept(BLOODBRASS.getNugget());
		output.accept(RunicSmelteryContent.BLOOD_SEARED_BRICK.get());
		//buckets
		output.accept(MOLTEN_BLOODBRASS.getBucket());
		output.accept(BLOOD_SEARED_STONE.getBucket());
		for (FluidObject<ForgeFlowingFluid> fluid : DemonWillUtils.getWillFluids()) output.accept(fluid.getBucket());
		for (FluidObject<ForgeFlowingFluid> fluid : HELLFORGED_FLUIDS.values()) output.accept(fluid.getBucket());
	}

}
