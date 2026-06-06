package net.dakotapride.machination.enchantment.shuffler;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ShufflerEnchantment extends Enchantment {
    public ShufflerEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(rarity, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return enchantment instanceof SharptoothEnchantment;
    }
}
