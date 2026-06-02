package net.dakotapride.machination.item;

import net.minecraft.world.entity.EntityType;

import java.util.Locale;

public enum DivineBeings {
    NONE,
    ENDER_DRAGON(EntityType.ENDER_DRAGON),
    WITHER(EntityType.WITHER),
    WARDEN(EntityType.WARDEN),
    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE),
    GUARDIAN(EntityType.GUARDIAN),;

    EntityType<?> entityType;

    DivineBeings(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    DivineBeings() {}

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public String getId() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
