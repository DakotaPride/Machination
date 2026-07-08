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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
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
            if (player != null && state.getBlock() instanceof SculkShriekerBlock
                    && state.getValue(BlockStateProperties.CAN_SUMMON) && level.getBlockState(pos.below()).is(Blocks.SOUL_CAMPFIRE)
                    && stack.is(Items.DRAGON_BREATH)) {
                ItemStack newStack = new ItemStack(ItemsRegistrar.SOUL_BOTTLE.get());
                if (!player.getAbilities().instabuild) {
                    if (!player.getInventory().add(newStack)) {
                        stack.shrink(1);
                        if (player.getItemInHand(context.getHand()).getCount() <= 1)
                            player.setItemInHand(context.getHand(), newStack);
                        else player.drop(newStack, false);
                    } else {
                        stack.shrink(1);
                        player.getInventory().add(newStack);
                    }
                } else {
                    if (!player.getInventory().add(newStack)) {
                        player.drop(newStack, false);
                    } else {
                        player.getInventory().add(newStack);
                    }
                }

                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                cir.setReturnValue(InteractionResult.sidedSuccess(true));
            }
        }
    }

}
