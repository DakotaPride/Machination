package net.dakotapride.machination.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Unique
    Player player = (Player) (Object) this;
    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        CompoundTag tag = player.getPersistentData();
        if (player.getTags().contains("UnderPrismaticEffects")) {
            if (player.hasEffect(MobEffects.DIG_SLOWDOWN))
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
            int immuneToFatigueTicks = tag.getInt("ImmuneToFatigueTicks");
            if (immuneToFatigueTicks >= 200) {
                player.removeTag("UnderPrismaticEffects");
                tag.putInt("ImmuneToFatigueTicks", 0);
            } else {
                immuneToFatigueTicks++;
                tag.putInt("ImmuneToFatigueTicks", immuneToFatigueTicks);
            }
        }

        if (player.getTags().contains("ExpiredFromPrismaticShuffler")) {
            int expiredTicks = tag.getInt("ExpiredTicks");
            if (expiredTicks >= 400) {
                player.removeTag("ExpiredFromPrismaticShuffler");
                tag.putInt("ExpiredTicks", 0);
            } else {
                expiredTicks++;
                tag.putInt("ExpiredTicks", expiredTicks);
            }
        }

        if (player.getTags().contains("HasSteppedOnSculkRecently")) {
            int shadowkinBenefitsRemovalTicks = tag.getInt("ShadowkinBenefitsRemovalTicks");
            if (shadowkinBenefitsRemovalTicks >= 60) {
                player.removeTag("HasSteppedOnSculkRecently");
                tag.putInt("ShadowkinBenefitsRemovalTicks", 0);
            } else {
                shadowkinBenefitsRemovalTicks++;
                tag.putInt("ShadowkinBenefitsRemovalTicks", shadowkinBenefitsRemovalTicks);
            }
        }
    }
}
