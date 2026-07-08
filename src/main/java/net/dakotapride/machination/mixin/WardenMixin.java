package net.dakotapride.machination.mixin;

import net.dakotapride.machination.item.ShadowkinGearItem;
import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenMixin extends Monster {
    public WardenMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void hurt(DamageSource source, float f, CallbackInfoReturnable<Boolean> cir) {
        if (source.getDirectEntity() instanceof LivingEntity livingEntity) {
            for (ItemStack stack : livingEntity.getArmorSlots()) {
                if (stack.getItem() instanceof ShadowkinGearItem && livingEntity instanceof Player player && ShadowkinGearItem.hasHivemindEnchantment(player)) {
                    livingEntity.addTag("VulnerableToWardenAttacks");
                }
            }
        }
    }

    @Inject(method = "canTargetEntity", at = @At("HEAD"), cancellable = true)
    private void canTargetEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            for (ItemStack stack : livingEntity.getArmorSlots()) {
                if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getMaterial() == MachinationArmourMaterials.SHADOWKIN) {
                    cir.setReturnValue(livingEntity.getTags().contains("VulnerableToWardenAttacks") || stack.getEnchantmentLevel(EnchantmentRegistrar.HIVEMIND.get()) <= 0);
                }
            }
        }
    }
}
