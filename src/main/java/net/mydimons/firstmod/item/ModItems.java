package net.mydimons.firstmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mydimons.firstmod.FirstMod;
import net.mydimons.firstmod.item.custom.ChiselItem;
import net.mydimons.firstmod.item.custom.WateringCanItem;

public class ModItems {
    // ITEMS
    public static final Item PINK_GARNET = registerItem("pink_garnet", new Item(new Item.Settings()));
    public static final Item RAW_PINK_GARNET = registerItem("raw_pink_garnet", new Item(new Item.Settings()));
    public static final Item BANANA = registerItem("banana", new Item(new Item.Settings()));
    public static final Item GREEN_APPLE = registerItem("green_apple", new Item(new Item.Settings()));

    public static final Item CHISEL = registerItem("chisel", new ChiselItem(new Item.Settings().maxDamage(100)));
    public static final Item WATERING_CAN = registerItem("watering_can", new WateringCanItem(new Item.Settings().maxDamage(60)));

    // REGISTER
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(FirstMod.MOD_ID, name), item);
    }

    // REGISTER EACH ITEM
    public static void registerModItems() {
        FirstMod.LOGGER.info("Registering Mod Items for " + FirstMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(PINK_GARNET);
            fabricItemGroupEntries.add(RAW_PINK_GARNET);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(BANANA);
            fabricItemGroupEntries.add(GREEN_APPLE);
        }));
    }

}
