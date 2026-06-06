package net.dakotapride.machination.mixin;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public class WardenMixin {
    @Inject(method = "canTargetEntity", at = @At("HEAD"), cancellable = true)
    private void canTargetEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            for (ItemStack stack : livingEntity.getArmorSlots()) {
                if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getMaterial() == MachinationArmourMaterials.SHADOWKIN) {
                    cir.setReturnValue(stack.getEnchantmentLevel(EnchantmentRegistrar.HIVEMIND.get()) <= 0);
                }
            }
        }
    }
}
