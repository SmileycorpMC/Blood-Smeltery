package net.smileycorp.bloodsmeltery.common.modifiers.upgrades;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryContent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.interaction.KeybindInteractModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.util.ChatUtil;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class DivinationModifier extends Modifier implements GeneralInteractionModifierHook, KeybindInteractModifierHook {

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.GENERAL_INTERACT, ModifierHooks.ARMOR_INTERACT);
	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Component getDisplayName(int level) {
		Component name = super.getDisplayName(level);
		return level > 1 ? name.copy().withStyle(style -> style.withColor(TextColor.fromRgb(0xEE425F))) : name;
	}

	@Override
	public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
		if (tool.getModifierLevel(BloodSmelteryContent.HEMOGLOWIN.get()) == 0 |! HemoglowinModifier.isReady(tool.getPersistentData())) sendMessage(modifier, player);
		return InteractionResult.PASS;
	}

	@Override
	public boolean startInteract(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot, TooltipKey keyModifier) {
		sendMessage(modifier, player);
		return false;
	}

	public static void sendMessage(ModifierEntry modifier, Player player) {
		ChatUtil.sendNoSpam(player, Component.translatable("tooltip.bloodmagic.sigil." + (modifier.getLevel() > 1 ? "divination" : "seer")
						+ ".currentEssence", NetworkHelper.getSoulNetwork(player).getCurrentEssence()));
	}

}
