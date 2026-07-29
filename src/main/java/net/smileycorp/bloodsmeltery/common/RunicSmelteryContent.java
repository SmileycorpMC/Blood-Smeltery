package net.smileycorp.bloodsmeltery.common;

import com.google.common.base.Function;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.mantle.registration.object.WallBuildingBlockObject;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.shared.block.ClearGlassPaneBlock;
import slimeknights.tconstruct.smeltery.block.component.SearedBlock;
import slimeknights.tconstruct.smeltery.block.component.SearedGlassBlock;
import slimeknights.tconstruct.smeltery.block.component.SearedLadderBlock;

public class RunicSmelteryContent {
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
    public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Constants.MODID);

    private static final BlockBehaviour.Properties PROPS = BlockBehaviour.Properties.of()
            .strength(2f, 5f).sound(SoundType.STONE).requiresCorrectToolForDrops();
    private static final BlockBehaviour.Properties NON_SOLID_PROPS = PROPS.noOcclusion().isValidSpawn((s,g,p,e) -> false)
            .isRedstoneConductor((s,g,p) -> false).isSuffocating((s,g,p) -> false).isViewBlocking((s,g,p) -> false);
    
    private static final Function<Block, BlockTooltipItem> TOOLTIP = RunicSmelteryContent::tooltip;

    public static final RegistryObject<Item> BLOOD_SEARED_BRICK = ITEMS.register("blood_seared_brick",
            () -> new Item(new Item.Properties()));
    
    public static final WallBuildingBlockObject BLOOD_SEARED_BRICKS = BLOCKS.registerWallBuilding("blood_seared_bricks",
            () -> new SearedBlock(PROPS, false), TOOLTIP);
    
    public static final ItemObject<Block> CRACKED_BLOOD_SEARED_BRICKS = BLOCKS.register("cracked_blood_seared_bricks", PROPS, TOOLTIP);
    
    public static final ItemObject<Block> FANCY_BLOOD_SEARED_BRICKS = BLOCKS.register("fancy_blood_seared_bricks", PROPS, TOOLTIP);
   
    public static final ItemObject<Block> TRIANGLE_BLOOD_SEARED_BRICKS = BLOCKS.register("triangle_blood_seared_bricks", PROPS, TOOLTIP);
    
    public static final ItemObject<SearedLadderBlock> BLOOD_SEARED_LADDER = BLOCKS.register("blood_seared_ladder",
            () -> new SearedLadderBlock(NON_SOLID_PROPS), TOOLTIP);
    
    public static final ItemObject<SearedGlassBlock> BLOOD_SEARED_GLASS = BLOCKS.register("blood_seared_glass",
            () -> new SearedGlassBlock(NON_SOLID_PROPS.sound(SoundType.GLASS)), TOOLTIP);
    
    public static final ItemObject<ClearGlassPaneBlock> BLOOD_SEARED_GLASS_PANE = BLOCKS.register("blood_seared_glass_pane",
            () -> new ClearGlassPaneBlock(NON_SOLID_PROPS.sound(SoundType.GLASS)), TOOLTIP);

    private static BlockTooltipItem tooltip(Block block) {
        return new BlockTooltipItem(block, new Item.Properties());
    }
    
}
