package net.emirikol.golemancy.item;

import net.emirikol.golemancy.GolemancyItemGroup;
import net.minecraft.item.Item;

public class SoulstoneEmpty extends Item {

    public SoulstoneEmpty(Settings settings) {
        super(settings.group(GolemancyItemGroup.GOLEMANCY_ITEM_GROUP));
    }
}