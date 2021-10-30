package net.smileycorp.bloodsmeltery.common;

import net.minecraft.util.ResourceLocation;

public class ModDefinitions {
	public static final String modid = "bloodsmeltery";
	public static final String name = "Blood Smeltery: A TCon Addon for Blood Magic";
	public static final String dependencies = "required-after:tconstruct;required-after:bloodmagic;required-after:mantle;required-after:atlaslib";
	public static final String version = "1.1.2";
	public static final String location = "net.smileycorp.bloodsmeltery.";
	
	public static final String client = location + "client.ClientProxy";
	public static final String common = location + "common.CommonProxy";
	
	public static String getName(String name) {
		return modid + "." + name.replace("_", "");
	}
	
	public static ResourceLocation getResource(String name) {
		return new ResourceLocation(modid, name.toLowerCase());
	}
}
