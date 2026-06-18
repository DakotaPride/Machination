package net.dakotapride.machination.registrar;

import net.dakotapride.machination.Machination;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabsRegistrar {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Machination.MOD_ID);

    public static RegistryObject<CreativeModeTab> MACHINATION_TAB = CREATIVE_MODE_TABS.register("machination_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> ItemsRegistrar.COBALT_INGOT.get().getDefaultInstance())
                    .title(Component.translatable("itemGroup.machination.tab"))
                    .displayItems((params, output) -> {
                        output.accept(ItemsRegistrar.RAW_COBALT.get());
                        output.accept(ItemsRegistrar.COBALT_INGOT.get());
                        output.accept(ItemsRegistrar.COBALT_NUGGET.get());
                        output.accept(ItemsRegistrar.COBALT_LEGGINGS.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL_ENDER_DRAGON.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL_WITHER.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL_WARDEN.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL_ELDER_GUARDIAN.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL_PIGLIN_BRUTE.get());
                        output.accept(ItemsRegistrar.COBALT_PHIAL_GUARDIAN.get());
                        output.accept(BlocksRegistrar.COBALT_ORE.get());
                        output.accept(BlocksRegistrar.DEEPSLATE_COBALT_ORE.get());
                        output.accept(BlocksRegistrar.NETHERRACK_COBALT_ORE.get());
                        output.accept(BlocksRegistrar.COBALT_BLOCK.get());
                        output.accept(ItemsRegistrar.SOUL_BOTTLE.get());
                        output.accept(ItemsRegistrar.WITHERING_BASH.get());
                        output.accept(ItemsRegistrar.FLASK_OF_DESOLATIC_BURST.get());
                        output.accept(ItemsRegistrar.SHADOWKIN_GROWTH.get());
                        output.accept(ItemsRegistrar.ENFORCEMENT_UPGRADE.get());
                        output.accept(ItemsRegistrar.ENFORCED_CHESTPLATE.get());
                        output.accept(ItemsRegistrar.PRISMATIC_SHUFFLER.get());
                    }).build());

    public static void worshipOurToxicYuriAIOverlords(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
