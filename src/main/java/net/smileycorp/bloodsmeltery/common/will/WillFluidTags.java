package net.smileycorp.bloodsmeltery.common.will;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class WillFluidTags {

    public static TagKey<Fluid> DEMON_WILL = FluidTags.create(new ResourceLocation("bloodmagic:demon_will"));

    public static TagKey<Fluid> DEFAULT_WILL = FluidTags.create(new ResourceLocation("bloodmagic:demon_will/default"));
    public static TagKey<Fluid> CORROSIVE_WILL = FluidTags.create(new ResourceLocation("bloodmagic:demon_will/corrosive"));
    public static TagKey<Fluid> DESTRUCTIVE_WILL = FluidTags.create(new ResourceLocation("bloodmagic:demon_will/destructive"));
    public static TagKey<Fluid> VENGEFUL_WILL = FluidTags.create(new ResourceLocation("bloodmagic:demon_will/vengeful"));
    public static TagKey<Fluid> STEADFAST_WILL = FluidTags.create(new ResourceLocation("bloodmagic:demon_will/steadfast"));

}
