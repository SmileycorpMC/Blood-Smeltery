package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.smileycorp.bloodsmeltery.common.DemonWillUtils;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.tconstruct.library.modifiers.SingleLevelModifier;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModDataReadOnly;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
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
		super(0x4EF6FF);
	}


	@Override
	public ITextComponent getDisplayName(IModifierToolStack tool, int level) {
		IFormattableTextComponent name = (IFormattableTextComponent) getDisplayName(level).copy();
		EnumDemonWillType type = getWillType(tool);
		int tier = getTier(tool) + 1;
		if (tier > 0) name = name.append(" ").append(new TranslationTextComponent("enchantment.level."+tier));
		return name.withStyle(name.getStyle().withColor(Color.fromRgb(DemonWillUtils.getColour(type))));
	}

	@Override
	public void onRemoved(IModifierToolStack tool) {
		tool.getPersistentData().remove(SENTIENT_DATA);
	}


	@Override
	public ActionResultType onToolUse(IModifierToolStack tool, int level, World world, PlayerEntity player, Hand hand, EquipmentSlotType slot) {
		recalcStats(tool, player, hand, true);
		return ActionResultType.SUCCESS;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		PlayerEntity player = context.getPlayerAttacker();
		LivingEntity target = context.getLivingTarget();
		CompoundNBT nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
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
				target.addEffect(new EffectInstance(Effects.WITHER, ItemSentientSword.poisonTime[tier], ItemSentientSword.poisonLevel[tier]));
			} else if (type == EnumDemonWillType.STEADFAST && target.isDeadOrDying()) {
				float absorption = player.getAbsorptionAmount();
				player.addEffect(new EffectInstance(Effects.ABSORPTION, ItemSentientSword.absorptionTime[tier], 127, false, false));
				player.setAbsorptionAmount((float) Math.min(absorption + target.getMaxHealth() * 0.05f, ItemSentientSword.maxAbsorptionHearts));
			}
		}
		tool.getPersistentData().put(SENTIENT_DATA, nbt);
		return 0;
	}

	@Override
	public List<ItemStack> processLoot(IModifierToolStack tool, int level, List<ItemStack> drops, LootContext context) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		IDemonWill will = DemonWillUtils.getWillItem(type);
		LivingEntity target = (LivingEntity) context.getParamOrNull(LootParameters.THIS_ENTITY);
		if (target != null) {
			for (int i = 0; i <= context.getLootingModifier(); i++) {
				if (i == 0 || RANDOM.nextDouble() < 0.4) {
					ItemStack drop = will.createWill((target instanceof SlimeEntity ? 0.67 : 1) * (tier >=  0 ? ItemSentientSword.soulDrop[tier] : 0) * RANDOM.nextDouble()
							+ (tier >=  0 ? ItemSentientSword.staticDrop[tier] : 1) * target.getMaxHealth() / 20d);
					drops.add(drop);
				}
			}
		}
		return drops;
	}

	@Override
	public void addToolStats(Item item, ToolDefinition toolDefinition, StatsNBT baseStats, IModDataReadOnly persistentData, IModDataReadOnly volatileData, int level, ModifierStatsBuilder builder) {
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
	public void addAttributes(IModifierToolStack tool, int level, EquipmentSlotType slot, BiConsumer<Attribute,AttributeModifier> consumer) {
		int tier = getTier(tool);
		EnumDemonWillType type = getWillType(tool);
		if (tier >= 0 && type == EnumDemonWillType.VENGEFUL) {
			consumer.accept(Attributes.MOVEMENT_SPEED, new AttributeModifier(new UUID(0, 4218052), "Weapon modifier", ItemSentientSword.movementSpeed[tier], AttributeModifier.Operation.ADDITION));
		}
	}

	protected void recalcStats(IModifierToolStack tool, PlayerEntity player, Hand hand, boolean recalcToolStats) {
		CompoundNBT nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
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

	public static int getTier(IModifierToolStack tool) {
		return getTier(tool.getPersistentData());
	}

	public static EnumDemonWillType getWillType(IModifierToolStack tool) {
		return getWillType(tool.getPersistentData());
	}


	protected static int getTier(IModDataReadOnly data) {
		CompoundNBT nbt = data.getCompound(SENTIENT_DATA);
		int tier = -1;
		if (nbt.contains("tier")) {
			tier = nbt.getInt("tier");
		}
		return tier;
	}

	protected static EnumDemonWillType getWillType(IModDataReadOnly data) {
		CompoundNBT nbt = data.getCompound(SENTIENT_DATA);
		EnumDemonWillType type = EnumDemonWillType.DEFAULT;
		if (nbt.contains("type")) {
			type = EnumDemonWillType.getType(nbt.getString("type"));
		}
		return type;
	}

}
