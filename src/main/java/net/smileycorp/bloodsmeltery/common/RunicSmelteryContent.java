package net.smileycorp.bloodsmeltery.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.WallBuildingBlockObject;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.component.SearedBlock;

public class RunicSmelteryContent {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
    public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Constants.MODID);

    private static final BlockBehaviour.Properties BLOOD_SEARED_PROPS = BlockBehaviour.Properties.of(Material.STONE)
            .strength(2.0F, 5.0F).sound(SoundType.STONE).requiresCorrectToolForDrops();

    public static final RegistryObject<Item> BLOOD_SEARED_BRICK = ITEMS.register("blood-seared_brick", () -> new Item(new Item.Properties().tab(TinkerSmeltery.TAB_SMELTERY)));

    public static final WallBuildingBlockObject BLOOD_SEARED_BRICKS = BLOCKS.registerWallBuilding("seared_bricks", () -> new SearedBlock(BLOOD_SEARED_PROPS),
            (block) -> new BlockTooltipItem(block, new Item.Properties().tab(TinkerSmeltery.TAB_SMELTERY)));

}
