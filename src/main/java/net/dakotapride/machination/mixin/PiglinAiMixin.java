package net.dakotapride.machination.mixin;

import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {

    @Inject(method = "isWearingGold", at = @At("HEAD"), cancellable = true)
    private static void isWearingGold(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : livingEntity.getArmorSlots()) {
            if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getMaterial() == MachinationArmourMaterials.ENFORCED) {
                cir.setReturnValue(true);
            }
        }
    }
}
