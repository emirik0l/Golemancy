package net.emirikol.golemancy.util;

import net.minecraft.item.ItemStack;

public class ModSupport {

    public static final String CROPARIA_IDENTIFIER = "com.croparia.mod.item.CropsSeed";

    public static boolean isModdedSeed(ItemStack stack) {
        boolean croparia = stack.getItem().getClass().getName().equals(CROPARIA_IDENTIFIER);
        return croparia;
    }
}