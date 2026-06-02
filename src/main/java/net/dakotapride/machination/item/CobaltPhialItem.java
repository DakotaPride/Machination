package net.dakotapride.machination.item;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.registrar.ItemsRegistrar;
import net.dakotapride.machination.registrar.SoundsRegistrar;
import net.dakotapride.machination.util.EntityTypeTags;
import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CobaltPhialItem extends Item {
    boolean isEmptyPhial;
    DivineBeings divineBeing;
    public CobaltPhialItem(boolean isEmptyPhial, Properties properties) {
        super(properties);
        this.isEmptyPhial = isEmptyPhial;
        this.divineBeing = DivineBeings.NONE;
    }

    public boolean isEmptyPhial() {
        return isEmptyPhial;
    }

    public DivineBeings getDivineBeing() {
        return divineBeing;
    }

    public static boolean hasSiphonEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.SIPHON.get(), player) > 0;
    }

    public static boolean hasBonkEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.BONK.get(), player) > 0;
    }

    @Override
    public @NotNull String getDescriptionId(@NotNull ItemStack stack) {
        return ItemsRegistrar.COBALT_PHIAL.get().getDescriptionId();
    }

    public static void applyInjectionEffects(Player player, LivingEntity livingEntity, float damage) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 100));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 100));

        if (hasSiphonEnchantment(player))
            player.heal(damage);
        else livingEntity.hurt(livingEntity.damageSources().generic(), damage);

        if (hasBonkEnchantment(player)) {
            livingEntity.knockback(2.5D, Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F)), (-Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F))));
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
        } else livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100));
    }

    public static void applyInjectionEffects(Player player, EnderDragonPart part, DamageSource source, float damage) {
        EnderDragon enderDragon = part.getParent();
        enderDragon.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100));
        enderDragon.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 100));
        enderDragon.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 100));
        if (hasSiphonEnchantment(player))
            player.heal(damage);
        enderDragon.hurt(part, source, damage);
    }

    public static void applyInjectionEffects(Player player, LivingEntity livingEntity) {
        float percentHealth = livingEntity.getMaxHealth() / 10;
        float actualDamage = livingEntity.getHealth() <= percentHealth ? livingEntity.getHealth() / 10 : percentHealth;
        applyInjectionEffects(player, livingEntity, actualDamage);
    }

    public InteractionResult createNewPhialInteraction(Player player, ItemStack stack, LivingEntity target, InteractionHand hand, Level level, FilledCobaltPhialItem phialItem, EntityType<?> entityType) {
        if (!player.getCooldowns().isOnCooldown(stack.getItem()) && this.isEmptyPhial()) {
            CobaltPhialItem.applyInjectionEffects(player, target);
            if (!level.isClientSide) {
                if (!player.getAbilities().instabuild)
                    MachinationUtils.createCooldown(player, 300);

                if (target.getType() == entityType) {
                    player.setItemInHand(hand, new ItemStack(phialItem));
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        Level level = player.level();

        createNewPhialInteraction(player, stack, target, hand, level,
                ItemsRegistrar.COBALT_PHIAL_ENDER_DRAGON.get(), DivineBeings.ENDER_DRAGON.getEntityType());
        createNewPhialInteraction(player, stack, target, hand, level,
                ItemsRegistrar.COBALT_PHIAL_WITHER.get(), DivineBeings.WITHER.getEntityType());
        createNewPhialInteraction(player, stack, target, hand, level,
                ItemsRegistrar.COBALT_PHIAL_WARDEN.get(), DivineBeings.WARDEN.getEntityType());
        createNewPhialInteraction(player, stack, target, hand, level,
                ItemsRegistrar.COBALT_PHIAL_ELDER_GUARDIAN.get(), DivineBeings.ELDER_GUARDIAN.getEntityType());
        createNewPhialInteraction(player, stack, target, hand, level,
                ItemsRegistrar.COBALT_PHIAL_PIGLIN_BRUTE.get(), DivineBeings.PIGLIN_BRUTE.getEntityType());
        createNewPhialInteraction(player, stack, target, hand, level,
                ItemsRegistrar.COBALT_PHIAL_GUARDIAN.get(), DivineBeings.GUARDIAN.getEntityType());

        boolean flag = target.getType().is(EntityTypeTags.BLACKLISTED_PHIAL_ENTITIES);
        boolean flag2 = target.getType().is(EntityTypeTags.DIVINE_BEINGS);
        if (!flag && this.isEmptyPhial()) {
            level.playSound(player, player.blockPosition(), SoundsRegistrar.PHIAL_USE.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            if (flag2) {
                level.playSound(player, player.blockPosition(), SoundsRegistrar.PHIAL_USE_DIVINE_BEING.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            }
        }

        return this.isEmptyPhial() ? (flag ? InteractionResult.FAIL : InteractionResult.SUCCESS) : super.interactLivingEntity(stack, player, target, hand);
    }
}
