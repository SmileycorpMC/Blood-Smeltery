package net.smileycorp.bloodsmeltery.integration.thermal;

import cofh.core.common.item.CoinItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.bloodsmeltery.common.Constants;

public class ThermalIntegration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

    public static final RegistryObject<Item> BLOODBRASS_COIN = ITEMS.register("bloodbrass_coin", () -> new CoinItem(new Item.Properties()));

}
