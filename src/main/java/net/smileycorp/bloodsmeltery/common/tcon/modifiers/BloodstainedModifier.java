package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class BloodstainedModifier extends Modifier implements InventoryTickModifierHook {

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.INVENTORY_TICK);
	}

	@Override
	public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
		if (world.isClientSide |! isCorrectSlot || stack == null) return;
		if (holder.tickCount % BloodSmelteryConfig.bloodstainedCooldown.get() != 0) return;
		if (!(holder instanceof Player) |! stack.isDamaged()) return;
		Player player = (Player) holder;
		SoulNetwork network = NetworkHelper.getSoulNetwork(player);
		if (network == null) return;
		int amount = BloodSmelteryConfig.bloodstainedLPCost.get();
		int level = modifier.getLevel();
		if (level > 1) amount = (int) Math.ceil(((float)amount) * Math.pow(BloodSmelteryConfig.bloodstainedLPMultiplier.get(), level - 1) * level);
		if (BloodSmelteryConfig.bloodstainedHurtsPlayers.get() || network.getCurrentEssence() > amount) {
			float health = player.getHealth();
			if (network.syphonAndDamage(player, new SoulTicket(amount)).isSuccess()) if (health > player.getHealth()) return;
		}
		stack.setDamageValue(stack.getDamageValue() - level);
	}

}
