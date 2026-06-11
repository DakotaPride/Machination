package net.dakotapride.machination.item;

import net.dakotapride.machination.entity.BetterAOECloud;
import net.dakotapride.machination.registrar.AdvancementRegistrar;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnforcedGearItem extends ArmorItem {
    public EnforcedGearItem(Type type, Properties properties) {
        super(MachinationArmourMaterials.ENFORCED, type, properties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public static boolean hasGildedSkinEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.GILDED_SKIN.get(), player) > 0;
    }

    public static boolean hasBruteEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.BRUTE.get(), player) > 0;
    }

    public static boolean hasBulwartEnchantment(Player player) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.BULWART.get(), player) > 0;
    }

    int requiredBulwartCrouchingTicks = (20*10)*2;

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean b) {
        if (entity instanceof Player player) {
            for (ItemStack armorStack : player.getArmorSlots()) {
                if (armorStack.getItem() instanceof ArmorItem) {
                    if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem headItem
                            && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem chestItem
                            && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem legsItem
                            && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem feetItem) {
                        boolean isHeadGold = headItem.getMaterial() == ArmorMaterials.GOLD;
                        boolean isChestEnforced = chestItem.getMaterial() == MachinationArmourMaterials.ENFORCED;
                        boolean isLegsGold = legsItem.getMaterial() == ArmorMaterials.GOLD;
                        boolean isFeetGold = feetItem.getMaterial() == ArmorMaterials.GOLD;

                        if (isChestEnforced && hasGildedSkinEnchantment(player))
                            if (isHeadGold && isLegsGold && isFeetGold) {
                                MobEffectInstance effectInstance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20*60, 2);
                                if (effectInstance.endsWithin(20*3) || !player.hasEffect(MobEffects.DAMAGE_RESISTANCE))
                                    player.addEffect(effectInstance);
                            }
                    }
                }
            }
        }

        if (entity instanceof Player player && this.getEquipmentSlot() == EquipmentSlot.CHEST) {
            if (hasBulwartEnchantment(player) && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                if (player.isCrouching()) {
                    if (requiredBulwartCrouchingTicks > 0) {
                        --requiredBulwartCrouchingTicks;
                    }
                    if (requiredBulwartCrouchingTicks == 0) {
                        requiredBulwartCrouchingTicks = (20*10)*2;
                        BetterAOECloud cloud = new BetterAOECloud(level, player.getX(), player.getY(), player.getZ());
                        createAreaEffectCloud(cloud, player);
                        player.getCooldowns().addCooldown(stack.getItem(), 20*60);
                        if (player instanceof ServerPlayer serverPlayer)
                            AdvancementRegistrar.BULWART.trigger(serverPlayer);
                    }
                } else if (requiredBulwartCrouchingTicks < (20*10)*2) {
                    requiredBulwartCrouchingTicks = (20*10)*2;
                }
            }
        }
    }

    private static BetterAOECloud createAreaEffectCloud(BetterAOECloud cloud, Player player) {
        Level level = player.level();
        cloud.setOwner(player);
        cloud.getTags().add("FromBulwartInstance");
        cloud.addEffect(new MobEffectInstance(MobEffects.HARM));
        cloud.setParticle(ParticleTypes.MYCELIUM);
        cloud.setRadius(10.0F);
        cloud.setDuration(100);
        level.addFreshEntity(cloud);
        return cloud;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("text.machination.enforced_chestplate.fire_resistance").withStyle(ChatFormatting.DARK_GREEN));
    }
}
