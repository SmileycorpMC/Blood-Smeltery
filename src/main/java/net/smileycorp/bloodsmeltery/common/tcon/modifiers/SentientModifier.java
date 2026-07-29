package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

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
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
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
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class SentientModifier extends SingleLevelModifier implements ModifierRemovalHook, GeneralInteractionModifierHook, MeleeHitModifierHook,
		ProcessLootModifierHook, ToolStatsModifierHook, AttributesModifierHook {

	private static final ResourceLocation SENTIENT_DATA = Constants.loc("sentient");
	private static final UUID VENGEFUL_SPEED_ID = UUID.fromString("754d7a01-854a-47e5-8ed4-402955472030");

	protected void registerHooks(ModuleHookMap.Builder builder) {
		super.registerHooks(builder);
		builder.addHook(this, ModifierHooks.REMOVE, ModifierHooks.GENERAL_INTERACT, ModifierHooks.MELEE_HIT, ModifierHooks.PROCESS_LOOT,
				ModifierHooks.TOOL_STATS, ModifierHooks.ATTRIBUTES);
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
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Component onRemoved(IToolStackView tool, Modifier modifier) {
		tool.getPersistentData().remove(SENTIENT_DATA);
		return null;
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
		CompoundTag nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
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
				float absorption = player.getAbsorptionAmount();
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ItemSentientSword.absorptionTime[tier], 127, false, false));
				player.setAbsorptionAmount((float) Math.min(absorption + target.getMaxHealth() * 0.05f, ItemSentientSword.maxAbsorptionHearts));
			}
		}
		tool.getPersistentData().put(SENTIENT_DATA, nbt);
	}

	@Override
	public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		IDemonWill will = DemonWillUtils.getWillItem(type);
		if (context.getParamOrNull(LootContextParams.DAMAGE_SOURCE) != null) {
			LivingEntity target = (LivingEntity) context.getParamOrNull(LootContextParams.THIS_ENTITY);
			for (int i = 0; i <= context.getLootingModifier(); i++) {
				if (i == 0 || RANDOM.nextDouble() < 0.4) {
					ItemStack drop = will.createWill((target instanceof Slime ? 0.67 : 1d) * (tier >=  0 ? ItemSentientSword.soulDrop[tier] : 0) * RANDOM.nextDouble()
							+ (tier >=  0 ? ItemSentientSword.staticDrop[tier] : 1d) * target.getMaxHealth() / 20d);
					generatedLoot.add(drop);
				}
			}
		}
	}

	@Override
	public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
		IModDataView persistentData = context.getPersistentData();
		int tier = getTier(persistentData);
		EnumDemonWillType type = getWillType(persistentData);
		if (tier < 0) return;
		ToolStats.ATTACK_DAMAGE.add(builder, DemonWillUtils.getBonusDamage(tier, type));
		double attackSpeed = DemonWillUtils.getAttackSpeedMultiplier(tier, type);
		if (attackSpeed!=1) ToolStats.ATTACK_SPEED.multiply(builder, attackSpeed);
		ToolStats.MINING_SPEED.add(builder, ItemSentientPickaxe.defaultDigSpeedAdded[ tier < 5 ? tier : 4]);
	}

	@Override
	public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		if (tier >= 0 && type == EnumDemonWillType.VENGEFUL) consumer.accept(Attributes.MOVEMENT_SPEED, new AttributeModifier(VENGEFUL_SPEED_ID,
				"Weapon modifier", ItemSentientSword.movementSpeed[tier], AttributeModifier.Operation.ADDITION));
	}

	protected void recalcStats(IToolStackView tool, Player player, InteractionHand hand, boolean recalcToolStats) {
		CompoundTag nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
		EnumDemonWillType player_type = PlayerDemonWillHandler.getLargestWillType(player);
		EnumDemonWillType tool_type = EnumDemonWillType.DEFAULT;
		if (nbt.contains("type")) tool_type = EnumDemonWillType.getType(nbt.getString("type"));
		if (player_type != tool_type) nbt.putString("type", player_type.toString());
		double will = PlayerDemonWillHandler.getTotalDemonWill(player_type, player);
		nbt.putInt("tier", DemonWillUtils.getToolTier(will));
		if (recalcToolStats) getHeldTool(player, hand).rebuildStats();
		tool.getPersistentData().put(SENTIENT_DATA, nbt);
	}

	public static int getTier(IToolStackView tool) {
		return getTier(tool.getPersistentData());
	}

	public static EnumDemonWillType getWillType(IToolStackView tool) {
		return getWillType(tool.getPersistentData());
	}

	protected static int getTier(IModDataView data) {
		CompoundTag nbt = data.getCompound(SENTIENT_DATA);
		return nbt.contains("tier") ? nbt.getInt("tier") : -1;
	}

	protected static EnumDemonWillType getWillType(IModDataView data) {
		CompoundTag nbt = data.getCompound(SENTIENT_DATA);
		return nbt.contains("type") ? EnumDemonWillType.getType(nbt.getString("type")) : EnumDemonWillType.DEFAULT;
	}

}
