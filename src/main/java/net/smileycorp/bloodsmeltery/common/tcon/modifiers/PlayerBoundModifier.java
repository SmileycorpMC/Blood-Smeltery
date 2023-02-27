package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.smileycorp.bloodsmeltery.common.Constants;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.Binding;

public abstract class PlayerBoundModifier extends Modifier {

	protected static final ResourceLocation BINDING_DATA = Constants.loc("binding");

	public PlayerBoundModifier() {
		super();
	}

	public Binding getBinding(IToolStackView tool) {
		Binding binding = new Binding(null, null);
		binding.deserializeNBT((CompoundTag) tool.getPersistentData().get(BINDING_DATA));
		return binding;
	}

	public boolean isBound(IToolStackView tool) {
		return tool.getPersistentData().get(BINDING_DATA) != null;
	}

	protected void bind(IToolStackView tool, Player player) {
		GameProfile profile = player.getGameProfile();
		tool.getPersistentData().put(BINDING_DATA, new Binding(profile.getId(), profile.getName()).serializeNBT());
	}

	public String getOwner(IToolStackView tool) {
		return getBinding(tool).getOwnerName();
	}

}
