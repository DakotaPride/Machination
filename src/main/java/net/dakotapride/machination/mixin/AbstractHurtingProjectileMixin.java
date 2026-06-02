package net.dakotapride.machination.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHurtingProjectile.class)
public abstract class AbstractHurtingProjectileMixin extends Projectile {
    @Unique
    Level level;
    @Unique
    AbstractHurtingProjectile projectile = (AbstractHurtingProjectile) (Object) this;
    public AbstractHurtingProjectileMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.level = level;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (projectile instanceof WitherSkull skull && skull.getTags().contains("FromWitheringBashProjectile")) {
            CompoundTag tag = skull.getPersistentData();
            int age = tag.getInt("Age");
            if (age >= 30) {
                skull.discard();
            } else {
                age++;
                tag.putInt("Age", age);
            }
        }
    }

}
