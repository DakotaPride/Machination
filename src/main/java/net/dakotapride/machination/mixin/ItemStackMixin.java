package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.EnforcedGearItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    ItemStack stack = (ItemStack) (Object) this;

    @Inject(method = "hurt", at = @At("HEAD"))
    private void hurt(int amount, RandomSource random, ServerPlayer serverPlayer, CallbackInfoReturnable<Boolean> cir) {
        if (EnforcedGearItem.hasGildedSkinEnchantment(serverPlayer)) {
            int l = stack.getDamageValue() + (amount * 2);
            stack.setDamageValue(l);
        }
    }
}
