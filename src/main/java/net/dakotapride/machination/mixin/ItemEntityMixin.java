package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.ShadowkinGearItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow
    @Nullable
    public abstract Entity getOwner();

    @Inject(method = "dampensVibrations", at = @At("HEAD"), cancellable = true)
    private void dampensVibrations(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = this.getOwner();
        if (entity instanceof Player player) {
            if (ShadowkinGearItem.ableToStopVibrationDetection(player))
                cir.setReturnValue(ShadowkinGearItem.ableToStopVibrationDetection(player));
            else
                ShadowkinGearItem.createCatatonicEffects(player);
        }
    }
}
