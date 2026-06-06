package net.dakotapride.machination.enchantment.shuffler;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ExpireEnchantment extends ShufflerEnchantment {
    public ExpireEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }
}
