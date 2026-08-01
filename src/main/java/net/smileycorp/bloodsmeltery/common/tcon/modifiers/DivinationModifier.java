package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.util.ChatUtil;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

public class DivinationModifier extends Modifier implements GeneralInteractionModifierHook {

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
	}

	@Override
	public Component getDisplayName(int level) {
		Component name = super.getDisplayName(level);
		return level > 1 ? name.copy().withStyle(style -> style.withColor(TextColor.fromRgb(0xEE425F))) : name;
	}


	@Override
	public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
		int level = modifier.getLevel();
		String key = "tooltip.bloodmagic.sigil." + (level > 1 ? "divination" : "seer") + ".";
		GameProfile profile = player.getGameProfile();
		Binding binding = new Binding(profile.getId(), profile.getName());
		int currentEssence = NetworkHelper.getSoulNetwork(binding).getCurrentEssence();
		List<Component> message = Lists.newArrayList();
		if (!binding.getOwnerId().equals(player.getGameProfile().getId())) message.add(Component.translatable(key + "otherNetwork", binding.getOwnerName()));
		message.add(Component.translatable(key + "currentEssence", currentEssence));
		ChatUtil.sendNoSpam(player, message.toArray(new Component[message.size()]));
		return InteractionResult.SUCCESS;
	}

}
