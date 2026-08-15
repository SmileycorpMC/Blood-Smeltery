package net.smileycorp.bloodsmeltery.common.tcon.modifiers.hook;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.core.data.SoulNetwork;

public interface SpendLPModifierHook {

    //called whenever a player spends lp in their soul network, return the amount of lp to drain
    int spendLP(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot, ItemStack stack, SoulNetwork network, int amountDrained, boolean dealDamage);

    static int defaultInstance(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, Player player, SoulNetwork network, int amountDrained, boolean dealDamage) {
        return amountDrained;
    }

}
