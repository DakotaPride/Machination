package net.dakotapride.machination.util;

import net.dakotapride.machination.Machination;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTags {
    public static final TagKey<Block> SCULK_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "sculk_blocks"));
}
