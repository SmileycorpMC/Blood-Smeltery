package net.smileycorp.bloodsmeltery.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class BloodSmelteryTags {

    public static TagKey<Fluid> DEMON_WILL = FluidTags.create(new ResourceLocation("forge:demon_will"));

    public static TagKey<Fluid> DEFAULT_WILL = FluidTags.create(Constants.loc("default_will"));
    public static TagKey<Fluid> CORROSIVE_WILL = FluidTags.create(Constants.loc("corrosive_will"));
    public static TagKey<Fluid> DESTRUCTIVE_WILL = FluidTags.create(Constants.loc("destructive_will"));
    public static TagKey<Fluid> VENGEFUL_WILL = FluidTags.create(Constants.loc("vengeful_will"));
    public static TagKey<Fluid> STEADFAST_WILL = FluidTags.create(Constants.loc("steadfast_will"));

}
