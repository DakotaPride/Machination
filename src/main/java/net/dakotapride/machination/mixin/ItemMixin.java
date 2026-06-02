package net.dakotapride.machination.mixin;

import net.dakotapride.machination.registrar.ItemsRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            ItemStack stack = context.getItemInHand();
            Player player = context.getPlayer();
            if (player != null && state.getBlock() instanceof SculkShriekerBlock && state.getValue(BlockStateProperties.CAN_SUMMON) && stack.is(Items.GLASS_BOTTLE)) {
                player.addItem(new ItemStack(ItemsRegistrar.SOUL_BOTTLE.get(), 1));
                stack.shrink(1);
                level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.CAN_SUMMON, false));
                cir.setReturnValue(InteractionResult.sidedSuccess(true));
            }
        }
    }

}
