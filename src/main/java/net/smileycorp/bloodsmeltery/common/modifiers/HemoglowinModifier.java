package net.smileycorp.bloodsmeltery.common.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.ModContent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.sigil.ItemSigilBloodLight;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.entity.projectile.EntityBloodLight;
import wayoftime.bloodmagic.util.helper.BlockProtectionHelper;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class HemoglowinModifier extends Modifier implements GeneralInteractionModifierHook, InventoryTickModifierHook, ModifierRemovalHook {

	public static final ResourceLocation COOLDOWN = Constants.loc("hemoglowin_cooldown");

	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.GENERAL_INTERACT, ModifierHooks.INVENTORY_TICK, ModifierHooks.REMOVE);
	}

	@Override
	public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
		ModDataNBT nbt = tool.getPersistentData();
		if (!isReady(nbt)) return InteractionResult.PASS;
		Level level = player.level();
		BlockHitResult ray = ModifiableItem.blockRayTrace(level, player, ClipContext.Fluid.NONE);
		int cost = ((ItemSigilBloodLight) BloodMagicItems.BLOOD_LIGHT_SIGIL.get()).getLpUsed();
		ItemStack stack = player.getItemInHand(hand);
		if (ray.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = ray.getBlockPos().relative(ray.getDirection());
			if (level.isEmptyBlock(pos) && BlockProtectionHelper.tryPlaceBlock(level, pos, BloodMagicBlocks.BLOOD_LIGHT.get().defaultBlockState(), player)) {
				NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(stack, level, player, cost));
				nbt.putInt(COOLDOWN, 10);
				player.swing(hand);
			}
		} else {
			EntityBloodLight light = new EntityBloodLight(level, player);
			light.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5f, 1);
			level.addFreshEntity(light);
			NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(stack, level, player, cost));
			nbt.putInt(COOLDOWN, 10);
		}
		//custom divination handling to make the message run after activating hemoglowin
		ModifierEntry divination = tool.getModifier(ModContent.DIVINATION.get());
		if (divination.getLevel() > 0) DivinationModifier.sendMessage(divination, player);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
		ModDataNBT nbt = tool.getPersistentData();
		if (!nbt.contains(COOLDOWN)) return;
		int cooldown = tool.getPersistentData().getInt(COOLDOWN);
		if (--cooldown <= 0) nbt.remove(COOLDOWN);
		else nbt.putInt(COOLDOWN, cooldown);
	}

	@Override
	public Component onRemoved(IToolStackView tool, Modifier modifier) {
		tool.getPersistentData().remove(COOLDOWN);
		return null;
	}

	public static boolean isReady(ModDataNBT nbt) {
		return !nbt.contains(COOLDOWN);
	}


}
