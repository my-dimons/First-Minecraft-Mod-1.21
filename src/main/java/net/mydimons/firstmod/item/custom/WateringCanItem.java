package net.mydimons.firstmod.item.custom;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.AbstractMap;
import java.util.Map;

public class WateringCanItem extends Item {
    private static final Map<Block, Block> WATERING_CAN =
            Map.ofEntries (
                    new AbstractMap.SimpleEntry<>(Blocks.CYAN_CONCRETE_POWDER, Blocks.CYAN_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.BLACK_CONCRETE_POWDER, Blocks.BLACK_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.BLUE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.BROWN_CONCRETE_POWDER, Blocks.BROWN_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.GRAY_CONCRETE_POWDER, Blocks.GRAY_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.GREEN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.LIME_CONCRETE_POWDER, Blocks.LIME_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.MAGENTA_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.ORANGE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.PINK_CONCRETE_POWDER, Blocks.PINK_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.PURPLE_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.RED_CONCRETE_POWDER, Blocks.RED_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.WHITE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE),
                    new AbstractMap.SimpleEntry<>(Blocks.YELLOW_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE)
            );

    public WateringCanItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getBlockPos());
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();
        BlockPos pos = context.getBlockPos();

        // USED ON ANY POWDERED CONCRETE BLOCK (SETS IT TO ITS CONCRETE COUNTERPART)
        if (WATERING_CAN.containsKey(clickedBlock) && context.getStack().getDamage() < context.getStack().getMaxDamage() - 1) {
            if (!world.isClient()) {
                world.setBlockState(context.getBlockPos(), WATERING_CAN.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS);
            }
            return ActionResult.SUCCESS;
        }
        // USED ON FARMLAND (WATERS FARMLAND)
        else if (state.getBlock() instanceof FarmlandBlock) {
            int currentMoisture = state.get(Properties.MOISTURE);

            if (!world.isClient()) {
                if (currentMoisture < 7) {
                    System.out.println("Current Moisture Value: " + currentMoisture);
                    world.setBlockState(pos, state.with(Properties.MOISTURE, 7));
                    System.out.println("New Moisture Value: " + currentMoisture);
                    context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                            item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                    world.playSound(null, context.getBlockPos(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS);

                    return ActionResult.SUCCESS;
                }
            }

        }
        // USED ON CAULDRON (REFILLS WATERING CAN)
        else if (state.getBlock() instanceof LeveledCauldronBlock) {
            int waterLevel = state.get(Properties.LEVEL_3);
            if (waterLevel > 0 && context.getStack().getDamage() > 0) {
                // Repairs watering can durability by 20
                context.getStack().setDamage(Math.max(context.getStack().getDamage() - 20, 0));
                // Decrease the cauldron's water level by 1
                ((LeveledCauldronBlock) state.getBlock()).decrementFluidLevel(state, world, pos);

                System.out.println("Current water level: " + waterLevel);
                world.playSound(null, context.getBlockPos(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS);
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockHitResult hitResult = (BlockHitResult) player.raycast(5.0D, 1.0F, true);
        BlockPos pos = hitResult.getBlockPos();
        FluidState fluidState = world.getFluidState(pos);

        // WHEN USED ON WATER (REFILLS WATERING CAN)
        if (fluidState.getFluid() == Fluids.WATER) {
            // Reset the item's damage value to regain all durability
            itemStack.setDamage(0);

            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS);

            // Return SUCCESS with the item stack
            return TypedActionResult.success(itemStack);
        }

        return super.use(world, player, hand);
    }
}
