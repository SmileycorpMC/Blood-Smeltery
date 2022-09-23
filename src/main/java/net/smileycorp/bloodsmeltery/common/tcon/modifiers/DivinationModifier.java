package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.util.ChatUtil;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class DivinationModifier extends PlayerBoundModifier {

	public DivinationModifier() {
		super(0xFCE324);
	}

	@Override
	public ITextComponent getDisplayName(int level) {
		ITextComponent name = super.getDisplayName(level);
		return level > 1 ? name.copy().withStyle(style -> style.withColor(Color.fromRgb(0xEE425F))) : name;
	}

	@Override
	public void onRemoved(IModifierToolStack tool) {
		tool.getPersistentData().remove(PlayerBoundModifier.BINDING_DATA);
	}


	@Override
	public ActionResultType onToolUse(IModifierToolStack tool, int level, World world, PlayerEntity player, Hand hand, EquipmentSlotType slot) {
		if (!isBound(tool)) bind(tool, player);
		String key = "tooltip.bloodmagic.sigil." + (level > 1 ? "divination" : "seer") + ".";
		Binding binding = getBinding(tool);
		int currentEssence = NetworkHelper.getSoulNetwork(binding).getCurrentEssence();
		List<ITextComponent> message = Lists.newArrayList();
		if (!binding.getOwnerId().equals(player.getGameProfile().getId())) message.add(new TranslationTextComponent(key + "otherNetwork", binding.getOwnerName()));
		message.add(new TranslationTextComponent(key + "currentEssence", currentEssence));
		ChatUtil.sendNoSpam(player, message.toArray(new ITextComponent[message.size()]));
		return ActionResultType.SUCCESS;
	}

}
