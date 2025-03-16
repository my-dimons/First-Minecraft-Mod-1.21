package net.mydimons.firstmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mydimons.firstmod.FirstMod;
import net.mydimons.firstmod.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup PINK_GARNET_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(FirstMod.MOD_ID, "pink_garnet_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.PINK_GARNET))
                    .displayName(Text.translatable("itemgroup.firstmod.pink_garnet_items"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.PINK_GARNET);
                        entries.add(ModItems.RAW_PINK_GARNET);
                    }))
                    .build());

    public static final ItemGroup PINK_GARNET_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(FirstMod.MOD_ID, "pink_garnet_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.PINK_GARNET_BLOCK))
                    .displayName(Text.translatable("itemgroup.firstmod.pink_garnet_blocks"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModBlocks.PINK_GARNET_BLOCK);
                        entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);
                        entries.add(ModBlocks.PINK_GARNET_ORE);
                        entries.add(ModBlocks.PINK_GARNET_DEEPSLATE_ORE);
                    }))
                    .build());

    public static final ItemGroup NEW_FOOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(FirstMod.MOD_ID, "new_foods"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BANANA))
                    .displayName(Text.translatable("itemgroup.firstmod.new_foods"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.BANANA);
                        entries.add(ModItems.GREEN_APPLE);
                    }))
                    .build());

    public static void registerItemGroups() {
        FirstMod.LOGGER.info("Registering Item Groups for " + FirstMod.MOD_ID);
    }
}
