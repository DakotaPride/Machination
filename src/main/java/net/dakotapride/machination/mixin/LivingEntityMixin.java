package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.CobaltPhialItem;
import net.dakotapride.machination.item.DivineBeings;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.registrar.ItemsRegistrar;
import net.dakotapride.machination.registrar.SoundsRegistrar;
import net.dakotapride.machination.util.EntityTypeTags;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    LivingEntity livingEntity = (LivingEntity) (Object) this;
    @Unique
    UUID bruteMovementSpeedUUID = UUID.fromString("f537419e-feb3-4d38-91f7-7dc3b715a142");
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onChangedBlock", at = @At("HEAD"), cancellable = true)
    private void onChangedBlock(BlockPos pos, CallbackInfo ci) {
        if (this.shouldRemoveBruteMovement(this.getBlockStateOn())) {
            this.tryRemoveBruteMovement();
        }

        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.BRUTE.get(), livingEntity) > 0)
            this.tryAddBruteMovement();
    }

    @Unique
    protected boolean shouldRemoveBruteMovement(BlockState state) {
        return !state.isAir();
    }

    @Unique
    protected void tryRemoveBruteMovement() {
        AttributeInstance attributeinstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeinstance != null) {
            if (attributeinstance.getModifier(bruteMovementSpeedUUID) != null) {
                attributeinstance.removeModifier(bruteMovementSpeedUUID);
            }

        }
    }

    @Unique
    private void tryAddBruteMovement() {
        if (!this.getBlockStateOn().isAir()) {
            int i = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrar.BRUTE.get(), livingEntity);
            AttributeInstance attributeinstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeinstance == null) {
                return;
            }

            attributeinstance.addTransientModifier(new AttributeModifier(bruteMovementSpeedUUID, "Brute Movement Boost", (0.03F * (1.0F + (float)i * 0.35F)), AttributeModifier.Operation.ADDITION));
        }

    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            for (ItemStack stack : livingEntity.getArmorSlots()) {
                if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getMaterial() == MachinationArmourMaterials.ENFORCED) {
                    cir.setReturnValue(false);
                }
            }
        }

        if (source.getEntity() instanceof Player player) {
            Level level = player.level();
            InteractionHand hand = player.getUsedItemHand();
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof CobaltPhialItem phialItem) {
                phialItem.createNewPhialInteraction(player, stack, livingEntity, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_ENDER_DRAGON.get(), DivineBeings.ENDER_DRAGON.getEntityType());
                phialItem.createNewPhialInteraction(player, stack, livingEntity, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_WITHER.get(), DivineBeings.WITHER.getEntityType());
                phialItem.createNewPhialInteraction(player, stack, livingEntity, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_WARDEN.get(), DivineBeings.WARDEN.getEntityType());
                phialItem.createNewPhialInteraction(player, stack, livingEntity, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_ELDER_GUARDIAN.get(), DivineBeings.ELDER_GUARDIAN.getEntityType());
                phialItem.createNewPhialInteraction(player, stack, livingEntity, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_PIGLIN_BRUTE.get(), DivineBeings.PIGLIN_BRUTE.getEntityType());
                phialItem.createNewPhialInteraction(player, stack, livingEntity, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_GUARDIAN.get(), DivineBeings.GUARDIAN.getEntityType());

                boolean flag = livingEntity.getType().is(EntityTypeTags.BLACKLISTED_PHIAL_ENTITIES);
                boolean flag2 = livingEntity.getType().is(EntityTypeTags.DIVINE_BEINGS);
                if (!flag && phialItem.isEmptyPhial()) {
                    level.playSound(player, player.blockPosition(), SoundsRegistrar.PHIAL_USE.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                    if (flag2) {
                        level.playSound(player, player.blockPosition(), SoundsRegistrar.PHIAL_USE_DIVINE_BEING.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                    }
                }
            }
        }
    }

}
