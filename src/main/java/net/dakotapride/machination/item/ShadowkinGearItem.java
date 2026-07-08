package net.dakotapride.machination.item;

import net.dakotapride.machination.registrar.AdvancementRegistrar;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.BlockTags;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class ShadowkinGearItem extends ArmorItem {
    public ShadowkinGearItem(Type type, Properties properties) {
        super(MachinationArmourMaterials.SHADOWKIN, type, properties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public static boolean hasHivemindEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.HIVEMIND.get(), player) > 0;
    }

    public static boolean hasInfestEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.INFEST.get(), player) > 0;
    }

    public static boolean hasCatatonicEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.CATATONIC.get(), player) > 0;
    }

    public static boolean ableToStopVibrationDetection(Player player) {
        return !hasCatatonicEnchantment(player) || player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ShadowkinGearItem;
    }

    public static void createCatatonicEffects(Player player) {
        MobEffectInstance slownessInstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20*15, 1);
        MobEffectInstance strengthInstance = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20*15, 4);
        if (hasCatatonicEnchantment(player) && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ShadowkinGearItem) {
            if (slownessInstance.endsWithin(20*5) || !player.hasEffect(slownessInstance.getEffect()))
                player.addEffect(slownessInstance);
            if (strengthInstance.endsWithin(20*5) || !player.hasEffect(strengthInstance.getEffect()))
                player.addEffect(strengthInstance);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean b) {
        if (entity instanceof Player player) {
            if (this.getEquipmentSlot() == EquipmentSlot.FEET) {
                if (!ableToStopVibrationDetection(player) && player.getTags().contains("HasActivatedVibrationRecently")) {
                    createCatatonicEffects(player);
                }
                if (hasInfestEnchantment(player)) {
                    final List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class,
                            player.getBoundingBox().inflate(8F), Objects::nonNull);
                    list.forEach(livingEntity -> {
                        BlockPos supposedPos = player.blockPosition().below();
                        BlockPos supposedPos2 = livingEntity.blockPosition().below();
                        if (livingEntity != player && level.getBlockState(supposedPos).is(BlockTags.SCULK_BLOCKS)
                                && level.getBlockState(supposedPos2).is(BlockTags.SCULK_BLOCKS)) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 4));
                            if (player instanceof ServerPlayer serverPlayer && list.size() >= 11) // 10 inflicted + 1 wearer
                                AdvancementRegistrar.INFECT.trigger(serverPlayer);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("text.machination.shadowkin_growth.vibration_detection").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.translatable("text.machination.shadowkin_growth.vibration_detection.2").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.translatable("text.machination.shadowkin_growth.vibration_detection.3").withStyle(ChatFormatting.DARK_GREEN));
    }

    /*CREDIT FOR EVENT (MODIFIED) CODE GOES TO ESOTERICA, DEVELOPED BY STICKIA | https://codeberg.org/Stickia/Esoterica */
    @SubscribeEvent
    public static void stealthWithShadowkinBoots(VanillaGameEvent event) {
        if (!event.isCanceled() && event.getCause() instanceof ServerPlayer player) {
            int frequency = VibrationSystem.getGameEventFrequency(event.getVanillaEvent());
            boolean correctFrequencies = frequency == 0 || frequency == 1 || frequency == 2 || frequency == 9 || frequency == 10 || frequency == 12 || frequency == 13;
            if (event.isCancelable() && correctFrequencies && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ShadowkinGearItem) {
                if (ShadowkinGearItem.ableToStopVibrationDetection(player))
                    event.setCanceled(ShadowkinGearItem.ableToStopVibrationDetection(player));
                else {
                    if (hasCatatonicEnchantment(player)) {
                        player.addTag("HasActivatedVibrationRecently");
                    }
                }
            }
        }
    }
}
