package net.smileycorp.bloodsmeltery.common;

import net.minecraft.resources.ResourceLocation;

public class Constants {
	public static final String MODID = "bloodsmeltery";
	public static final String NAME = "Blood Smeltery";

	public static String name(String name) {
		return MODID + "." + name.replace("_", "");
	}

	public static ResourceLocation loc(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}
}
