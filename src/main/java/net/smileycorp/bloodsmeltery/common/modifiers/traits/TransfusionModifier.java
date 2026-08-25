package net.smileycorp.bloodsmeltery.common.modifiers.traits;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.common.item.ItemBloodOrb;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.core.registry.OrbRegistry;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class TransfusionModifier extends Modifier implements ToolDamageModifierHook {

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.TOOL_DAMAGE);
	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int damageDealt, @Nullable LivingEntity holder) {
		if (!(holder instanceof Player player)) return damageDealt;
        if (player.level().isClientSide) return damageDealt;
		if (!(tool.hasTag(TinkerTags.Items.ARMOR) || tool.hasTag(TinkerTags.Items.SHIELDS))) return damageDealt;
		int level = modifier.getLevel();
		double amount = damageDealt * BloodSmelteryConfig.transfusionLPRate.get();
		if (level > 1) amount = amount * Math.pow(BloodSmelteryConfig.transfusionLPMultiplier.get(), level - 1);
		GameProfile profile = player.getGameProfile();
		Binding binding = new Binding(profile.getId(), profile.getName());
		SoulNetwork network = NetworkHelper.getSoulNetwork(binding);
		if (network.getOrbTier() > 0) {
			ItemStack orb = OrbRegistry.getOrbsForTier(network.getOrbTier()).get(0);
			network.add(new SoulTicket((int) Math.floor(amount)), ((ItemBloodOrb)orb.getItem()).getOrb(orb).getCapacity());
		}
		return damageDealt;
	}

}
