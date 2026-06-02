package net.dakotapride.machination.util;

import net.minecraft.world.entity.player.Player;

public class MachinationUtils {
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
}
