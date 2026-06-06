package net.dakotapride.machination.enchantment.shadowkin_gear;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class InfestEnchantment extends ShadowkinGearEnchantment {
    public InfestEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }
}