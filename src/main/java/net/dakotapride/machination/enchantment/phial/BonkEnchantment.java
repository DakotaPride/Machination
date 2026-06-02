package net.dakotapride.machination.enchantment.phial;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class BonkEnchantment extends PhialEnchantment {
    public BonkEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != EnchantmentRegistrar.SIPHON.get();
    }
}