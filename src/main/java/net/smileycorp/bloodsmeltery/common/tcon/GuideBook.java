package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.item.LecternBookItem;
import slimeknights.tconstruct.shared.TinkerCommons;

public class GuideBook extends LecternBookItem {

	public static final BookData BOOK_DATA = BookLoader.registerBook(ModDefinitions.getResource("guide_book").toString(), false, false);

	public GuideBook() {
		super(new Properties().tab(TinkerCommons.TAB_GENERAL).stacksTo(1));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (world.isClientSide) {
			BOOK_DATA.openGui(hand, stack);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public void openLecternScreenClient(BlockPos pos, ItemStack stack) {
		BOOK_DATA.openGui(pos, stack);
	}

}
