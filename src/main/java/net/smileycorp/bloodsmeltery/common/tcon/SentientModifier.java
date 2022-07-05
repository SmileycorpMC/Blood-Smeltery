package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.smileycorp.bloodsmeltery.common.DemonWillUtils;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.tconstruct.library.modifiers.impl.SingleLevelModifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.api.compat.IDemonWill;
import wayoftime.bloodmagic.common.item.soul.ItemSentientPickaxe;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

public class SentientModifier extends SingleLevelModifier {

	private static final ResourceLocation SENTIENT_DATA = ModDefinitions.getResource("sentient");

	public SentientModifier() {
		super();
	}


	@Override
	public MutableComponent getDisplayName(IToolStackView tool, int level) {
		MutableComponent name = getDisplayName(level).copy();
		EnumDemonWillType type = getWillType(tool);
		int tier = getTier(tool) + 1;
		if (tier > 0) name = name.append(" ").append(new TranslatableComponent("enchantment.level."+tier));
		return name.withStyle(name.getStyle().withColor(DemonWillUtils.getColour(type)));
	}

	@Override
	public void onRemoved(IToolStackView tool) {
		tool.getPersistentData().remove(SENTIENT_DATA);
	}


	@Override
	public InteractionResult onToolUse(IToolStackView tool, int level, Level world, Player player, InteractionHand hand, EquipmentSlot slot) {
		recalcStats(tool, player, hand, true);
		return InteractionResult.SUCCESS;
	}

	@Override
	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		Player player = context.getPlayerAttacker();
		LivingEntity target = context.getLivingTarget();
		CompoundTag nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
		recalcStats(tool, player, context.getHand(), false);
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		if (tier >= 0) {
			PlayerDemonWillHandler.consumeDemonWill(type, player, ItemSentientSword.soulDrainPerSwing[tier]);
		}
		recalcStats(tool, player, context.getHand(), true);
		tier = getTier(tool);
		type = getWillType(tool);
		if (tier >= 0) {
			if (type == EnumDemonWillType.CORROSIVE) {
				target.addEffect(new MobEffectInstance(MobEffects.WITHER, ItemSentientSword.poisonTime[tier], ItemSentientSword.poisonLevel[tier]));
			} else if (type == EnumDemonWillType.STEADFAST && target.isDeadOrDying()) {
				float absorption = player.getAbsorptionAmount();
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ItemSentientSword.absorptionTime[tier], 127, false, false));
				player.setAbsorptionAmount((float) Math.min(absorption + target.getMaxHealth() * 0.05f, ItemSentientSword.maxAbsorptionHearts));
			}
		}
		tool.getPersistentData().put(SENTIENT_DATA, nbt);
		return 0;
	}

	@Override
	public List<ItemStack> processLoot(IToolStackView tool, int level, List<ItemStack> drops, LootContext context) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		IDemonWill will = DemonWillUtils.getWillItem(type);
		LivingEntity target = (LivingEntity) context.getParamOrNull(LootContextParams.THIS_ENTITY);
		if (target != null) {
			for (int i = 0; i <= context.getLootingModifier(); i++) {
				if (i == 0 || RANDOM.nextDouble() < 0.4) {
					ItemStack drop = will.createWill((target instanceof Slime ? 0.67 : 1) * (tier >=  0 ? ItemSentientSword.soulDrop[tier] : 0) * RANDOM.nextDouble()
							+ (tier >=  0 ? ItemSentientSword.staticDrop[tier] : 1) * target.getMaxHealth() / 20d);
					drops.add(drop);
				}
			}
		}
		return drops;
	}

	@Override
	public void addToolStats(Item item, ToolDefinition toolDefinition, StatsNBT baseStats, IModDataView persistentData, IModDataView volatileData, int level, ModifierStatsBuilder builder) {
		int tier = getTier(persistentData);
		EnumDemonWillType type = getWillType(persistentData);
		if (tier >= 0) {
			ToolStats.ATTACK_DAMAGE.add(builder, DemonWillUtils.getBonusDamage(tier, type));
			double attackSpeed = DemonWillUtils.getAttackSpeedMultiplier(tier, type);
			if (attackSpeed!=1) ToolStats.ATTACK_SPEED.multiply(builder, attackSpeed);
			ToolStats.MINING_SPEED.add(builder, ItemSentientPickaxe.defaultDigSpeedAdded[ tier < 5 ? tier : 4]);
		}
	}

	@Override
	public void addAttributes(IToolStackView tool, int level, EquipmentSlot slot, BiConsumer<Attribute,AttributeModifier> consumer) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		if (tier >= 0 && type == EnumDemonWillType.VENGEFUL) {
			consumer.accept(Attributes.MOVEMENT_SPEED, new AttributeModifier(new UUID(0, 4218052), "Weapon modifier", ItemSentientSword.movementSpeed[tier], AttributeModifier.Operation.ADDITION));
		}
	}

	protected void recalcStats(IToolStackView tool, Player player, InteractionHand hand, boolean recalcToolStats) {
		CompoundTag nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
		EnumDemonWillType player_type = PlayerDemonWillHandler.getLargestWillType(player);
		EnumDemonWillType tool_type = EnumDemonWillType.DEFAULT;
		if (nbt.contains("type")) {
			tool_type = EnumDemonWillType.getType(nbt.getString("type"));
		}
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
		int tier = -1;
		if (nbt.contains("tier")) {
			tier = nbt.getInt("tier");
		}
		return tier;
	}

	protected static EnumDemonWillType getWillType(IModDataView data) {
		CompoundTag nbt = data.getCompound(SENTIENT_DATA);
		EnumDemonWillType type = EnumDemonWillType.DEFAULT;
		if (nbt.contains("type")) {
			type = EnumDemonWillType.getType(nbt.getString("type"));
		}
		return type;
	}

}
