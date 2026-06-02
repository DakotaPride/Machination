package net.dakotapride.machination.enchantment.withering_bash;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class SuitEnchantment extends WitheringBashEnchantment {
    public SuitEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.UNCOMMON, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return enchantment != this;
    }
}
