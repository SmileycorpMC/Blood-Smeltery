package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.bloodsmeltery.common.DemonWillUtils;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

public class SentientModifier extends Modifier {

	private final ResourceLocation SENTIENT_DATA = ModDefinitions.getResource("sentient");

	public SentientModifier() {
		super(0x4EF6FF);
	}

	@Override
	public void onRemoved(IModifierToolStack tool) {
		tool.getPersistentData().remove(SENTIENT_DATA);
	}

	@Override
	public float beforeEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
		CompoundNBT nbt = tool.getPersistentData().getCompound(SENTIENT_DATA);
		PlayerEntity player = context.getPlayerAttacker();
		if (nbt == null) nbt = new CompoundNBT();
		if (player != null) {
			EnumDemonWillType player_type = PlayerDemonWillHandler.getLargestWillType(player);
			EnumDemonWillType tool_type = EnumDemonWillType.DEFAULT;
			if (nbt.contains("type")) {
				tool_type = EnumDemonWillType.getType(nbt.getString("type"));
			}
			if (player_type != tool_type) nbt.putString("type", player_type.toString());
			double will = PlayerDemonWillHandler.getTotalDemonWill(player_type, player);
			nbt.putInt("tier", DemonWillUtils.getToolTier(will));
		}
		tool.getPersistentData().put(SENTIENT_DATA, nbt);
		return knockback;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		//ModDataNBT nbt = tool.getPersistentData();
		return 0;
	}

}
