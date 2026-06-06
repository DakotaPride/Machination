package net.dakotapride.machination.registrar;

import net.dakotapride.machination.Machination;
import net.dakotapride.machination.block.CobaltOreBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlocksRegistrar {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Machination.MOD_ID);

    public static RegistryObject<Block> COBALT_ORE = registerBlock("cobalt_ore", () -> new CobaltOreBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> DEEPSLATE_COBALT_ORE = registerBlock("deepslate_cobalt_ore", () -> new CobaltOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE).mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> NETHERRACK_COBALT_ORE = registerBlock("netherrack_cobalt_ore", () -> new CobaltOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> COBALT_BLOCK = registerBlock("cobalt_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemsRegistrar.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void worshipOurToxicYuriAIOverlords(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
