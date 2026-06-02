package net.dakotapride.machination.util;

import net.dakotapride.machination.Machination;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum MachinationArmourMaterials implements StringRepresentable, ArmorMaterial {
    ENFORCED("enforced", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), (typeProtection) -> {
        typeProtection.put(ArmorItem.Type.BOOTS, 3);
        typeProtection.put(ArmorItem.Type.LEGGINGS, 6);
        typeProtection.put(ArmorItem.Type.CHESTPLATE, 8);
        typeProtection.put(ArmorItem.Type.HELMET, 3);
    }), 10, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F, 0.0F, () -> Ingredient.of(Items.PRISMARINE_SHARD)),;

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (typeProtection) -> {
        typeProtection.put(ArmorItem.Type.BOOTS, 13);
        typeProtection.put(ArmorItem.Type.LEGGINGS, 15);
        typeProtection.put(ArmorItem.Type.CHESTPLATE, 16);
        typeProtection.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    MachinationArmourMaterials(String name, int durability, EnumMap<ArmorItem.Type, Integer> protectionForType, int enchantmentValue, SoundEvent sound, float toughness, float knockback, Supplier<Ingredient> repairIngredient) {
        this.name = Machination.MOD_ID + ":" + name;
        this.durabilityMultiplier = durability;
        this.protectionFunctionForType = protectionForType;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockback;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type tye) {
        return this.protectionFunctionForType.get(tye);
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public String getSerializedName() {
        return this.name;
    }
}
