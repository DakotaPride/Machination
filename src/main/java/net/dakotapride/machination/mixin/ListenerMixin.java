package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.ShadowkinGearItem;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationSystem.Listener.class)
public class ListenerMixin {
    @Inject(method = "handleGameEvent", at = @At("HEAD"), cancellable = true)
    private void handleGameEvent(ServerLevel level, GameEvent event, GameEvent.Context ctx, Vec3 vec3, CallbackInfoReturnable<Boolean> cir) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ShadowkinGearItem)
                cir.setReturnValue(false);
        }
    }
}
