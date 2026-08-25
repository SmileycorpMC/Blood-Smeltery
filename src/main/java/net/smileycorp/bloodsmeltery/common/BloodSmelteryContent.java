package net.smileycorp.bloodsmeltery.common;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.modifiers.*;
import net.smileycorp.bloodsmeltery.common.modifiers.hook.SpendLPModifierHook;
import net.smileycorp.bloodsmeltery.common.recipes.WillMeltingRecipe;
import net.smileycorp.bloodsmeltery.common.will.DemonWillUtils;
import net.smileycorp.bloodsmeltery.integration.thermal.ThermalIntegration;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.tools.TinkerToolParts;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.registries.BloodMagicCreativeTabs;

import java.util.EnumMap;

@EventBusSubscriber(modid= Constants.MODID)
public class BloodSmelteryContent {

	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
	public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Constants.MODID);
	public static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(Constants.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MODID);
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Constants.MODID);

	//items
	public static final MetalItemObject BLOODBRASS = BLOCKS.registerMetal("bloodbrass", "bloodbrass",
			Block.Properties.of().mapColor(MapColor.NETHER).sound(SoundType.METAL),
			b -> new BlockTooltipItem(b, new Item.Properties()), new Item.Properties());

	public static final RegistryObject<Item> TAUONIC_THREAD = ITEMS.register("tauonic_thread", () -> new Item(new Item.Properties()));

	//fluids
	public static final FlowingFluidObject<ForgeFlowingFluid> BLOOD_SEARED_STONE = FLUIDS.registerStone("blood_seared_stone")
			.type(hotFluid("blood_seared_stone", 900, 2000, 10000, 6)).burningBlock(MapColor.WARPED_HYPHAE, 6, 7, 2).bucket().flowing();

	public static final FlowingFluidObject<ForgeFlowingFluid> MOLTEN_BLOODBRASS = FLUIDS.registerMetal("molten_bloodbrass")
			.type(hotFluid("molten_bloodbrass", 1000, 2000, 8000, 10)).burningBlock(MapColor.NETHER, 10, 10, 6).bucket().flowing();

	private static final EnumMap<EnumDemonWillType, FluidObject<ForgeFlowingFluid>> HELLFORGED_FLUIDS = Maps.newEnumMap(EnumDemonWillType.class);

	public static ForgeFlowingFluid getHellforged(EnumDemonWillType type) {
		return HELLFORGED_FLUIDS.get(BloodSmelteryConfig.unifiedWill.get() ? EnumDemonWillType.DEFAULT : type).get();
	}

	public static final RegistryObject<CreativeModeTab> TAB = TABS.register("materials", () -> CreativeModeTab.builder()
					.title(Component.translatable("creativetab." + Constants.MODID))
					.withTabsBefore(BloodMagicCreativeTabs.BLOODMAGIC_DECORATIVE.getId()).icon(() -> new ItemStack(BLOODBRASS.getIngot())).displayItems(BloodSmelteryContent::fillTab).build());

	//recipe serializers
	public static final RegistryObject<RecipeSerializer<WillMeltingRecipe>> WILL_MELTING = RECIPE_SERIALIZERS.register("will_melting", () -> LoadableRecipeSerializer.of(WillMeltingRecipe.LOADER));

	//hooks
	public static final ModuleHook<SpendLPModifierHook> SPEND_LP_HOOK = ModifierHooks.register(Constants.loc("spend_lp"), SpendLPModifierHook.class, (tool, modifier, player, slot, stack, network, amountDrained, dealDamage) -> SpendLPModifierHook.defaultInstance(tool, modifier, slot, player, network, amountDrained, dealDamage));

	//abilities
	public static final StaticModifier<SentienceModifier> SENTIENCE_MODIFIER = MODIFIERS.register("sentience", SentienceModifier::new);

	//upgrades
	public static final StaticModifier<DivinationModifier> DIVINATION = MODIFIERS.register("divination", DivinationModifier::new);
	public static final StaticModifier<HemoglowinModifier> HEMOGLOWIN = MODIFIERS.register("hemoglowin", HemoglowinModifier::new);

	//traits
	public static final StaticModifier<BloodstainedModifier> BLOODSTAINED = MODIFIERS.register("bloodstained", BloodstainedModifier::new);
	public static final StaticModifier<ExsanguinateModifier> EXSANGUINATE = MODIFIERS.register("exsanguinate", ExsanguinateModifier::new);
	public static final StaticModifier<TransfusionModifier> TRANSFUSION = MODIFIERS.register("transfusion", TransfusionModifier::new);
	public static final StaticModifier<AmbrosiacModifier> AMBROSIAC = MODIFIERS.register("ambrosiac", AmbrosiacModifier::new);

	//demon will fluids
	public static void initWillFluids() {
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			String name = DemonWillUtils.name(type) + "_will";
				DemonWillUtils.registerWillFluid(type, FLUIDS.register(name)
						.type(hotFluid(name, 500, 1000, 10000, 11))
						.burningBlock(DemonWillUtils.getMapColor(type), 11, 7, 10).bucket().flowing());
			if (type == EnumDemonWillType.DEFAULT && BloodSmelteryConfig.unifiedWill.get()) return;
		}
	}

	//demon will fluids
	public static void initHellforgedFluids() {
		for (EnumDemonWillType type : EnumDemonWillType.values()) {
			String name = (type == EnumDemonWillType.DEFAULT ? "" : DemonWillUtils.name(type) + "_") + "molten_hellforged";
			HELLFORGED_FLUIDS.put(type, FLUIDS.registerMetal(name)
					.type(hotFluid(name, 1000, 2000, 8000, 12))
					.burningBlock(DemonWillUtils.getMapColor(type), 10, 10, 6).bucket().flowing());
			if (type == EnumDemonWillType.DEFAULT /*&& BloodSmelteryConfig.unifiedDemonite.get()*/) return;
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
		//items
		output.accept(BLOODBRASS.getIngot());
		output.accept(BLOODBRASS.getNugget());
		if (ModList.get().isLoaded("thermal")) output.accept(ThermalIntegration.BLOODBRASS_COIN.get());
		output.accept(TAUONIC_THREAD.get());
		//buckets
		output.accept(MOLTEN_BLOODBRASS.getBucket());
		output.accept(BLOOD_SEARED_STONE.getBucket());
		for (FluidObject<ForgeFlowingFluid> fluid : DemonWillUtils.getWillFluids()) output.accept(fluid.getBucket());
		for (FluidObject<ForgeFlowingFluid> fluid : HELLFORGED_FLUIDS.values()) output.accept(fluid.getBucket());
		//parts
		addParts(output, TinkerToolParts.pickHead);
		addParts(output, TinkerToolParts.smallAxeHead);
		addParts(output, TinkerToolParts.smallBlade);
		addParts(output, TinkerToolParts.adzeHead);
		// large heads
		addParts(output, TinkerToolParts.hammerHead);
		addParts(output, TinkerToolParts.broadAxeHead);
		addParts(output, TinkerToolParts.broadBlade);
		addParts(output, TinkerToolParts.largePlate);
		// binding and rods
		addParts(output, TinkerToolParts.toolHandle);
		addParts(output, TinkerToolParts.toolBinding);
		addParts(output, TinkerToolParts.toughHandle);
		addParts(output, TinkerToolParts.toughBinding);
		// ranged
		addParts(output, TinkerToolParts.bowLimb);
		addParts(output, TinkerToolParts.bowGrip);
		addParts(output, TinkerToolParts.bowstring);
		addParts(output, TinkerToolParts.arrowHead);
		addParts(output, TinkerToolParts.arrowShaft);
		addParts(output, TinkerToolParts.fletching);
		// plating, pair each one with the dummy plating item
		for (ArmorItem.Type type : ArmorItem.Type.values()) addParts(output, TinkerToolParts.plating.get(type));
		addParts(output, TinkerToolParts.maille);
		addParts(output, TinkerToolParts.shieldCore);
	}

	private static void addParts(CreativeModeTab.Output output, ItemLike item) {
		addPart(output, item, MaterialVariantId.tryParse(Constants.MODID, "blood_seared_stone"));
		addPart(output, item, MaterialVariantId.tryParse(Constants.MODID, "bloodbrass"));
		addPart(output, item, MaterialVariantId.tryParse(Constants.MODID, "tauonic_thread"));
	}

	private static void addPart(CreativeModeTab.Output output, ItemLike item, MaterialVariantId id) {
		ItemStack material = IMaterialItem.withMaterial(new ItemStack(item), id);
		if (!material.hasTag()) return;
		output.accept(material);
	}

}
