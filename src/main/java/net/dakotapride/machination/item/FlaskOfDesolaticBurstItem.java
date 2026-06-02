package net.dakotapride.machination.item;

import net.dakotapride.machination.entity.BetterAOECloud;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlaskOfDesolaticBurstItem extends Item {
    public FlaskOfDesolaticBurstItem(Properties properties) {
        super(properties);
    }

    public static boolean hasEnlargeEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.ENLARGE.get(), player) > 0;
    }

    public static boolean hasFinalStandEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.FINAL_STAND.get(), player) > 0;
    }

    public static boolean hasHazardousEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.HAZARDOUS.get(), player) > 0;
    }

    public static boolean hasArsonistEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.ARSONIST.get(), player) > 0;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return stack.isEnchanted() ? 8 : 16;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (stack.getEnchantmentLevel(EnchantmentRegistrar.FINAL_STAND.get()) > 0)
            components.add(Component.translatable("text.machination.flask_of_desolatic_burst.final_stand").withStyle(ChatFormatting.DARK_GREEN));
        else components.add(Component.translatable("text.machination.flask_of_desolatic_burst").withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 10;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return hasFinalStandEnchantment(player) ? super.use(level, player, hand) : ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof Player player && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            BetterAOECloud cloud = new BetterAOECloud(level, player.getX(), player.getY(), player.getZ());
            createAreaEffectCloud(cloud, player);
            MachinationUtils.createCooldownAndDecrement(player, 60, 1);
        }
        return super.finishUsingItem(stack, level, user);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean b) {
        if (entity instanceof Player player && hasFinalStandEnchantment(player) && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            if (player.getHealth() == 1 && player.getOffhandItem() == stack) {
                BetterAOECloud cloud = new BetterAOECloud(level, player.getX(), player.getY(), player.getZ());
                createAreaEffectCloud(cloud, player);
                MachinationUtils.createCooldownAndDecrement(player, 60, 1);
            }
        }
    }

    private static BetterAOECloud createAreaEffectCloud(BetterAOECloud cloud, Player player) {
        Level level = player.level();
        cloud.setOwner(player);
        cloud.getTags().add("FromFlaskOfDesolaticBurst");
        cloud.addEffect(new MobEffectInstance(MobEffects.HARM));
        if (hasHazardousEnchantment(player)) {
            cloud.addEffect(new MobEffectInstance(MobEffects.POISON));
            cloud.setParticle(ParticleTypes.ENTITY_EFFECT);
        } else if (hasArsonistEnchantment(player)) {
            cloud.setCanSetFire(true);
            cloud.setFireSeconds(3);
            cloud.setParticle(ParticleTypes.FLAME);
        } else {
            cloud.setParticle(ParticleTypes.DRAGON_BREATH);
        }
        float radius = hasFinalStandEnchantment(player) ? (hasEnlargeEnchantment(player) ? 20.0F : 10.0F) : (hasEnlargeEnchantment(player) ? 15.0F : 10.0F);
        cloud.setRadius(radius);
        cloud.setDuration(100);
        level.addFreshEntity(cloud);
        return cloud;
    }
}
