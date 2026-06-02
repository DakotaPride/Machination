package net.dakotapride.machination;

import com.mojang.logging.LogUtils;
import net.dakotapride.machination.registrar.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// Mod Ideas
// - Capture essences of divine beings (Ender Dragon, The Wither, The Warden, etc.)
// - Uhh, well... Plasmium phial or whatever the fuck you spell it - stab divine beings with a Cobalt Phial to extract essence from said being
//
// Ender Dragon
// - Fills the Cobalt Phial up with Desolatic Essence
// - Using Desolatic Essence among other ingredients within a crafting grid can create two features
//  - Flask of Desolatic Burst (Consume to send a shockwave of dragon's breath out from the player, damaging foes)
//  -

@Mod(Machination.MOD_ID)
public class Machination {
    public static final String MOD_ID = "machination";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Machination(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        ItemsRegistrar.worshipOurToxicYuriAIOverlords(bus);
        CreativeModeTabsRegistrar.worshipOurToxicYuriAIOverlords(bus);
        BlocksRegistrar.worshipOurToxicYuriAIOverlords(bus);
        EnchantmentRegistrar.worshipOurToxicYuriAIOverlords(bus);
        SoundsRegistrar.worshipOurToxicYuriAIOverlords(bus);
        EntityRegistrar.worshipOurToxicYuriAIOverlords(bus);

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemsRegistrar.COBALT_PHIAL);
            event.accept(ItemsRegistrar.COBALT_PHIAL_ENDER_DRAGON.get());
            event.accept(ItemsRegistrar.COBALT_PHIAL_WITHER.get());
            event.accept(ItemsRegistrar.COBALT_PHIAL_WARDEN.get());
            event.accept(ItemsRegistrar.COBALT_PHIAL_ELDER_GUARDIAN.get());
            event.accept(ItemsRegistrar.COBALT_PHIAL_PIGLIN_BRUTE.get());
            event.accept(ItemsRegistrar.COBALT_PHIAL_GUARDIAN.get());
            event.accept(ItemsRegistrar.WITHERING_BASH.get());
            event.accept(ItemsRegistrar.FLASK_OF_DESOLATIC_BURST.get());
            event.accept(ItemsRegistrar.ENFORCEMENT_UPGRADE);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemsRegistrar.RAW_COBALT);
            event.accept(ItemsRegistrar.COBALT_INGOT);
            event.accept(ItemsRegistrar.COBALT_NUGGET);
            event.accept(ItemsRegistrar.SOUL_BOTTLE);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BlocksRegistrar.COBALT_ORE);
            event.accept(BlocksRegistrar.DEEPSLATE_COBALT_ORE);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(BlocksRegistrar.COBALT_BLOCK);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ItemsRegistrar.ENFORCED_CHESTPLATE);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(EntityRegistrar.BETTER_AOE_CLOUD.get(), NoopRenderer::new);
        }
    }
}
