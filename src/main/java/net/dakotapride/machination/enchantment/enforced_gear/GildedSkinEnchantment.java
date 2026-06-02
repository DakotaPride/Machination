package net.dakotapride.machination.enchantment.enforced_gear;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GildedSkinEnchantment extends EnforcedGearEnchantment {
    public GildedSkinEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }
}