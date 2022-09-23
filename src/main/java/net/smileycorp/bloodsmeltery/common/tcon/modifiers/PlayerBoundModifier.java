package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import wayoftime.bloodmagic.core.data.Binding;

public abstract class PlayerBoundModifier extends Modifier {

	protected static final ResourceLocation BINDING_DATA = ModDefinitions.getResource("binding");

	public PlayerBoundModifier(int color) {
		super(color);
	}

	public Binding getBinding(IModifierToolStack tool) {
		Binding binding = new Binding(null, null);
		binding.deserializeNBT((CompoundNBT) tool.getPersistentData().get(BINDING_DATA));
		return binding;
	}

	public boolean isBound(IModifierToolStack tool) {
		return tool.getPersistentData().get(BINDING_DATA) != null;
	}

	protected void bind(IModifierToolStack tool, PlayerEntity player) {
		GameProfile profile = player.getGameProfile();
		tool.getPersistentData().put(BINDING_DATA, new Binding(profile.getId(), profile.getName()).serializeNBT());
	}

	public String getOwner(IModifierToolStack tool) {
		return getBinding(tool).getOwnerName();
	}

}
