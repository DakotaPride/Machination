package net.dakotapride.machination.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;

public class MachinationUtils {
    // Exclude mending and unbreaking as instances of this do not utilise durability
    public static final List<Enchantment> standardArmourEnchantments = List.of(Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.BLAST_PROTECTION, Enchantments.FIRE_PROTECTION);
    public static final List<Enchantment> standardBootEnchantments = List.of(Enchantments.FALL_PROTECTION, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER, Enchantments.SOUL_SPEED);

    public static void createCooldown(Player player, int cooldown) {
        if (!player.getAbilities().instabuild)
            player.getCooldowns().addCooldown(player.getItemInHand(player.getUsedItemHand()).getItem(), 20*cooldown);
    }

    public static void createCooldownAndDecrement(Player player, int cooldown, int decrement) {
        if (!player.getAbilities().instabuild) {
            player.getCooldowns().addCooldown(player.getItemInHand(player.getUsedItemHand()).getItem(), 20 * cooldown);
            player.getItemInHand(player.getUsedItemHand()).shrink(decrement);
        }
    }

    public static void createCooldownAndDecrement(Player player, ItemStack stack, int cooldown, int decrement) {
        if (!player.getAbilities().instabuild) {
            player.getCooldowns().addCooldown(stack.getItem(), 20 * cooldown);
            stack.shrink(decrement);
        }
    }
}
