package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class BloodstainedModifier extends Modifier {

	public BloodstainedModifier() {
		super(0x512525);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
		if (!world.isClientSide && isCorrectSlot && stack != null) {
			if (holder.tickCount % BloodSmelteryConfig.bloodstainedCooldown.get() == 0) {
				if (holder instanceof PlayerEntity && stack.isDamaged()) {
					PlayerEntity player = (PlayerEntity) holder;
					SoulNetwork network = NetworkHelper.getSoulNetwork(player);
					if (network != null) {
						int amount = BloodSmelteryConfig.bloodstainedLPCost.get();
						if (level > 1) {
							amount = (int) Math.ceil(((float)amount)*Math.pow(BloodSmelteryConfig.bloodstainedLPMultiplier.get(), level-1));
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
