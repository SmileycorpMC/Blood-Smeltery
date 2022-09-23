package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.common.item.ItemBloodOrb;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.core.registry.OrbRegistry;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class ExsanguinateModifier extends PlayerBoundModifier {

	@Override
	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		Player player = context.getPlayerAttacker();
		if (!isBound(tool)) bind(tool, player);
		if (!player.level.isClientSide) {
			LivingEntity target = context.getLivingTarget();
			if ((target.isDeadOrDying()) && (target instanceof Monster || BloodSmelteryConfig.exsanguinateDrainsPassives.get())) {
				double amount = Math.floor(target.getMaxHealth() * BloodSmelteryConfig.exsanguinateLPPercent.get());
				amount = amount * BloodSmelteryConfig.exsanguinateLPRate.get();
				if (level > 1) amount = amount * Math.pow(BloodSmelteryConfig.exsanguinateLPMultiplier.get(), level+1);
				Binding binding = getBinding(tool);
				SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
				if (network.getOrbTier() > 0) {
					ItemStack orb = OrbRegistry.getOrbsForTier(network.getOrbTier()).get(0);
					network.add(new SoulTicket((int) Math.floor(amount)), ((ItemBloodOrb)orb.getItem()).getOrb(orb).getCapacity());
				}
			}
		}
		return 0;
	}

}
