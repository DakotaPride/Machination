package net.dakotapride.machination.item;

import net.dakotapride.machination.registrar.AdvancementRegistrar;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.BlockTags;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ShadowkinGearItem extends ArmorItem {
    public ShadowkinGearItem(Type type, Properties properties) {
        super(MachinationArmourMaterials.SHADOWKIN, type, properties);
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

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean b) {
        if (entity instanceof Player player) {
            if (hasCatatonicEnchantment(player)) {
                BlockPos supposedPos = player.blockPosition().below();
                if (level.getBlockState(supposedPos).is(BlockTags.SCULK_BLOCKS)) {
                    player.addTag("HasSteppedOnSculkRecently");
                }

                if (player.getTags().contains("HasSteppedOnSculkRecently")) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 1));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2));
                }
            }
            if (this.getEquipmentSlot() == EquipmentSlot.FEET) {
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
}
