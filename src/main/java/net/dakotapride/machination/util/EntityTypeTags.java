package net.dakotapride.machination.util;

import net.dakotapride.machination.Machination;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTypeTags {
    public static final TagKey<EntityType<?>> DIVINE_BEINGS = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "divine_beings"));
    public static final TagKey<EntityType<?>> BLACKLISTED_PHIAL_ENTITIES = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "phial/damage_blacklist"));
}
