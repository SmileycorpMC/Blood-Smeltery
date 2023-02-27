package net.smileycorp.bloodsmeltery.client;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.smileycorp.bloodsmeltery.common.Constants;
import net.smileycorp.bloodsmeltery.common.tcon.modifiers.PlayerBoundModifier;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager.ModifierModelRegistrationEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID, bus = Bus.MOD)
public class ClientEventListener {

	@SubscribeEvent
	public static void registerModifierModels(ModifierModelRegistrationEvent event) {
		event.registerModel(Constants.loc("sentient"), SentientModifierModel.UNBAKED_INSTANCE);
	}

	@SubscribeEvent
	public void renderTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack != null) {
			if (stack.getItem() instanceof IModifiable) {
				ToolStack tool = ToolStack.from(stack);
				if (tool != null) {
					for (ModifierEntry entry : tool.getModifierList()) {
						if (entry.getModifier() instanceof PlayerBoundModifier) {
							PlayerBoundModifier modifier = (PlayerBoundModifier) entry.getModifier();
							if (modifier.isBound(tool)) {
								List<Component> tooltips = event.getToolTip();
								tooltips.add(tooltips.size()-6, new TranslatableComponent("tooltip.bloodmagic.currentOwner", modifier.getOwner(tool)));
								return;
							}
						}
					}
				}
			}
		}
	}

}
