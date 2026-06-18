package net.dakotapride.machination.enchantment.enforced_gear;

import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public class BruteEnchantment extends EnforcedGearEnchantment {
    public BruteEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        if (enchantment == Enchantments.ALL_DAMAGE_PROTECTION
                || enchantment == Enchantments.FIRE_PROTECTION
                || enchantment == Enchantments.PROJECTILE_PROTECTION
                || enchantment == Enchantments.BLAST_PROTECTION)
            return false;
        return super.checkCompatibility(enchantment) || MachinationUtils.standardArmourEnchantments.contains(enchantment);
    }

    @Override
    public int getDamageProtection(int amount, DamageSource source) {
        return amount * 4;
    }
}
