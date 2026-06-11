package net.dakotapride.machination.enchantment.shadowkin_gear;

import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ShadowkinGearEnchantment extends Enchantment {
    EquipmentSlot[] equipmentSlots;
    public ShadowkinGearEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(rarity, category, equipmentSlots);
        this.equipmentSlots = equipmentSlots;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        boolean slotEnchantmentCheck = false;
        if (Arrays.stream(equipmentSlots).anyMatch(equipmentSlot -> equipmentSlot == EquipmentSlot.FEET))
            slotEnchantmentCheck = MachinationUtils.standardBootEnchantments.contains(enchantment);
        return enchantment instanceof HivemindEnchantment || slotEnchantmentCheck;
    }
}
