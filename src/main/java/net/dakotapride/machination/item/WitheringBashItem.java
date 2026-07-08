package net.dakotapride.machination.item;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class WitheringBashItem extends ProjectileWeaponItem {
    public WitheringBashItem(Properties properties) {
        super(properties);
    }

    public static boolean hasViralEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.VIRAL.get(), player) > 0;
    }

    public static boolean hasMomentumEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.MOMENTUM.get(), player) > 0;
    }

    public static boolean hasSuitEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.SUIT.get(), player) > 0;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 17;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("text.machination.withering_bash.ammo").withStyle(ChatFormatting.DARK_GREEN));
        if (stack.getEnchantmentLevel(EnchantmentRegistrar.SUIT.get()) > 0)
            components.add(Component.translatable("text.machination.withering_bash.ammo_consumption.suit").withStyle(ChatFormatting.DARK_GREEN));
        else components.add(Component.translatable("text.machination.withering_bash.ammo_consumption").withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        ItemStack suitStack = new ItemStack(Items.WITHER_SKELETON_SKULL, 3);
        return stack -> stack.is(Items.WITHER_SKELETON_SKULL) || (stack.getEnchantmentLevel(EnchantmentRegistrar.SUIT.get()) > 0 && stack == suitStack);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 120;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (entityLiving.isUsingItem())
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        });
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 30;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user) {
        if (user instanceof Player player) {
            ItemStack projectile = player.getProjectile(stack);
            int cooldown = hasMomentumEnchantment(player) ? 20 : 40;
            if (!level.isClientSide) {
                MachinationUtils.createCooldown(player, cooldown);
                if (!player.getAbilities().instabuild) {
                    if (hasSuitEnchantment(player))
                        projectile.shrink(3);
                    else projectile.shrink(1);
                }
            }

            this.performRangedAttack(player);
        }
        return super.finishUsingItem(stack, level, user);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.getAbilities().instabuild && player.getProjectile(stack).isEmpty())
            return InteractionResultHolder.fail(stack);
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    private void performRangedAttack(Player player) {
        Level level = player.level();

        if (!level.isClientSide) {
            WitherSkull witherSkull = getWitherSkull(player, level);
            level.addFreshEntity(witherSkull);
            level.playSound(player, player.blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    private @NotNull WitherSkull getWitherSkull(Player player, Level level) {
        WitherSkull witherSkull = new WitherSkull(EntityType.WITHER_SKULL, level);
        this.shoot(witherSkull, player);
        return witherSkull;
    }

    public void shoot(WitherSkull witherSkull, Player player) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();

        witherSkull.setOwner(player);
        witherSkull.addTag("FromWitheringBashProjectile");
        if (hasSuitEnchantment(player))
            witherSkull.addTag("SuitAccessible");
        witherSkull.setDangerous(hasViralEnchantment(player));
        witherSkull.setPos(eyePos.x(), eyePos.y(), eyePos.z());
        witherSkull.setXRot(player.getXRot());
        witherSkull.setYRot(player.getYRot());

        double speed = witherSkull.isDangerous() ? (hasMomentumEnchantment(player) ? 8.4 : 2.8) : (hasMomentumEnchantment(player) ? 4.2 : 1.4);
        witherSkull.setDeltaMovement(
                lookVec.x * speed,
                lookVec.y * speed,
                lookVec.z * speed
        );

        player.level().addFreshEntity(witherSkull);
    }

    @SubscribeEvent
    public static void getSuitAccessibleDamageDealt(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source.getDirectEntity() instanceof WitherSkull witherSkull && witherSkull.getTags().contains("FromWitheringBashProjectile")) {
            float suitDamage = witherSkull.getTags().contains("SuitAccessible") ? event.getAmount() * 2 : event.getAmount();
            event.setAmount(suitDamage);
        }
    }
}
