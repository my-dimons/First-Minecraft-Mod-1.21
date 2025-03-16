package net.mydimons.firstmod;

import net.fabricmc.api.ModInitializer;

import net.mydimons.firstmod.block.ModBlocks;
import net.mydimons.firstmod.item.ModItemGroups;
import net.mydimons.firstmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstMod implements ModInitializer {
	public static final String MOD_ID = "firstmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
	}
}