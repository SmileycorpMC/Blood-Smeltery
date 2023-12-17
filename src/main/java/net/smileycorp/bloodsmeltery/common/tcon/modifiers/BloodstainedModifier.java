package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class BloodstainedModifier extends Modifier {

	@Override
	public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
		if (!world.isClientSide && isCorrectSlot && stack != null) {
			if (holder.tickCount % BloodSmelteryConfig.bloodstainedCooldown.get() == 0) {
				if (holder instanceof Player && stack.isDamaged()) {
					Player player = (Player) holder;
					SoulNetwork network = NetworkHelper.getSoulNetwork(player);
					if (network != null) {
						int amount = BloodSmelteryConfig.bloodstainedLPCost.get();
						if (level > 1) {
							amount = (int) Math.ceil(((float)amount) * Math.pow(BloodSmelteryConfig.bloodstainedLPMultiplier.get(), level-1));
						}
						if (BloodSmelteryConfig.bloodstainedHurtsPlayers.get() || network.getCurrentEssence() > amount) {
							float health = player.getHealth();
							if (network.syphonAndDamage(player, new SoulTicket(amount)).isSuccess()) {
								if (health <= player.getHealth()) stack.setDamageValue(stack.getDamageValue()-1);;
							}
						}
					}
				}
			}
		}
	}

}
