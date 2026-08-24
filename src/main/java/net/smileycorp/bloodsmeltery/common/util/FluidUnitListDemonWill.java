package net.smileycorp.bloodsmeltery.common.util;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryConfig;
import net.smileycorp.bloodsmeltery.common.BloodSmelteryTags;
import slimeknights.mantle.fluid.tooltip.FluidUnitList;
import wayoftime.bloodmagic.util.ChatUtil;

import java.util.List;

public class FluidUnitListDemonWill extends FluidUnitList {

    public FluidUnitListDemonWill() {
        super(BloodSmelteryTags.DEMON_WILL, Lists.newArrayList());
    }

    @Override
    public int getText(List<Component> tooltip, int amount) {
        tooltip.add(Component.translatable("gui.bloodsmeltery.fluid.will", ChatUtil.DECIMAL_FORMAT.format((float) amount
                / (float) BloodSmelteryConfig.willFluidAmount.get())).withStyle(ChatFormatting.GRAY));
        return 0;
    }

}
