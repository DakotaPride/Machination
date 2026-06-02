package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.CobaltPhialItem;
import net.dakotapride.machination.item.DivineBeings;
import net.dakotapride.machination.registrar.ItemsRegistrar;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {
    @Unique
    EnderDragon enderDragon = (EnderDragon) (Object) this;
    protected EnderDragonMixin(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Inject(method = "hurt(Lnet/minecraft/world/entity/boss/EnderDragonPart;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"))
    private void hurt(EnderDragonPart part, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof Player player) {
            Level level = player.level();
            InteractionHand hand = player.getUsedItemHand();
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof CobaltPhialItem phialItem)
               phialItem.createNewPhialInteraction(player, stack, enderDragon, hand, level,
                        ItemsRegistrar.COBALT_PHIAL_ENDER_DRAGON.get(), DivineBeings.ENDER_DRAGON.getEntityType());
        }
    }

}
