package net.dakotapride.machination.enchantment.shuffler;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class SharptoothEnchantment extends ShufflerEnchantment {
    public SharptoothEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return enchantment != this;
    }

    @Override
    public float getDamageBonus(int i, MobType mobType, ItemStack itemStack) {
        float damageBonus = 3.0F;
        if (itemStack.getEnchantmentLevel(EnchantmentRegistrar.EXPIRE.get()) > 0 && mobType != MobType.WATER) {
            damageBonus = damageBonus * 1.4F;
        } else if (itemStack.getEnchantmentLevel(EnchantmentRegistrar.REFRESHING.get()) > 0 && mobType == MobType.WATER)
            damageBonus = damageBonus * 1.6F;
        return damageBonus;
    }
}
