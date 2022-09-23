package net.smileycorp.bloodsmeltery.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.bloodsmeltery.common.util.TartaricFluidCapability;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;

@EventBusSubscriber(modid=ModDefinitions.MODID)
public class BloodSmelteryEvents {

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemSoulGem) {
				TartaricFluidCapability cap = new TartaricFluidCapability(stack);
				event.addCapability(ModDefinitions.getResource("TartaricFluid"), cap);
			}
		}
	}

}