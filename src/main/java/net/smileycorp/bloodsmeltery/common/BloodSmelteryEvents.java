package net.smileycorp.bloodsmeltery.common;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.bloodsmeltery.common.tcon.ModContent;
import net.smileycorp.bloodsmeltery.common.util.HellfireForgeFluidCapability;
import net.smileycorp.bloodsmeltery.common.util.TartaricFluidCapability;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import wayoftime.bloodmagic.common.item.soul.ItemSoulGem;
import wayoftime.bloodmagic.common.tile.TileSoulForge;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.event.SoulNetworkEvent;

@EventBusSubscriber(modid=Constants.MODID)
public class BloodSmelteryEvents {

	@SubscribeEvent
	public void attachStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack == null) return;
		Item item = stack.getItem();
		if (!(item instanceof ItemSoulGem)) return;
		TartaricFluidCapability cap = new TartaricFluidCapability(stack);
		event.addCapability(Constants.loc("TartaricFluid"), cap);
	}

	@SubscribeEvent
	public void attachBECapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
		BlockEntity tile = event.getObject();
		if (!(tile instanceof TileSoulForge)) return;
		HellfireForgeFluidCapability cap = new HellfireForgeFluidCapability((TileSoulForge) tile);
		event.addCapability(Constants.loc("HellfireFluid"), cap);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void siphonLP(SoulNetworkEvent.Syphon.User event) {
		SoulNetwork network = event.getNetwork();
		SoulTicket ticket = event.getTicket();
		int amount = ticket.getAmount();
		Player player = event.getUser();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			if (!(stack.getItem() instanceof IModifiable)) continue;
			ToolStack tool = ToolStack.from(stack);
			for (ModifierEntry entry : tool.getModifierList()) entry.getHook(ModContent.SPEND_LP_HOOK).spendLP(tool, entry, player, slot, stack, network, amount, event.shouldDamage());
		}
		if (amount != ticket.getAmount()) event.setTicket(new SoulTicket(ticket.getDescription(), amount));
	}

}