package net.smileycorp.bloodsmeltery.common.modifiers;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import org.apache.commons.compress.utils.Lists;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.SingleLevelModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.RomanNumeralHelper;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.api.compat.IDemonWill;
import wayoftime.bloodmagic.common.item.soul.ItemSentientPickaxe;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.common.item.soul.SentientTooltipHelper;
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class SentienceModifier extends SingleLevelModifier implements TooltipModifierHook, GeneralInteractionModifierHook, MeleeHitModifierHook,
		ProcessLootModifierHook, ToolStatsModifierHook, AttributesModifierHook, ModifierRemovalHook {

	private static final ResourceLocation SENTIENCE_DATA = Constants.loc("sentience");
	private static final UUID VENGEFUL_SPEED_ID = UUID.fromString("754d7a01-854a-47e5-8ed4-402955472030");
	private static final UUID STEADFAST_HEALTH_ID = UUID.fromString("1c733825-2a97-4473-b100-9f40ee26ed33");

	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.REMOVE, ModifierHooks.GENERAL_INTERACT, ModifierHooks.MELEE_HIT, ModifierHooks.PROCESS_LOOT,
				ModifierHooks.TOOL_STATS, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOLTIP);
	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @Nullable RegistryAccess access) {
		MutableComponent name = getDisplayName(entry.getLevel()).copy();
		EnumDemonWillType type = getWillType(tool);
		int tier = getTier(tool) + 1;
		if (tier > 0) name = name.append(" ").append(RomanNumeralHelper.getNumeral(tier));
		return name.withStyle(name.getStyle().withColor(TextColor.fromRgb(DemonWillUtils.getColour(type))));
	}

	@Override
	public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
		int tier = getTier(tool);
		if (tier < 0) return;
		EnumDemonWillType type = getWillType(tool);
		SentientTooltipHelper.appendSentientTooltip(tooltip, "", type, tier, DemonWillUtils.getBonusDamage(tier, type), null,
				tier >= 0 ? ItemSentientPickaxe.defaultDigSpeedAdded[tier] : null, ItemSentientSword.poisonTime[Math.max(0, tier)],
				ItemSentientSword.poisonLevel[Math.max(0, tier)], ItemSentientSword.absorptionTime[Math.max(0, tier)],
				type == EnumDemonWillType.VENGEFUL ? ItemSentientSword.movementSpeed[Math.max(0, tier)] : 0);
	}

	@Override
	public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
		recalcStats(tool, player, hand, true);
		return InteractionResult.PASS;
	}

	@Override
	public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
		Player player = context.getPlayerAttacker();
		LivingEntity target = context.getLivingTarget();
		CompoundTag nbt = tool.getPersistentData().getCompound(SENTIENCE_DATA);
		recalcStats(tool, player, context.getHand(), false);
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		if (tier >= 0) PlayerDemonWillHandler.consumeDemonWill(type, player, ItemSentientSword.soulDrainPerSwing[tier]);
		recalcStats(tool, player, context.getHand(), true);
		tier = getTier(tool);
		type = getWillType(tool);
		if (tier >= 0) {
			if (type == EnumDemonWillType.CORROSIVE) target.addEffect(new MobEffectInstance(MobEffects.WITHER, ItemSentientSword.poisonTime[tier], ItemSentientSword.poisonLevel[tier]));
			else if (type == EnumDemonWillType.STEADFAST && target.isDeadOrDying()) {
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ItemSentientSword.absorptionTime[tier], 127, false, false));
				player.setAbsorptionAmount((float) Math.min(player.getAbsorptionAmount() + target.getMaxHealth() * 0.05f, ItemSentientSword.maxAbsorptionHearts));
			}
		}
		tool.getPersistentData().put(SENTIENCE_DATA, nbt);
		if (!target.isDeadOrDying() |! context.isProjectile()) return;
		getDrops(tool, target, ForgeHooks.getLootingLevel(target, player, context.makeDamageSource())).forEach(target::spawnAtLocation);
	}

	@Override
	public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
		if (context.getParamOrNull(LootContextParams.DAMAGE_SOURCE) == null) return;
		LivingEntity target = (LivingEntity) context.getParamOrNull(LootContextParams.THIS_ENTITY);
		if (target == null) return;
		generatedLoot.addAll(getDrops(tool, target, context.getLootingModifier()));
	}

	private List<ItemStack> getDrops(IToolStackView tool, LivingEntity target, int looting) {
		List<ItemStack> items = Lists.newArrayList();
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		double multiplier = (target instanceof Slime ? 0.67 : 1d) * (tier >=  0 ? ItemSentientSword.soulDrop[tier] : 0);
		double healthBonus = (tier >= 0 ? ItemSentientSword.staticDrop[tier] : 1d) * target.getMaxHealth() / 20d;
		IDemonWill will = DemonWillUtils.getWillItem(type);
		// if tank is full, nothing to do
		FluidStack current = TANK_HELPER.getFluid(tool);
		int capacity = TANK_HELPER.getCapacity(tool);
		Fluid fluid = DemonWillUtils.getFluidForType(type);
		for (int i = 0; i <= looting; i++) if (i == 0 || RANDOM.nextDouble() < 0.4) {
			double amount = RANDOM.nextDouble() * multiplier + healthBonus;
			if (capacity > current.getAmount() && (current.isEmpty() || current.getFluid() == fluid)) {
				int fluidAmount = (int) (amount * BloodSmelteryConfig.willFluidAmount.get());
				int amountToAdd = Math.min(fluidAmount, capacity - current.getAmount());
				if (current.isEmpty()) current = new FluidStack(fluid, amountToAdd);
				else current.setAmount(current.getAmount() + amountToAdd);
				TANK_HELPER.setFluid(tool, current);
				amount = (double) (fluidAmount - amountToAdd) / (double) BloodSmelteryConfig.willFluidAmount.get();
			}
			if (amount <= 0) continue;
			items.add(will.createWill(amount));
		}
		return items;
	}

	@Override
	public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
		IModDataView persistentData = context.getPersistentData();
		int tier = getTier(persistentData);
		EnumDemonWillType type = getWillType(persistentData);
		if (tier < 0) return;
		ToolStats.ATTACK_DAMAGE.add(builder, DemonWillUtils.getBonusDamage(tier, type));
		double attackSpeed = DemonWillUtils.getAttackSpeedMultiplier(tier, type);
		if (attackSpeed != 1) ToolStats.ATTACK_SPEED.multiply(builder, attackSpeed);
		ToolStats.MINING_SPEED.add(builder, ItemSentientPickaxe.defaultDigSpeedAdded[tier]);
	}

	@Override
	public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		if (tier < 0) return;
		if (type == EnumDemonWillType.VENGEFUL) consumer.accept(Attributes.MOVEMENT_SPEED, new AttributeModifier(VENGEFUL_SPEED_ID,
				"Weapon modifier", ItemSentientSword.movementSpeed[tier], AttributeModifier.Operation.ADDITION));
		if (type == EnumDemonWillType.STEADFAST) consumer.accept(Attributes.MAX_HEALTH, new AttributeModifier(STEADFAST_HEALTH_ID, "Weapon modifier",
				ItemSentientSword.healthBonus[tier], AttributeModifier.Operation.ADDITION));
	}

	@Override
	public Component onRemoved(IToolStackView tool, Modifier modifier) {
		tool.getPersistentData().remove(SENTIENCE_DATA);
		return null;
	}

	protected void recalcStats(IToolStackView tool, Player player, InteractionHand hand, boolean recalcToolStats) {
		CompoundTag nbt = tool.getPersistentData().getCompound(SENTIENCE_DATA);
		EnumDemonWillType playerType = getWillType(tool, player);
		EnumDemonWillType toolType = nbt.contains("type") ? EnumDemonWillType.getType(nbt.getString("type")) : EnumDemonWillType.DEFAULT;
		if (playerType != toolType) nbt.putString("type", playerType.toString());
		double will = getTotalDemonWill(tool, playerType, player);
		nbt.putInt("tier", DemonWillUtils.getToolTier(will));
		if (recalcToolStats) getHeldTool(player, hand).rebuildStats();
		tool.getPersistentData().put(SENTIENCE_DATA, nbt);
	}

	private double getTotalDemonWill(IToolStackView tool, EnumDemonWillType type, Player player) {
		double will = PlayerDemonWillHandler.getTotalDemonWill(type, player);
		FluidStack stack = TANK_HELPER.getFluid(tool);
		if (!stack.isEmpty() && stack.getFluid().is(DemonWillUtils.getTagForType(type)))
			will += (double) stack.getAmount() / (double) BloodSmelteryConfig.willFluidAmount.get();
		return will;
	}

	private EnumDemonWillType getWillType(IToolStackView tool, Player player) {
		FluidStack stack = TANK_HELPER.getFluid(tool);
		if (!stack.isEmpty()) {
			EnumDemonWillType type = DemonWillUtils.getTypeForFluid(stack);
			if (type != null) return type;
		}
		return PlayerDemonWillHandler.getLargestWillType(player);
	}

	public static int getTier(IToolStackView tool) {
		return getTier(tool.getPersistentData());
	}

	public static EnumDemonWillType getWillType(IToolStackView tool) {
		return getWillType(tool.getPersistentData());
	}

	protected static int getTier(IModDataView data) {
		CompoundTag nbt = data.getCompound(SENTIENCE_DATA);
		return nbt.contains("tier") ? nbt.getInt("tier") : -1;
	}

	protected static EnumDemonWillType getWillType(IModDataView data) {
		CompoundTag nbt = data.getCompound(SENTIENCE_DATA);
		return nbt.contains("type") ? EnumDemonWillType.getType(nbt.getString("type")) : EnumDemonWillType.DEFAULT;
	}

}
