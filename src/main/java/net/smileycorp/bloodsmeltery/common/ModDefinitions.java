package net.smileycorp.bloodsmeltery.common;

import net.minecraft.resources.ResourceLocation;

public class ModDefinitions {
	public static final String MODID = "bloodsmeltery";
	public static final String NAME = "Blood Smeltery";

	public static String getName(String name) {
		return MODID + "." + name.replace("_", "");
	}

	public static ResourceLocation getResource(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}
}
