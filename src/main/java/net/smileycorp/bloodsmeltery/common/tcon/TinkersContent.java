package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.DemonWillUtils;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;

@EventBusSubscriber(modid=ModDefinitions.MODID)
public class TinkersContent {

	public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(ModDefinitions.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModDefinitions.MODID);
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ModDefinitions.MODID);

	public static final FluidObject<ForgeFlowingFluid> BLOOD_INFUSED_STONE = FLUIDS.register("blood_stone", ModelFluidAttributes.builder().luminosity(0).density(2000)
			.viscosity(8000).temperature(900).color(0x432425).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), Material.LAVA, 8);

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

	public static final RegistryObject<RecipeSerializer<MeltingRecipe>> WILL_MELTING = RECIPE_SERIALIZERS.register("will_melting", () -> new MeltingRecipe.Serializer<>(WillMeltingRecipe::new));

	public static final StaticModifier<SentientModifier> SENTIENT_MODIFIER = MODIFIERS.register("sentient", () -> new SentientModifier());

}
