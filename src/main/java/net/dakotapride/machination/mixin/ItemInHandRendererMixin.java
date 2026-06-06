package net.dakotapride.machination.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.dakotapride.machination.item.PrismaticShufflerItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void renderArmWithItem(AbstractClientPlayer player, float d, float d2,
                                   InteractionHand hand, float d3, ItemStack itemStack, float d4,
                                   PoseStack poseStack, MultiBufferSource multiBufferSource, int d5, CallbackInfo ci) {
        boolean flag = hand == InteractionHand.MAIN_HAND;
        HumanoidArm humanoidarm = flag ? player.getMainArm() : player.getMainArm().getOpposite();
        if (itemStack.getItem() instanceof PrismaticShufflerItem && player.isUsingItem()
                && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
            this.applyPrismaticShufflerTransform(poseStack, humanoidarm, 25.0F);
        }
    }

    @Unique
    private void applyPrismaticShufflerTransform(PoseStack poseStack, HumanoidArm humanoidArm, float rot) {
        Quaternionf rotationDegrees = Axis.ZP.rotationDegrees(rot);
        if (humanoidArm != HumanoidArm.RIGHT) {
            poseStack.mulPose(rotationDegrees);
        } else {
            poseStack.mulPose(rotationDegrees.invert());
        }

    }
}
