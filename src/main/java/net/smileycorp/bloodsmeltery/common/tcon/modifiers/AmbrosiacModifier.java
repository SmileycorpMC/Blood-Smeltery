package net.smileycorp.bloodsmeltery.common.tcon.modifiers;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.tcon.ModContent;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.hook.SpendLPModifierHook;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.SoulNetwork;

public class AmbrosiacModifier extends Modifier implements SpendLPModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModContent.SPEND_LP_HOOK);
    }

    @Override
    public int spendLP(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot, ItemStack stack, SoulNetwork network, int amountDrained, boolean dealDamage) {
        if (stack.isDamaged() && amountDrained >= BloodSmelteryConfig.ambrosiacLPThreshold.get() &&
                network.getCurrentEssence() >= BloodSmelteryConfig.ambrosiacLPThreshold.get()) stack.setDamageValue(stack.getDamageValue() - 1);
        return amountDrained;
    }

}
