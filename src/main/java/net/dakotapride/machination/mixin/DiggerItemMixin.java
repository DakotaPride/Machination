package net.dakotapride.machination.mixin;

import net.dakotapride.machination.block.CobaltOreBlock;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DiggerItem.class, remap = false)
public abstract class DiggerItemMixin extends TieredItem implements Vanishable {
    public DiggerItemMixin(Tier tier, Properties p_43309_) {
        super(tier, p_43309_);
    }

    @Inject(method = "isCorrectToolForDrops(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private void isCorrectToolForDrops(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof DiggerItem diggerItem && diggerItem.getTier() != Tiers.IRON && state.getBlock() instanceof CobaltOreBlock)
            cir.setReturnValue(false);
    }

    @Inject(method = "isCorrectToolForDrops(Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private void isCorrectToolForDropsTwo(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        DiggerItem diggerItem = (DiggerItem) (Object) this;
        if (diggerItem.getTier() != Tiers.IRON && state.getBlock() instanceof CobaltOreBlock)
            cir.setReturnValue(false);
    }
}
