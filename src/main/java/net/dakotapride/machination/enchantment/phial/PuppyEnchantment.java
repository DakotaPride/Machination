package net.dakotapride.machination.enchantment.phial;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class PuppyEnchantment extends PhialEnchantment {
    public PuppyEnchantment(EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(Rarity.RARE, category, equipmentSlots);
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return enchantment != this;
    }

    //    @SubscribeEvent
//    public static void onServerChat(ServerChatEvent event) {
//        ServerPlayer player = event.getPlayer();
//        String message = event.getMessage().getString();
//
//        if (player.getTags().contains("StunlockedFromCuteness")) {
//            if (message.contains("woof :3"))
//                player.removeTag("StunlockedFromCuteness");
//            else event.setMessage(Component.translatable("text.machination.woof_warning"));
//        }
//    }
//
//    @Mod.EventBusSubscriber(value = Dist.CLIENT)
//    static class ClientEvent {
//        @SubscribeEvent
//        public static void onClientChat(ClientChatEvent event) {
//            Player player = Minecraft.getInstance().player;
//            String message = event.getOriginalMessage();
//
//            if (player != null && player.getTags().contains("StunlockedFromCuteness")) {
//                if (message.contains("woof :3"))
//                    player.removeTag("StunlockedFromCuteness");
//                else event.setMessage(String.valueOf(Component.translatable("text.machination.woof_warning")));
//            }
//        }
//    }
}