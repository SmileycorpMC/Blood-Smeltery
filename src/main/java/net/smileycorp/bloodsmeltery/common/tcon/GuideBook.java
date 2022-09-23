package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smileycorp.bloodsmeltery.common.ModDefinitions;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.BookTransformer;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.item.LecternBookItem;
import slimeknights.tconstruct.shared.TinkerCommons;

public class GuideBook extends LecternBookItem {

	public static BookData BOOK_DATA = null;

	private static BookData createBookData() {
		ResourceLocation loc = ModDefinitions.getResource("guide_book");
		BookData book = BookLoader.registerBook(loc.toString(), false, false);
		book.addRepository(new FileRepository(loc.getNamespace() + ":book/" + loc.getPath()));
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
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (world.isClientSide) {
			System.out.println(BOOK_DATA.toString());
			BOOK_DATA.openGui(hand, stack);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public void openLecternScreenClient(BlockPos pos, ItemStack stack) {
		BOOK_DATA.openGui(pos, stack);
	}

}
