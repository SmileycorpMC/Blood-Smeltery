package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.smileycorp.bloodsmeltery.common.Constants;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import wayoftime.bloodmagic.core.data.Binding;

public abstract class PlayerBoundModifier extends Modifier implements ModifierRemovalHook {

	protected static final ResourceLocation BINDING_DATA = Constants.loc("binding");

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.REMOVE);
	}

	@Override
	public Component onRemoved(IToolStackView tool, Modifier modifier) {
		tool.getPersistentData().remove(BINDING_DATA);
		return null;
	}

	public Binding getBinding(ModDataNBT nbt) {
		Binding binding = new Binding(null, null);
		binding.deserializeNBT(nbt.getCompound(BINDING_DATA));
		return binding;
	}

	public boolean isBound(ModDataNBT nbt) {
		return nbt.get(BINDING_DATA) != null;
	}

	protected void bind(ModDataNBT nbt, Player player) {
		GameProfile profile = player.getGameProfile();
		nbt.put(BINDING_DATA, new Binding(profile.getId(), profile.getName()).serializeNBT());
	}

	public String getOwner(ModDataNBT nbt) {
		return getBinding(nbt).getOwnerName();
	}

	public Binding getBinding(IToolStackView tool) {
		return getBinding(tool.getPersistentData());
	}

	public boolean isBound(IToolStackView tool) {
		return isBound(tool.getPersistentData());
	}

	protected void bind(IToolStackView tool, Player player) {
		bind(tool.getPersistentData(), player);
	}

	public String getOwner(IToolStackView tool) {
		return getOwner(tool.getPersistentData());
	}

}
