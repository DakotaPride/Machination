package net.dakotapride.machination.enchantment.withering_bash;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ViralEnchantment extends WitheringBashEnchantment {
    public ViralEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.VERY_RARE, category, equipmentSlots);
    }
}
