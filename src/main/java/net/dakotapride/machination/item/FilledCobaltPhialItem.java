package net.dakotapride.machination.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledCobaltPhialItem extends CobaltPhialItem {
    public FilledCobaltPhialItem(DivineBeings divineBeing, Properties properties) {
        super(false, properties);
        this.divineBeing = divineBeing;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (divineBeing != DivineBeings.NONE)
            tooltipComponents.add(Component.translatable("text.machination.creature", Component.translatable(divineBeing.getEntityType().getDescriptionId()).withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.DARK_GREEN));
    }
}
