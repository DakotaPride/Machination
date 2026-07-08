package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.ShadowkinGearItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {
    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    private void stepOn(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (entity instanceof Player player && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ShadowkinGearItem) {
            if (ShadowkinGearItem.ableToStopVibrationDetection(player))
                ci.cancel();
            else
                ShadowkinGearItem.createCatatonicEffects(player);
        }
    }
}
