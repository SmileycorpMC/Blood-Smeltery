package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

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
