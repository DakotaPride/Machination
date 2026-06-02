package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.CobaltPhialItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonPart.class)
public abstract class EnderDragonPartMixin extends Entity {
    @Unique
    EnderDragonPart enderDragonPart = (EnderDragonPart) (Object) this;
    public EnderDragonPartMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() != null && source.getEntity() instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof CobaltPhialItem phialItem
                    && !player.getCooldowns().isOnCooldown(stack.getItem()) && phialItem.isEmptyPhial()) {
                EnderDragon enderDragon = enderDragonPart.getParent();
                float percentHealth = enderDragon.getMaxHealth() / 2;
                float actualDamage = enderDragon.getHealth() <= percentHealth ? enderDragon.getHealth() / 2 : percentHealth;
                CobaltPhialItem.applyInjectionEffects(player, enderDragonPart, source, actualDamage);
            }
        }
    }

}
