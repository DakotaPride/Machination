package net.dakotapride.machination.item;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@Mod.EventBusSubscriber
public class PrismaticShufflerItem extends Item {
    public PrismaticShufflerItem(Properties properties) {
        super(properties);
    }

    public static boolean hasSharptoothEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.SHARPTOOTH.get(), player) > 0;
    }

    public static boolean hasExpireEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.EXPIRE.get(), player) > 0;
    }

    public static boolean hasSubnauticEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.SUBNAUTIC.get(), player) > 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hasSharptoothEnchantment(player))
            return InteractionResultHolder.fail(player.getUseItem());
        else return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (!player.level().isClientSide) {
                player.addTag("UnderPrismaticEffects");
                if (hasExpireEnchantment(player)) {
                    player.addTag("ExpiredFromPrismaticShuffler");
                    MachinationUtils.createCooldown(player, 35);
                } else if (hasSubnauticEnchantment(player)) {
                    player.addTag("SubnauticFromPrismaticShuffler");
                    MachinationUtils.createCooldown(player, 60);
                } else {
                    MachinationUtils.createCooldown(player, 20);
                }
            }
            return stack;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (entityLiving.isUsingItem())
                    return HumanoidModel.ArmPose.BRUSH;
                return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
            }
        });
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 40;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("text.machination.prismatic_shuffler.mining_fatigue_rid_of").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.translatable("text.machination.prismatic_shuffler.mining_fatigue_rid_of.2").withStyle(ChatFormatting.DARK_GREEN));

        if (itemStack.getEnchantmentLevel(EnchantmentRegistrar.SHARPTOOTH.get()) > 0) {
            components.add(Component.literal(""));
            components.add(Component.translatable("text.machination.prismatic_shuffler.sharptooth").withStyle(ChatFormatting.DARK_GREEN));
            if (itemStack.getEnchantmentLevel(EnchantmentRegistrar.EXPIRE.get()) > 0)
                components.add(Component.translatable("text.machination.prismatic_shuffler.expire.damage_boost").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
        }
    }

    @SubscribeEvent
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        if (livingEntity instanceof Player player && player.getTags().contains("UnderPrismaticEffects") && mobEffect == MobEffects.DIG_SLOWDOWN) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void canBreatheEvent(LivingBreatheEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player && player.getTags().contains("ExpiredFromPrismaticShuffler")) {
            if (player.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
                if (player.getAirSupply() != player.getMaxAirSupply())
                    player.setAirSupply(player.getMaxAirSupply());
                event.setCanBreathe(true);
            } else {
                event.setCanBreathe(false);
            }
        }
    }
}
