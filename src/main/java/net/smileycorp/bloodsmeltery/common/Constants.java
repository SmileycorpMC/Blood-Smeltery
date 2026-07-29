package net.smileycorp.bloodsmeltery.common;

import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class Constants {
	
	public static final String MODID = "bloodsmeltery";
	public static final String NAME = "Blood Smeltery";

	public static String name(String prefix, String suffix) {
		return prefix.toLowerCase() + "." + MODID + "." + suffix.toLowerCase(Locale.US);
	}

	public static ResourceLocation loc(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}
	
}
