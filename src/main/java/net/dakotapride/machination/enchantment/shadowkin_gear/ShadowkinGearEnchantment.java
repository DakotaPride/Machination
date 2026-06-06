package net.dakotapride.machination.enchantment.shadowkin_gear;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ShadowkinGearEnchantment extends Enchantment {
    public ShadowkinGearEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(rarity, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return enchantment instanceof HivemindEnchantment;
    }
}
