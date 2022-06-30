package net.smileycorp.bloodsmeltery.common;

import java.lang.reflect.Field;

import slimeknights.tconstruct.smeltery.tileentity.controller.SmelteryTileEntity;
import slimeknights.tconstruct.smeltery.tileentity.module.MeltingModule;

public class ReflectionUtils {

	private static Field FIELD_PARENT = null;

	public static SmelteryTileEntity getSmelteryTile(MeltingModule module) {
		try {
			if (FIELD_PARENT == null) {
				FIELD_PARENT = MeltingModule.class.getField("parent");
				FIELD_PARENT.setAccessible(true);
			}
			return (SmelteryTileEntity) FIELD_PARENT.get(module);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
