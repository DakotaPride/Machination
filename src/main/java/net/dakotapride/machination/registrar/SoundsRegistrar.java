package net.dakotapride.machination.registrar;

import net.dakotapride.machination.Machination;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundsRegistrar {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Machination.MOD_ID);

    public static final RegistryObject<SoundEvent> PHIAL_USE = registerSound("use_phial_on_living_creature");
    public static final RegistryObject<SoundEvent> PHIAL_USE_SIPHON = registerSound("use_phial_on_living_creature_siphon");
    public static final RegistryObject<SoundEvent> PHIAL_USE_DIVINE_BEING = registerSound("use_phial_on_divine_being");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, name)));
    }

    public static void worshipOurToxicYuriAIOverlords(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
