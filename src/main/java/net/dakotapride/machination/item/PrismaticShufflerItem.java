package net.dakotapride.machination.item;

import net.dakotapride.machination.Machination;
import net.dakotapride.machination.registrar.AdvancementRegistrar;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

    public static boolean hasRefreshingEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.REFRESHING.get(), player) > 0;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean b) {
        if (entity instanceof Player player && hasRefreshingEnchantment(player))
            if (player.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
                MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION, 100, 1);
                if (!player.hasEffect(regeneration.getEffect()) || regeneration.endsWithin(3))
                    player.addEffect(regeneration);
            }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hasSharptoothEnchantment(player))
            return super.use(level, player, hand);
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
            if (itemStack.getEnchantmentLevel(EnchantmentRegistrar.REFRESHING.get()) > 0)
                components.add(Component.translatable("text.machination.prismatic_shuffler.subnautic.damage_boost").withStyle(ChatFormatting.DARK_GREEN));
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

    @SubscribeEvent
    public static void onKillElderGuardianEvent(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource source = event.getSource();

        if (source.getEntity() instanceof ServerPlayer player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag tag = stack.getTag();
            int elderGuardianDeaths = tag.getInt("ElderGuardianDeathsCount");
            if (livingEntity instanceof ElderGuardian) {
                if (elderGuardianDeaths == 0)
                    elderGuardianDeaths = 1;
                if (elderGuardianDeaths >= 3) {
                    tag.putInt("ElderGuardianDeathsCount", 0);
                    if (hasSharptoothEnchantment(player) && hasRefreshingEnchantment(player))
                        AdvancementRegistrar.SKEWER.trigger(player);
                } else {
                    elderGuardianDeaths++;
                    tag.putInt("ElderGuardianDeathsCount", elderGuardianDeaths);
                }
            }
        }
    }
}
