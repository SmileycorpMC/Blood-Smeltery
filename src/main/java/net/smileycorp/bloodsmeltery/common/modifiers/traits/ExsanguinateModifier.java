package net.smileycorp.bloodsmeltery.common.modifiers.traits;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.LauncherHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.common.item.ItemBloodOrb;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.core.registry.OrbRegistry;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import javax.annotation.Nullable;

public class ExsanguinateModifier extends Modifier implements MeleeHitModifierHook, LauncherHitModifierHook {

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.LAUNCHER_HIT);
	}

	@Override
	public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
		hit(modifier, context.getPlayerAttacker(), context.getLivingTarget(), damageDealt);
	}

	@Override
	public void onLauncherHitEntity(IToolStackView tool, ModifierEntry modifier, Projectile projectile, LivingEntity attacker, Entity target, @Nullable LivingEntity livingTarget, float damageDealt) {
		hit(modifier, (Player) attacker, livingTarget, damageDealt);
	}

	public void hit(ModifierEntry modifier, Player player, LivingEntity target, float damageDealt) {
		if (player == null || target == null) return;
		if (player.level().isClientSide) return;
		int level = modifier.getLevel();
		double amount = damageDealt * BloodSmelteryConfig.exsanguinateLPRate.get();
		if (level > 1) amount = amount * Math.pow(BloodSmelteryConfig.exsanguinateLPMultiplier.get(), level - 1);
		GameProfile profile = player.getGameProfile();
		Binding binding = new Binding(profile.getId(), profile.getName());
		SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
		if (network.getOrbTier() > 0) {
			ItemStack orb = OrbRegistry.getOrbsForTier(network.getOrbTier()).get(0);
			network.add(new SoulTicket((int) Math.floor(amount)), ((ItemBloodOrb)orb.getItem()).getOrb(orb).getCapacity());
		}
	}

}
