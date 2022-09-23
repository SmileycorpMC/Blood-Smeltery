package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.util.ChatUtil;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class DivinationModifier extends PlayerBoundModifier {

	@Override
	public Component getDisplayName(int level) {
		Component name = super.getDisplayName(level);
		return level > 1 ? name.copy().withStyle(style -> style.withColor(TextColor.fromRgb(0xEE425F))) : name;
	}

	@Override
	public void onRemoved(IToolStackView tool) {
		tool.getPersistentData().remove(PlayerBoundModifier.BINDING_DATA);
	}


	@Override
	public InteractionResult onToolUse(IToolStackView tool, int level, Level world, Player player, InteractionHand hand, EquipmentSlot slot) {
		if (!isBound(tool)) bind(tool, player);
		String key = "tooltip.bloodmagic.sigil." + (level > 1 ? "divination" : "seer") + ".";
		Binding binding = getBinding(tool);
		int currentEssence = NetworkHelper.getSoulNetwork(binding).getCurrentEssence();
		List<Component> message = Lists.newArrayList();
		if (!binding.getOwnerId().equals(player.getGameProfile().getId())) message.add(new TranslatableComponent(key + "otherNetwork", binding.getOwnerName()));
		message.add(new TranslatableComponent(key + "currentEssence", currentEssence));
		ChatUtil.sendNoSpam(player, message.toArray(new Component[message.size()]));
		return InteractionResult.SUCCESS;
	}

}
