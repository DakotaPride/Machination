package net.dakotapride.machination.enchantment.enforced_gear;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BruteEnchantment extends EnforcedGearEnchantment {
    public BruteEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }
    
    @Override
    public int getDamageProtection(int amount, DamageSource source) {
        return amount * 4;
    }
}
