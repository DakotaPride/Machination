package net.dakotapride.machination.registrar;

import net.dakotapride.machination.Machination;
import net.dakotapride.machination.enchantment.enforced_gear.BruteEnchantment;
import net.dakotapride.machination.enchantment.enforced_gear.BulwartEnchantment;
import net.dakotapride.machination.enchantment.enforced_gear.GildedSkinEnchantment;
import net.dakotapride.machination.enchantment.flask.ArsonistEnchantment;
import net.dakotapride.machination.enchantment.flask.EnlargeEnchantment;
import net.dakotapride.machination.enchantment.flask.FinalStandEnchantment;
import net.dakotapride.machination.enchantment.flask.HazardousEnchantment;
import net.dakotapride.machination.enchantment.phial.BonkEnchantment;
import net.dakotapride.machination.enchantment.phial.PuppyEnchantment;
import net.dakotapride.machination.enchantment.phial.SiphonEnchantment;
import net.dakotapride.machination.enchantment.shadowkin_gear.CatatonicEnchantment;
import net.dakotapride.machination.enchantment.shadowkin_gear.HivemindEnchantment;
import net.dakotapride.machination.enchantment.shadowkin_gear.InfestEnchantment;
import net.dakotapride.machination.enchantment.shuffler.ExpireEnchantment;
import net.dakotapride.machination.enchantment.shuffler.RefreshingEnchantment;
import net.dakotapride.machination.enchantment.shuffler.SharptoothEnchantment;
import net.dakotapride.machination.enchantment.withering_bash.MomentumEnchantment;
import net.dakotapride.machination.enchantment.withering_bash.SuitEnchantment;
import net.dakotapride.machination.enchantment.withering_bash.ViralEnchantment;
import net.dakotapride.machination.item.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistrar {
    public static final EnchantmentCategory PHIAL = EnchantmentCategory.create("phial", item -> item instanceof CobaltPhialItem);
    public static final EnchantmentCategory EXPLOSIVE_PROJECTILE = EnchantmentCategory.create("explosive_projectile", item -> item instanceof WitheringBashItem);
    public static final EnchantmentCategory FLASK = EnchantmentCategory.create("flask", item -> item instanceof FlaskOfDesolaticBurstItem);
    public static final EnchantmentCategory ENFORCED_GEAR = EnchantmentCategory.create("enforced_gear", item -> item instanceof EnforcedGearItem);
    public static final EnchantmentCategory SHADOWKIN_GEAR = EnchantmentCategory.create("shadowkin_gear", item -> item instanceof ShadowkinGearItem);
    public static final EnchantmentCategory SHUFFLER = EnchantmentCategory.create("shuffler", item -> item instanceof PrismaticShufflerItem);

    static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Machination.MOD_ID);

    // Cobalt Phial
    public static final RegistryObject<Enchantment> SIPHON = ENCHANTMENTS.register("siphon",
            () -> new SiphonEnchantment(PHIAL, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> BONK = ENCHANTMENTS.register("bonk",
            () -> new BonkEnchantment(PHIAL, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> PUPPY = ENCHANTMENTS.register("puppy",
            () -> new PuppyEnchantment(PHIAL, EquipmentSlot.MAINHAND));
    // Withering Bash
    public static final RegistryObject<Enchantment> VIRAL = ENCHANTMENTS.register("viral",
            () -> new ViralEnchantment(EXPLOSIVE_PROJECTILE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> MOMENTUM = ENCHANTMENTS.register("momentum",
            () -> new MomentumEnchantment(EXPLOSIVE_PROJECTILE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> SUIT = ENCHANTMENTS.register("suit",
            () -> new SuitEnchantment(EXPLOSIVE_PROJECTILE, EquipmentSlot.MAINHAND));
    // Flask of Desolatic Burst
    public static final RegistryObject<Enchantment> ENLARGE = ENCHANTMENTS.register("enlarge",
            () -> new EnlargeEnchantment(FLASK, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> FINAL_STAND = ENCHANTMENTS.register("final_stand",
            () -> new FinalStandEnchantment(FLASK, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> HAZARDOUS = ENCHANTMENTS.register("hazardous",
            () -> new HazardousEnchantment(FLASK, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ARSONIST = ENCHANTMENTS.register("arsonist",
            () -> new ArsonistEnchantment(FLASK, EquipmentSlot.MAINHAND));
    // Enforced Gear
    public static final RegistryObject<Enchantment> GILDED_SKIN = ENCHANTMENTS.register("gilded_skin",
            () -> new GildedSkinEnchantment(ENFORCED_GEAR, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> BRUTE = ENCHANTMENTS.register("brute",
            () -> new BruteEnchantment(ENFORCED_GEAR, EquipmentSlot.CHEST));
    public static final RegistryObject<Enchantment> BULWART = ENCHANTMENTS.register("bulwart",
            () -> new BulwartEnchantment(ENFORCED_GEAR, EquipmentSlot.CHEST));
    // Shadowkin Gear
    public static final RegistryObject<Enchantment> HIVEMIND = ENCHANTMENTS.register("hivemind", // Planned to be moved to just head if a headpiece is added
            () -> new HivemindEnchantment(SHADOWKIN_GEAR, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> INFEST = ENCHANTMENTS.register("infest",
            () -> new InfestEnchantment(SHADOWKIN_GEAR, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> CATATONIC = ENCHANTMENTS.register("catatonic",
            () -> new CatatonicEnchantment(SHADOWKIN_GEAR, EquipmentSlot.FEET));
    // Prismatic Shuffler
    public static final RegistryObject<Enchantment> SHARPTOOTH = ENCHANTMENTS.register("sharptooth",
            () -> new SharptoothEnchantment(SHUFFLER, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> EXPIRE = ENCHANTMENTS.register("expire",
            () -> new ExpireEnchantment(SHUFFLER, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REFRESHING = ENCHANTMENTS.register("refreshing",
            () -> new RefreshingEnchantment(SHUFFLER, EquipmentSlot.MAINHAND));

    public static void worshipOurToxicYuriAIOverlords(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }
}
