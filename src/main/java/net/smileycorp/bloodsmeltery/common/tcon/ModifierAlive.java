package net.smileycorp.bloodsmeltery.common.tcon;

import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import net.minecraft.nbt.NBTTagCompound;

import net.smileycorp.bloodsmeltery.common.ModDefinitions;

public class ModifierAlive extends ToolModifier {

	public ModifierAlive() {
		super(ModDefinitions.getName("alive"), 0xBAEEF0);
		addAspects(ModifierAspect.weaponOnly, new ModifierAspect.SingleAspect(this));
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {}

	public String getName() {
		return identifier;
	}

}
