package net.dakotapride.machination.registrar;

import net.dakotapride.machination.Machination;
import net.dakotapride.machination.item.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ItemsRegistrar {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Machination.MOD_ID);

    public static RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget", () -> new Item(new Item.Properties()));
    public static RegistryObject<CobaltPhialItem> COBALT_PHIAL = ITEMS.register("cobalt_phial",
            () -> new CobaltPhialItem(true, new Item.Properties().stacksTo(1)));
    public static RegistryObject<FilledCobaltPhialItem> COBALT_PHIAL_ENDER_DRAGON = registerFilledCobaltPhialItem(DivineBeings.ENDER_DRAGON);
    public static RegistryObject<FilledCobaltPhialItem> COBALT_PHIAL_WITHER = registerFilledCobaltPhialItem(DivineBeings.WITHER);
    public static RegistryObject<FilledCobaltPhialItem> COBALT_PHIAL_WARDEN = registerFilledCobaltPhialItem(DivineBeings.WARDEN);
    public static RegistryObject<FilledCobaltPhialItem> COBALT_PHIAL_ELDER_GUARDIAN = registerFilledCobaltPhialItem(DivineBeings.ELDER_GUARDIAN);
    public static RegistryObject<FilledCobaltPhialItem> COBALT_PHIAL_PIGLIN_BRUTE = registerFilledCobaltPhialItem(DivineBeings.PIGLIN_BRUTE);
    public static RegistryObject<FilledCobaltPhialItem> COBALT_PHIAL_GUARDIAN = registerFilledCobaltPhialItem(DivineBeings.GUARDIAN);
    public static RegistryObject<Item> SOUL_BOTTLE = ITEMS.register("soul_bottle", () -> new Item(new Item.Properties()));

    // Ender Dragon
    public static RegistryObject<FlaskOfDesolaticBurstItem> FLASK_OF_DESOLATIC_BURST = ITEMS.register("flask_of_desolatic_burst", () -> new FlaskOfDesolaticBurstItem(new Item.Properties().stacksTo(16)));

    // Wither
    public static RegistryObject<WitheringBashItem> WITHERING_BASH = ITEMS.register("withering_bash", () -> new WitheringBashItem(new Item.Properties().stacksTo(1)));

    // Warden
    public static RegistryObject<ShadowkinGearItem> SHADOWKIN_GROWTH = ITEMS.register("shadowkin_growth", () -> new ShadowkinGearItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    // Elder Guardian

    // Piglin Brute
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component NETHERITE_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade",
            ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "enforcement_upgrade"))).withStyle(TITLE_FORMAT);
    private static final Component ENFORCEMENT_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "smithing_template.enforcement_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component ENFORCEMENT_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "smithing_template.enforcement_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component ENFORCEMENT_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "smithing_template.enforcement_upgrade.base_slot_description")));
    private static final Component ENFORCEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(Machination.MOD_ID, "smithing_template.enforcement_upgrade.additions_slot_description")));
    private static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_pickaxe");
    private static final ResourceLocation EMPTY_SLOT_INGOT = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_ingot");

    private static List<ResourceLocation> createEnforcementUpgradeIconList() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL);
    }

    private static List<ResourceLocation> createEnforcementUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_INGOT);
    }
    public static SmithingTemplateItem createEnforcementUpgradeTemplate() {
        return new SmithingTemplateItem(
                ENFORCEMENT_UPGRADE_APPLIES_TO,
                ENFORCEMENT_UPGRADE_INGREDIENTS,
                NETHERITE_UPGRADE,
                ENFORCEMENT_UPGRADE_BASE_SLOT_DESCRIPTION,
                ENFORCEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                createEnforcementUpgradeIconList(),
                createEnforcementUpgradeMaterialList());
    }

    public static RegistryObject<SmithingTemplateItem> ENFORCEMENT_UPGRADE = ITEMS.register("enforcement_upgrade",
            ItemsRegistrar::createEnforcementUpgradeTemplate);
    public static RegistryObject<EnforcedGearItem> ENFORCED_CHESTPLATE = ITEMS.register("enforced_chestplate",
            () -> new EnforcedGearItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).fireResistant()));

    // Guardian
    public static RegistryObject<Item> PRISMATIC_SHUFFLER = ITEMS.register("prismatic_shuffler", () -> new PrismaticShufflerItem(new Item.Properties().stacksTo(1)));

    private static RegistryObject<FilledCobaltPhialItem> registerFilledCobaltPhialItem(DivineBeings divineBeing) {
        return ITEMS.register("cobalt_phial_" + divineBeing.getId(), () -> new FilledCobaltPhialItem(divineBeing, new Item.Properties().stacksTo(1).craftRemainder(ItemsRegistrar.COBALT_PHIAL.get())));
    }

    public static void worshipOurToxicYuriAIOverlords(IEventBus bus) {
        ITEMS.register(bus);
    }
}
