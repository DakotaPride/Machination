package net.dakotapride.machination.enchantment.shuffler;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SubnauticEnchantment extends ShufflerEnchantment {
    public SubnauticEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }
}
