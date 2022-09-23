package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.client.book.transformer.BookTransformer;
import slimeknights.mantle.item.LecternBookItem;
import slimeknights.tconstruct.shared.TinkerCommons;

public class GuideBook extends LecternBookItem {

	public static BookData BOOK_DATA = null;

	private static BookData createBookData() {
		ResourceLocation loc = ModDefinitions.getResource("guide_book");
		BookData book = BookLoader.registerBook(loc, false, false, new FileRepository(ModDefinitions.getResource("book/" + loc.getPath())));
		book.addTransformer(BookTransformer.indexTranformer());
		book.addTransformer(BookTransformer.paddingTransformer());
		book.addTransformer(new SingleMaterialSectionTransformer(ModDefinitions.getResource("blood_stone")));
		book.addTransformer(new SingleMaterialSectionTransformer(ModDefinitions.getResource("blood_brass")));
		//book.addTransformer(new ModifierSectionTransformer("upgrades"));
		return book;
	}

	public GuideBook() {
		super(new Properties().tab(TinkerCommons.TAB_GENERAL).stacksTo(1));
		if (BOOK_DATA == null) BOOK_DATA = createBookData();
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (world.isClientSide) {
			BOOK_DATA.openGui(hand, stack);
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@Override
	public void openLecternScreenClient(BlockPos pos, ItemStack stack) {
		BOOK_DATA.openGui(pos, stack);
	}

}
