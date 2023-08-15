package net.smileycorp.bloodsmeltery.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.bloodsmeltery.common.util.HellfireForgeFluidCapability;
import net.smileycorp.bloodsmeltery.common.util.TartaricFluidCapability;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;
import wayoftime.bloodmagic.common.tile.TileSoulForge;

@EventBusSubscriber(modid=Constants.MODID)
public class BloodSmelteryEvents {

	@SubscribeEvent
	public void attachStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemSoulGem) {
				TartaricFluidCapability cap = new TartaricFluidCapability(stack);
				event.addCapability(Constants.loc("TartaricFluid"), cap);
			}
		}
	}

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
		BlockEntity tile = event.getObject();
		if (tile instanceof TileSoulForge) {
			HellfireForgeFluidCapability cap = new HellfireForgeFluidCapability((TileSoulForge) tile);
			event.addCapability(Constants.loc("HellfireFluid"), cap);
		}
	}

}