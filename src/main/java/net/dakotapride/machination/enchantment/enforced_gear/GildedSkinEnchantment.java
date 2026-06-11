package net.dakotapride.machination.enchantment.enforced_gear;

import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class GildedSkinEnchantment extends EnforcedGearEnchantment {
    public GildedSkinEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return super.checkCompatibility(enchantment) || MachinationUtils.standardArmourEnchantments.contains(enchantment);
    }
}