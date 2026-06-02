package net.dakotapride.machination.mixin;

import net.dakotapride.machination.registrar.EnchantmentRegistrar;
import net.dakotapride.machination.util.MachinationArmourMaterials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Unique
    Player player = (Player) (Object) this;
    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof ArmorItem) {
                ItemStack headSlot = player.getItemBySlot(EquipmentSlot.HEAD);
                ItemStack chestSlot = player.getItemBySlot(EquipmentSlot.CHEST);
                ItemStack legsSlot = player.getItemBySlot(EquipmentSlot.LEGS);
                ItemStack feetSlot = player.getItemBySlot(EquipmentSlot.FEET);
                if (headSlot.getItem() instanceof ArmorItem headItem
                        && chestSlot .getItem() instanceof ArmorItem chestItem
                        && legsSlot .getItem() instanceof ArmorItem legsItem
                        && feetSlot .getItem() instanceof ArmorItem feetItem) {
                    boolean isHeadGold = headItem.getMaterial() == ArmorMaterials.GOLD;
                    boolean isChestEnforced = chestItem.getMaterial() == MachinationArmourMaterials.ENFORCED;
                    boolean isLegsGold = legsItem.getMaterial() == ArmorMaterials.GOLD;
                    boolean isFeetGold = feetItem.getMaterial() == ArmorMaterials.GOLD;

                    if (isChestEnforced && stack.getEnchantmentLevel(EnchantmentRegistrar.GILDED_SKIN.get()) > 0)
                        if (isHeadGold && isLegsGold && isFeetGold) {
                            MobEffectInstance effectInstance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20*60, 2);
                            if (effectInstance.endsWithin(20*3) || !player.hasEffect(MobEffects.DAMAGE_RESISTANCE))
                                player.addEffect(effectInstance);
                        }
                }
            }
        }
    }
}
