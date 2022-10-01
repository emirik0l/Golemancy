package net.emirikol.golemancy.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class SoulstoneEmpty extends Item {

    public SoulstoneEmpty(Settings settings) {
        super(settings.group(ItemGroup.SEARCH));
    }
}