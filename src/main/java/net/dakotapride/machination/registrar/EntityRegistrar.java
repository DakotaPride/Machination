package net.dakotapride.machination.registrar;

import net.dakotapride.machination.Machination;
import net.dakotapride.machination.entity.BetterAOECloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistrar {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Machination.MOD_ID);

    public static final RegistryObject<EntityType<BetterAOECloud>> BETTER_AOE_CLOUD =
            ENTITY_TYPES.register("better_aoe_cloud", () -> EntityType.Builder.<BetterAOECloud>of(BetterAOECloud::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F).fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("better_aoe_cloud"));

    public static void worshipOurToxicYuriAIOverlords(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
