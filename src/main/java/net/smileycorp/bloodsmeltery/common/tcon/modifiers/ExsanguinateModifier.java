package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import wayoftime.bloodmagic.common.item.ItemBloodOrb;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.core.registry.OrbRegistry;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import javax.annotation.Nullable;

public class ExsanguinateModifier extends PlayerBoundModifier implements MeleeHitModifierHook, ProjectileHitModifierHook {

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
	}

	@Override
	public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
		hit(tool.getPersistentData(), modifier, context.getPlayerAttacker(), context.getLivingTarget());
	}

	@Override
	public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
		if (!notBlocked || target == null |! (attacker instanceof Player)) return false;
		hit(persistentData, modifier, (Player) attacker, target);
		return false;
	}

	public void hit(ModDataNBT nbt, ModifierEntry modifier, Player player, LivingEntity target) {
		if (player == null) return;
		if (!isBound(nbt)) bind(nbt, player);
		if (player.level().isClientSide) return;
		if ((target.isDeadOrDying()) && (target instanceof Enemy || BloodSmelteryConfig.exsanguinateDrainsPassives.get())) {
			int level = modifier.getLevel();
			double amount = Math.floor(target.getMaxHealth() * BloodSmelteryConfig.exsanguinateLPPercent.get());
			amount = amount * BloodSmelteryConfig.exsanguinateLPRate.get();
			if (level > 1) amount = amount * Math.pow(BloodSmelteryConfig.exsanguinateLPMultiplier.get(), level + 1);
			Binding binding = getBinding(nbt);
			SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
			if (network.getOrbTier() > 0) {
				ItemStack orb = OrbRegistry.getOrbsForTier(network.getOrbTier()).get(0);
				network.add(new SoulTicket((int) Math.floor(amount)), ((ItemBloodOrb)orb.getItem()).getOrb(orb).getCapacity());
			}
		}
	}

}
